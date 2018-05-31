package by.fyodorov.musicstore.command.impl;

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

/**
 * sign in
 */
public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private UserReceiver userReceiver;

    /**
     * creating command with user receiver
     * @param userReceiver - receiver for working with users
     */
    public LoginCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }

    /**
     * performing command. Sign in for user
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();

        String login = requestInfo.getParameter(RequestArgument.LOGIN.getName());
        String password = requestInfo.getParameter(RequestArgument.PASSWORD.getName());

        try {
            if (userReceiver.validateUser(login) && userReceiver.validatePassword(password)) {
                if (userReceiver.checkUser(login, password)) {
                    requestInfo.setRequestAttribute(RequestArgument.LOGIN_RESULT.getName(), "Confirm");

                    UserEntity user = userReceiver.findUser(login);
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
        } catch (ConnectorException e) {
            throw new CommandException("can't execute login command - connection problem", e);
        }
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
