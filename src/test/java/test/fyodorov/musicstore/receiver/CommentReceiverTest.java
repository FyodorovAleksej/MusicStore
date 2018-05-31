package test.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.receiver.CommentReceiver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CommentReceiverTest {
    private static final String PATH = "db.properties";
    private CommentReceiver receiver;

    @BeforeClass
    public void initClass() throws Exception {
        ConnectionPool.getInstance(PATH);
        receiver = new CommentReceiver();
    }

    @AfterClass
    public void finalize() throws Exception {
        ConnectionPool.getInstance(PATH).destroy();
    }
}
