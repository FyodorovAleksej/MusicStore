package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.ASSEMBLAGE_INFO_WITH_ARG_PAGE;

/**
 * buy assemblage for current user
 */
public class BuyAssemblageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BuyAssemblageCommand.class);
    private UserReceiver userReceiver;

    /**
     * creating command with user receiver
     * @param userReceiver - receiver for working with users
     */
    public BuyAssemblageCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }

    /**
     * performing command. Adding assemblage and tracks in it to user
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String assemblageName = (String) requestInfo.getSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName());
        try {
            if (userName != null && assemblageName != null) {

                if (userReceiver.buyAssemblage(userName, assemblageName)) {
                    LOGGER.debug("buy successfully");
                } else {
                    LOGGER.debug("buy Unsuccessfully");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't buy assemblage - " + assemblageName, e);
        }
        return new RedirectGoTo(requestInfo.getContextPath() + ASSEMBLAGE_INFO_WITH_ARG_PAGE.getPath() + assemblageName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
