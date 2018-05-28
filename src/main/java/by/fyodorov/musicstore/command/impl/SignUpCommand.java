package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.receiver.UserReceiver;
import by.fyodorov.musicstore.util.MailException;
import by.fyodorov.musicstore.util.MailSender;
import by.fyodorov.musicstore.util.RegistrantKeyMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);
    private UserReceiver receiver;

    public SignUpCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }


    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        LOGGER.debug("performing Sign Up command");

        String login = requestInfo.getParameter(RequestArgument.LOGIN.getName());
        String email = requestInfo.getParameter(RequestArgument.EMAIL.getName());
        String password = requestInfo.getParameter(RequestArgument.PASSWORD.getName());
        String repPassword = requestInfo.getParameter(RequestArgument.REP_PASSWORD.getName());
        RegistrantKeyMap keyMap = RegistrantKeyMap.getInstance();

        try {
            if (!validInput(login, email, password)) {
                requestInfo.setRequestAttribute(RequestArgument.SIGN_UP_RESULT.getName(), "input wasn't valid");
                return new ForwardGoTo(PagesUrl.SIGN_UP_PAGE.getPath());
            }

            if (!validPassword(password, repPassword)) {
                requestInfo.setRequestAttribute(RequestArgument.SIGN_UP_RESULT.getName(), "passwords are not same");
                return new ForwardGoTo(PagesUrl.SIGN_UP_PAGE.getPath());
            }

            if (!validExist(login, email, keyMap)) {
                requestInfo.setRequestAttribute(RequestArgument.SIGN_UP_RESULT.getName(), "user already exist");
                return new ForwardGoTo(PagesUrl.SIGN_UP_PAGE.getPath());
            }

            LOGGER.debug("login, password, email is valid");
            String uuid = keyMap.addValue(new UserEntity(login, email, 0, 0, password));
            LOGGER.debug("uuid = " + uuid);

            path = Optional.of(PagesUrl.REGISTER_PAGE.getPath());
            try {
                MailSender sender = new MailSender(ContextParameter.getInstance().getContextParam(InitParameter.MAIL_INIT.toString()));
                sender.sendUrl("register: ","register" + "?uuid=" + uuid, email);
            } catch (MailException e) {
                LOGGER.catching(e);
                requestInfo.setRequestAttribute(RequestArgument.SIGN_UP_RESULT.getName(), e.getMessage());
                keyMap.continueRegister(uuid);
                return new ForwardGoTo(PagesUrl.SIGN_UP_PAGE.getPath());
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't check login", e);
        }
        return new RedirectGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }

    private boolean validInput(String login, String email, String password) {
        return  receiver.validateUser(login)
                && receiver.validateEmail(email)
                && receiver.validatePassword(password);
    }

    private boolean validPassword(String password, String repPassword) {
        return password != null && password.equals(repPassword);
    }

    private boolean validExist(String login, String email, RegistrantKeyMap keyMap) throws ConnectorException {
        return  receiver.findUserByMail(login, email) == null
                && !keyMap.checkLogin(login)
                && !keyMap.checkEmail(email);
    }
}
