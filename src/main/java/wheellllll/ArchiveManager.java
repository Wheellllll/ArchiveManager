package wheellllll;

/**
 * Created by sweet on 5/4/16.
 */
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
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
                File destArchive = new File(mArchiveDir, mArchivePrefix + " " + df.format(new Date()) + "." + mArchiveSuffix);

                File archiveFolder = new File(mArchiveDir);
                if (!archiveFolder.exists()) archiveFolder.mkdirs();

                ZipParameters parameters = new ZipParameters();
                if (this.isEncrypt) {
                    parameters.setEncryptFiles(true);
                    parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                    parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                    parameters.setPassword(password.toCharArray());
                }

                ZipFile destFile = new ZipFile(destArchive);

                for (File folder : folders) {
                    if (!folder.exists()) folder.mkdirs();
                    destFile.addFolder(folder, parameters);
                    FileUtils.cleanDirectory(folder);
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

}
