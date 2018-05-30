package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RemoveAssemblageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveAssemblageCommand.class);
    private AssemblageReceiver receiver;

    public RemoveAssemblageCommand(AssemblageReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userRole = (String) request.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String assemblageName = (String) request.getSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && assemblageName != null) {
                if (receiver.removeAssemblage(assemblageName)) {
                    LOGGER.debug("remove assemblage successfully");
                } else {
                    LOGGER.debug("remove assemblage failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't remove assemblage - " + assemblageName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_ASSEMBLAGES_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
