package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommandImpl implements Command {
    private static Logger LOGGER = LogManager.getLogger(LoginCommandImpl.class);
    private UserReceiver receiver;

    public LoginCommandImpl(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();

        String login = requestInfo.getParameter(RequestArgument.LOGIN.getName());
        String password = requestInfo.getParameter(RequestArgument.PASSWORD.getName());

        try {
            if (receiver.validateUser(login) && receiver.validatePassword(password)) {
                if (receiver.checkUser(login, password)) {
                    requestInfo.setRequestAttribute(RequestArgument.LOGIN_RESULT.getName(), "Confirm");

                    UserEntity user = receiver.findUser(login);
                    if (user != null) {
                        requestInfo.setSessionAttribute(RequestArgument.SESSION_LOGIN.getName(), user.getUserName());
                        requestInfo.setSessionAttribute(RequestArgument.SESSION_ROLE.getName(), user.getRole());
                    }

                    path = Optional.of(PagesUrl.MAIN_PAGE.getPath());
                } else {
                    requestInfo.setRequestAttribute(RequestArgument.LOGIN_RESULT.getName(), "Unsuccessfully");
                    path = Optional.of(PagesUrl.MAIN_PAGE.getPath());
                }
            }
            return new RedirectGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
        }
        catch (ConnectorException e) {
            throw new CommandException("can't execute login command - connection problem", e);
        }
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
