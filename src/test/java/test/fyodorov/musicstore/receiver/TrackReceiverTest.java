package test.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedList;

import static org.testng.Assert.*;

public class TrackReceiverTest {
    private static TrackReceiver receiver;
    private static final String PATH = "db.properties";

    @BeforeClass
    public void initClass() throws Exception {
        ConnectionPool.getInstance(PATH);
        receiver = new TrackReceiver();
    }

    @AfterClass
    public void finalize() throws Exception {
        ConnectionPool.getInstance(PATH).destroy();
    }

    @Test
    public void testFindAllTracks() throws Exception {
        receiver.findAllTracks("p%").forEach(System.out::println);
    }

    @Test
    public void testFindComments() throws Exception {
        receiver.findComments("outbreak").forEach(System.out::println);
    }

    @Test
    public void testFindTrackInfo() throws Exception {
        receiver.findTrackInfo("Katrine").forEach(System.out::println);
    }
}