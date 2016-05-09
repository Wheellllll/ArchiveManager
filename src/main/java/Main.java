import wheellllll.ArchiveManager;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ArchiveManager archiveManager = new ArchiveManager();
        //初始化archiveManager
        archiveManager.setDatePattern("yyyy-MM-dd");
        archiveManager.addFolder("./clientarchive");
        archiveManager.setInterval(7, TimeUnit.SECONDS);
        archiveManager.setInitialDelay(1);
        archiveManager.setEncrypt(true);
        archiveManager.setPassword("aaa");

        archiveManager.start();
    }
}
