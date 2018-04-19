package by.fyodorov.musicstore.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    GoToInterface perform(RequestParameterMap requestInfo) throws CommandException;
    void refresh(HttpServletRequest request);
}
