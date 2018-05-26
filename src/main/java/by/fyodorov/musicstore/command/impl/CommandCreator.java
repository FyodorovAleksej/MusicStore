package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandCreator {
    private static Logger LOGGER = LogManager.getLogger(CommandCreator.class);

    public Command createCommand(String command) throws CommandException {
        if (command == null) {
            return CommandType.getDefault().getCommand();
        }
        try {
            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            return commandType.getCommand();
        }
        catch (IllegalArgumentException e) {
            throw new CommandException("invalid command name for create = \"" + command + "\"", e);
        }
    }
}
