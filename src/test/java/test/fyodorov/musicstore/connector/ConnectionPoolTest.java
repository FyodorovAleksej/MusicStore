package test.fyodorov.musicstore.connector;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.repository.AlbumRepository;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileWriter;

import static org.testng.Assert.*;

public class ConnectionPoolTest {

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testReleaseConnection() throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance("db.properties");
        pool.destroy();
    }

}