package wheellllll;

import javafx.scene.shape.Arc;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by summer on 4/25/16.
 */
public class ArchiveManagerTest {

    @Test
    public void testAll() throws  Exception {
        ArchiveManager archiveManager = new ArchiveManager();
        //初始化archiveManager
        archiveManager.setDatePattern("yyyy-MM-dd");
        archiveManager.addFolder("./clientarchive");
        archiveManager.setInterval(7, TimeUnit.SECONDS);
        archiveManager.setInitialDelay(1);
        archiveManager.setEncrypt(true);
        archiveManager.setPassword("aaa");

        archiveManager.start();

        Thread.currentThread().join();
    }

    @Test
    public void testIsEncrypt() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        manager.setEncrypt(true);
        assertEquals(true,manager.isEncrypt());
    }
        @Test
    public void testSetEncrypt() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        manager.setEncrypt(true);
        assertEquals(true,manager.isEncrypt());
    }

    @Test
    public void testGetPassword() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        manager.setPassword("test");
        assertEquals("test",manager.getPassword());
    }

    @Test
    public void testSetPassword() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        manager.setPassword("test");
        assertEquals("test",manager.getPassword());
    }

    @Test
    public void testSetInitialDelay() throws Exception {

    }

    @Test
    public void testSetInterval() throws Exception {

    }

    @Test
    public void testSetDatePattern() throws Exception {

    }

    @Test
    public void testStart() throws Exception {

    }

    @Test
    public void testStop() throws Exception {

    }

    @Test
    public void testAddLogger() throws Exception {

    }

    @Test
    public void testRemoveLogger() throws Exception {

    }

    @Test
    public void testSetArchiveDir() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String dir = "test";
        manager.setArchiveDir(dir);
        assertEquals(manager.getArchiveDir(),dir);
    }

    @Test
    public void testSetArchivePrefix() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String prefix = "test";
        manager.setArchivePrefix(prefix);
        assertEquals(manager.getArchivePrefix(),prefix);
    }

    @Test
    public void testGetArchiveDir() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String dir = "test";
        manager.setArchiveDir(dir);
        assertEquals(manager.getArchiveDir(),dir);
    }

    @Test
    public void testGetArchivePrefix() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        String prefix = "test";
        manager.setArchivePrefix(prefix);
        assertEquals(manager.getArchivePrefix(),prefix);
    }

    @Test
    public void testGetArchiveSuffix() throws Exception {
        ArchiveManager manager = new ArchiveManager();
        assertEquals("zip",manager.getArchiveSuffix());
    }
}

