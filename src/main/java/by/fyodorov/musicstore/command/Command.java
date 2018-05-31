package by.fyodorov.musicstore.command;

import javax.servlet.http.HttpServletRequest;

/**
 * interface of command to perform by servlet
 */
public interface Command {
    /**
     * performing command and return page to forward or redirect
     * @param requestInfo - map with arguments of request and session
     * @return - path of new page to forward or redirect
     * @throws CommandException - exception, when command can't perform
     */
    GoToInterface perform(RequestParameterMap requestInfo) throws CommandException;

    /**
     * refresh page
     * @param request - map with arguments of request and session
     */
    void refresh(HttpServletRequest request);
}
