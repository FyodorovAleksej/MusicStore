package test.fyodorov.musicstore.command;

import by.fyodorov.musicstore.command.impl.CommandCreator;
import by.fyodorov.musicstore.command.impl.CommandType;
import by.fyodorov.musicstore.command.CommandException;
import by.fyodorov.musicstore.command.Command;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CommandCreatorTest {
    private final static String FAIL_COMMAND_NAME = "NOT_EXIST_COMMAND";
    private final static String LOGIN_COMMAND_NAME = CommandType.LOGIN_COMMAND.toString();
    private final static String LOWER_LOGIN_COMMAND_NAME = CommandType.LOGIN_COMMAND.toString().toLowerCase();

    @Test
    public void testCreateCommandPositive() throws Exception {
        CommandCreator commandCreator = new CommandCreator();
        Command command = commandCreator.createCommand(LOGIN_COMMAND_NAME);
        Assert.assertEquals(command, CommandType.LOGIN_COMMAND.getCommand());
    }

    @Test
    public void testCreateCommandLower() throws Exception {
        CommandCreator commandCreator = new CommandCreator();
        Command command = commandCreator.createCommand(LOWER_LOGIN_COMMAND_NAME);
        Assert.assertEquals(command, CommandType.LOGIN_COMMAND.getCommand());
    }


    @Test(expectedExceptions = CommandException.class)
    public void testCreateCommandNegative() throws Exception {
        CommandCreator commandCreator = new CommandCreator();
        commandCreator.createCommand(FAIL_COMMAND_NAME);
        Assert.fail();
    }

    @Test
    public void testCreateCommandNull() throws Exception {
        CommandCreator commandCreator = new CommandCreator();
        Command command = commandCreator.createCommand(null);
        Assert.assertEquals(command, CommandType.getDefault().getCommand());
    }
}