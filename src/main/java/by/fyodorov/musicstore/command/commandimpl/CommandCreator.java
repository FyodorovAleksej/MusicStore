package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandCreator {
    private static Logger LOGGER = LogManager.getLogger(CommandCreator.class);

    public Command createCommand(String command) throws CommandException {
        if (command == null) {
            return CommandEnum.getDefault().getCommand();
        }
        try {
            CommandEnum commandType = CommandEnum.valueOf(command.toUpperCase());
            return commandType.getCommand();
        }
        catch (IllegalArgumentException e) {
            throw new CommandException("invalid command name for create = \"" + command + "\"", e);
        }
    }
}
