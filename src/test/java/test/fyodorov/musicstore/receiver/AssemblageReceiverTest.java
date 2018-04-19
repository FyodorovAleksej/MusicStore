package test.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.receiver.UserReceiver;
import by.fyodorov.musicstore.repository.AssemblageRepository;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AssemblageReceiverTest {
    private static UserReceiver userReceiver;
    private static AssemblageReceiver assemblageReceiver;

    private static final String PATH = "db.properties";

    @BeforeClass
    public void initClass() throws Exception {
        ConnectionPool.getInstance(PATH);
        userReceiver = new UserReceiver();
        assemblageReceiver = new AssemblageReceiver();
    }

    @AfterClass
    public void finalize() throws Exception {
        ConnectionPool.getInstance(PATH).destroy();
    }

    @Test
    public void testBuyAssemblage() throws Exception {
        userReceiver.buyAssemblage("admin", "new");
    }

    @Test
    public void testGet() throws Exception {
        assemblageReceiver.findAssemblageForUser("admin").forEach(System.out::println);
    }
}
