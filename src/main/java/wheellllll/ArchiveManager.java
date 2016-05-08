package wheellllll;

/**
 * Created by sweet on 5/4/16.
 */
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sweet on 4/10/16.
 */
public class ArchiveManager {
    private String mArchiveDir = "./";
    private String mArchivePrefix = "record";
    private String mArchiveSuffix = "zip";

    private ScheduledExecutorService sc = null;
    private int mInitialDelay = 1;
    private int mPeriod = 1;
    private TimeUnit mTimeUnit = TimeUnit.MINUTES;
    private boolean isEncrypt = false;//默认不加密
    private String password = "123456";

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");

    private Set<File> folders;

    public ArchiveManager() {
        folders = new HashSet<>();
        sc = Executors.newScheduledThreadPool(1);
    }

    public void setInitialDelay(int initialDelay) {
        mInitialDelay = initialDelay;
    }

    public void setInterval(int period, TimeUnit timeUnit) {
        mPeriod = period;
        mTimeUnit = timeUnit;
    }

    public void setDatePattern(String datePattern) {
        df.applyPattern(datePattern);
    }

    public void start() {
        sc.scheduleAtFixedRate(() -> {
            try {
                File tmpFolder = new File(System.getProperty("java.io.tmpdir") + "/.wheellllll"+System.nanoTime());
                if (!tmpFolder.exists()) tmpFolder.mkdirs();

                for (File folder : folders) {
                    if (!folder.exists()) folder.mkdirs();
                    File ttmpFolder = new File(tmpFolder, folder.getName());
                    if (!ttmpFolder.exists()) ttmpFolder.mkdirs();
                    FileUtils.copyDirectory(folder, ttmpFolder);
                    FileUtils.cleanDirectory(folder);
                }

                File destArchive = new File(mArchiveDir, mArchivePrefix + " " + df.format(new Date()) + "." + mArchiveSuffix);

                File archiveFolder = new File(mArchiveDir);
                if (!archiveFolder.exists()) archiveFolder.mkdirs();


                ZipUtil.pack(tmpFolder, destArchive);

                FileUtils.deleteDirectory(tmpFolder);
                if(this.isEncrypt) {
                    encrypt(destArchive);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        },
                mInitialDelay,
                mPeriod,
                mTimeUnit);
    }

    public void stop() {
        sc.shutdown();
    }

    public void addFolder(String path) {
        File folder = new File(path);
        folders.add(folder);
    }

    public void setArchiveDir(String archiveDir) {
        File file = new File(archiveDir);
        file.mkdirs();
        mArchiveDir = archiveDir;
    }

    public void setArchivePrefix(String archivePrefix) {
        mArchivePrefix = archivePrefix;
    }

    public String getArchiveDir() {
        return mArchiveDir;
    }

    public String getArchivePrefix() {
        return mArchivePrefix;
    }

    public String getArchiveSuffix() {
        return mArchiveSuffix;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //加密
    public boolean encrypt(File scrPath) {
        try {
            if(!new File(scrPath.getName()).exists()) {
                System.out.println("源路径不存在 "+scrPath);
                return false;
            }
            ZipParameters parameters = new ZipParameters();
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(password.toCharArray());
            File srcFile = new File(scrPath.getName());
            ZipFile destFile = new ZipFile(scrPath);
            if(srcFile.isDirectory()) {
                destFile.addFolder(srcFile, parameters);
            } else {
                destFile.addFile(srcFile, parameters);
            }
            System.out.println("成功加密文件");
            return true;
        } catch (Exception e) {
            System.out.println("加密文件发生异常:"+e);
            return false;
        }
    }
}
