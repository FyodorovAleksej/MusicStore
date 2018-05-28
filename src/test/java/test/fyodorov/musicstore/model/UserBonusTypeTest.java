package test.fyodorov.musicstore.model;

import by.fyodorov.musicstore.model.UserBonusType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserBonusTypeTest {

    @Test
    public void testToBonusType() {
        String expected = "free track,free assemblage";
        Assert.assertEquals(UserBonusType.fromBonusType(1|4), expected);
    }

    @Test
    public void testFromBonusType() {
        int expected = 2|4;
        Assert.assertEquals(UserBonusType.toBonusType("free album,free assemblage"), expected);
    }
}