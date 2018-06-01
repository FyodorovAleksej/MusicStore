package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * removing selected assemblage from system
 */
public class RemoveAssemblageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveAssemblageCommand.class);
    private AssemblageReceiver assemblageReceiver;

    /**
     * creating command with assemblage receiver
     * @param assemblageReceiver - receiver for working with assemblages
     */
    public RemoveAssemblageCommand(AssemblageReceiver assemblageReceiver) {
        this.assemblageReceiver = assemblageReceiver;
    }

    /**
     * performing command. Removing selected assemblage frm system
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String assemblageName = (String) requestInfo.getSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && assemblageName != null) {
                if (assemblageReceiver.removeAssemblage(assemblageName)) {
                    LOGGER.debug("remove \"" + assemblageName + "\" assemblage successfully");
                } else {
                    LOGGER.debug("remove \"" + assemblageName + "\" assemblage failed");
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
