package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.GoToInterface;
import by.fyodorov.musicstore.command.RedirectGoTo;
import by.fyodorov.musicstore.command.RequestParameterMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommandImpl implements Command {
    private static Logger LOGGER = LogManager.getLogger(LogoutCommandImpl.class);

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) {
        requestInfo.setSessionAttribute("userRole", null);
        requestInfo.setSessionAttribute("userName", null);
        return new RedirectGoTo("/");
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
