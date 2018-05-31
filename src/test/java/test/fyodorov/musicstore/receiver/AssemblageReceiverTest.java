package test.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.model.UserBonusType;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AssemblageReceiverTest {
    private static UserReceiver userReceiver;
    private static AssemblageReceiver assemblageReceiver;

    private static final String PATH = "db.properties";
    private static final String TEST_ASSEMBLAGE = "testAssemblage";
    private static final String TEST_USER = "root";

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
    public void testAddAssemblage() throws Exception {
        Assert.assertTrue(assemblageReceiver.addNewAssemblage(TEST_ASSEMBLAGE, "electro", 23, TEST_USER, new String[0]));
    }

    @Test(dependsOnMethods = {"testAddAssemblage"})
    public void testGetAssemblage() throws Exception {
        Assert.assertTrue(assemblageReceiver.assemblageInfoForUser(TEST_ASSEMBLAGE, TEST_USER).isPresent());
    }

    @Test(dependsOnMethods = {"testGetAssemblage"})
    public void testUpdateUser() throws Exception {
        Assert.assertTrue(userReceiver.updateUser(TEST_USER, "admin", UserBonusType.fromBonusType(7), 20));
    }

    @Test(dependsOnMethods = {"testUpdateUser"})
    public void testGetUser() throws Exception {
        Assert.assertTrue(userReceiver.findUser(TEST_USER).getDiscount() == 20);
    }

    @Test(dependsOnMethods = {"testGetUser"})
    public void testBuyAssemblage() throws Exception {
        Assert.assertTrue(userReceiver.buyAssemblage(TEST_USER, TEST_ASSEMBLAGE));
    }

    @Test(dependsOnMethods = {"testBuyAssemblage"})
    public void testRemoveAssemblage() throws Exception {
        Assert.assertTrue(assemblageReceiver.removeAssemblage(TEST_ASSEMBLAGE));
    }

    @Test(dependsOnMethods = {"testRemoveAssemblage"})
    public void testGetOwnAssemblage() throws Exception {
        Assert.assertFalse(assemblageReceiver.assemblageInfoForUser(TEST_ASSEMBLAGE, TEST_USER).isPresent());
    }
}
