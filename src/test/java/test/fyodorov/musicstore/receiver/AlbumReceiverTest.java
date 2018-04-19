package test.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectionPool;
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
    public void testGetAlbum() throws Exception {
        albumReceiver.findAlbumForUser("root").forEach(System.out::println);
    }

    @Test
    public void testBuyAlbum() throws Exception {
        Assert.assertTrue(userReceiver.buyAlbum("admin", "stronger"));
    }
}
