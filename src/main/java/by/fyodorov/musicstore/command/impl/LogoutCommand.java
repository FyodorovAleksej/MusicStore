package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * logout user
 */
public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    /**
     * performing command. Prepare information about tracks for adding assemblage
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) {
        requestInfo.setSessionAttribute(RequestArgument.SESSION_ROLE.getName(), null);
        requestInfo.setSessionAttribute(RequestArgument.SESSION_LOGIN.getName(), null);
        return new RedirectGoTo(PagesUrl.MAIN_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
