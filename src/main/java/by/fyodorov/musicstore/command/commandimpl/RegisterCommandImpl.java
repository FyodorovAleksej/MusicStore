package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.receiver.UserReceiver;
import by.fyodorov.musicstore.util.RegistrantKeyMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RegisterCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommandImpl.class);
    private UserReceiver receiver;

    public RegisterCommandImpl(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String uuid = requestInfo.getParameter(RequestArgument.UUID.getName());
        UserEntity entity = RegistrantKeyMap.getInstance().continueRegister(uuid);
        if (entity != null) {
            try {
                if (receiver.addUser(entity)) {
                    LOGGER.debug("register was successfully");
                    requestInfo.setSessionAttribute(RequestArgument.SESSION_LOGIN.getName(), entity.getUserName());
                    requestInfo.setSessionAttribute(RequestArgument.SESSION_ROLE.getName(), entity.getRole());

                    requestInfo.setRequestAttribute(RequestArgument.LOGIN_RESULT.getName(), "Register successfully");
                    path = Optional.of(PagesUrl.MAIN_PAGE.getPath());
                } else {
                    LOGGER.debug("register was not successfully");
                    requestInfo.setRequestAttribute(RequestArgument.LOGIN_RESULT.getName(), "Can't register");
                    path = Optional.of(PagesUrl.MAIN_PAGE.getPath());
                }
            }
            catch (ConnectorException e) {
                throw new CommandException("can't adding user", e);
            }
            return new RedirectGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
        }
        requestInfo.setRequestAttribute(RequestArgument.UUID_RESULT.getName(), "invalid uuid");
        return new ForwardGoTo(path.orElse(PagesUrl.REGISTER_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
