package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.ASSEMBLAGE_INFO_WITH_ARG_PAGE;

public class AssemblageBuyCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AssemblageBuyCommand.class);
    private UserReceiver receiver;

    public AssemblageBuyCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userName = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String assemblageName = (String) request.getSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName());
        try {
            if (userName != null && assemblageName != null) {

                if (receiver.buyAssemblage(userName, assemblageName)) {
                    LOGGER.debug("buy successfully");
                } else {
                    LOGGER.debug("buy Unsuccessfully");
                }
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("Can't buy assemblage - " + assemblageName, e);
        }
        return new RedirectGoTo(request.getContextPath() + ASSEMBLAGE_INFO_WITH_ARG_PAGE.getPath() + assemblageName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
