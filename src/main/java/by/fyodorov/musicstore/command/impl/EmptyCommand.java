package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.GoToInterface;
import by.fyodorov.musicstore.command.RedirectGoTo;
import by.fyodorov.musicstore.command.RequestParameterMap;

import javax.servlet.http.HttpServletRequest;

/**
 * empty command
 */
public class EmptyCommand implements Command {

    /**
     * performing command. Redirect to main page
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) {
        return new RedirectGoTo(PagesUrl.MAIN_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
