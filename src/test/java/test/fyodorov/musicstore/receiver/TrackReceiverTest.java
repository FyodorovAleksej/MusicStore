package test.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedList;

import static org.testng.Assert.*;

public class TrackReceiverTest {
    private static TrackReceiver trackReceiver;
    private static final String PATH = "db.properties";

    @BeforeClass
    public void initClass() throws Exception {
        ConnectionPool.getInstance(PATH);
        trackReceiver = new TrackReceiver();
    }

    @AfterClass
    public void finalize() throws Exception {
        ConnectionPool.getInstance(PATH).destroy();
    }
}