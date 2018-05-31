package test.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.model.UserBonusType;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AlbumReceiverTest {
    private static UserReceiver userReceiver;
    private static AlbumReceiver albumReceiver;

    private static final String PATH = "db.properties";
    private static final String TEST_ALBUM = "testAlbum";
    private static final String TEST_USER = "root";

    @BeforeClass
    public void initClass() throws Exception {
        ConnectionPool.getInstance(PATH);
        userReceiver = new UserReceiver();
        albumReceiver = new AlbumReceiver();
    }

    @AfterClass
    public void finalize() throws Exception {
        ConnectionPool.getInstance(PATH).destroy();
    }

    @Test
    public void testAddAlbum() throws Exception {
        Assert.assertTrue(albumReceiver.addNewAlbum(TEST_ALBUM, "electro", 23, "NitroFun", new String[0]));
    }

    @Test (dependsOnMethods={"testAddAlbum"})
    public void testGetAlbum() throws Exception {
        Assert.assertTrue(albumReceiver.albumInfoForUser(TEST_ALBUM, TEST_USER).isPresent());
    }

    @Test (dependsOnMethods = {"testGetAlbum"})
    public void testUpdateAlbum() throws Exception {
        Assert.assertTrue(albumReceiver.editAlbum(TEST_ALBUM, TEST_ALBUM, "electro,pop", 45, "NitroFun", new String[0]));
    }

    @Test (dependsOnMethods = {"testUpdateAlbum"})
    public void testUpdateUser() throws Exception {
        Assert.assertTrue(userReceiver.updateUser(TEST_USER, "admin", UserBonusType.fromBonusType(7), 20));
    }

    @Test (dependsOnMethods = {"testUpdateUser"})
    public void testGetUser() throws Exception {
        Assert.assertTrue(userReceiver.findUser(TEST_USER).getDiscount() == 20);
    }

    @Test (dependsOnMethods = {"testGetUser"})
    public void testBuyAlbum() throws Exception {
        Assert.assertTrue(userReceiver.buyAlbum(TEST_USER, TEST_ALBUM));
    }

    @Test (dependsOnMethods = {"testBuyAlbum"})
    public void testRemoveAlbum() throws Exception {
        Assert.assertTrue(albumReceiver.removeAlbum(TEST_ALBUM));
    }

    @Test (dependsOnMethods = {"testRemoveAlbum"})
    public void testGetOwnAlbum() throws Exception {
        Assert.assertFalse(albumReceiver.albumInfoForUser(TEST_ALBUM, TEST_USER).isPresent());
    }
}
