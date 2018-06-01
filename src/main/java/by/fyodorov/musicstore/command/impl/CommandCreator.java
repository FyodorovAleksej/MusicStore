package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * command builder
 */
public class CommandCreator {
    /**
     * create command by it name
     * @param command - name of command. Was set by filters
     * @return - created command
     * @throws CommandException - when name of command is invalid
     */
    public Command createCommand(String command) throws CommandException {
        if (command == null) {
            return CommandType.getDefault().getCommand();
        }
        try {
            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            return commandType.getCommand();
        } catch (IllegalArgumentException e) {
            throw new CommandException("invalid command name for create = \"" + command + "\"", e);
        }
    }
}
