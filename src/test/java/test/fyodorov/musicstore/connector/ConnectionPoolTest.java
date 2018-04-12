package test.fyodorov.musicstore.connector;

import by.fyodorov.musicstore.connector.ConnectionPool;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @Test
    public void testDestroy() throws Exception {
    }

    @Test
    public void testGetConnection() throws Exception {
    }
}