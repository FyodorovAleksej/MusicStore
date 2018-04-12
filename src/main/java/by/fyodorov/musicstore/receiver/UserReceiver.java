package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.repository.UserRepository;
import by.fyodorov.musicstore.specification.user.UserByNameAndEmailSpecification;
import by.fyodorov.musicstore.specification.user.UserByNameAndPasswordSpecification;
import by.fyodorov.musicstore.specification.user.UserByNameSpecification;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class UserReceiver implements CommandReceiver {
    private static Logger LOGGER = LogManager.getLogger(UserReceiver.class);

    public boolean checkUser(String name, String password) throws ConnectorException {
        LOGGER.debug("checking user = \"" + name + "\", password = \"" + password + "\"");
        UserRepository userRepository = new UserRepository();

        LinkedList<UserEntity> list = userRepository.prepareQuery(new UserByNameAndPasswordSpecification(name, password));

        userRepository.close();
        return !list.isEmpty();

        //return (STUB_NAME.equalsIgnoreCase(name) && STUB_PASSWORD.equalsIgnoreCase(password));
    }

    public UserEntity findUser(String name) throws ConnectorException {
        LOGGER.debug("finding user = \"" + name + "\"");
        UserRepository userRepository = new UserRepository();

        LinkedList<UserEntity> list = userRepository.prepareQuery(new UserByNameSpecification(name));

        userRepository.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }

        /*
        if (STUB_NAME.equalsIgnoreCase(name)) {
            return new UserEntity(0, STUB_NAME, STUB_EMAIL, STUB_ROLE, STUB_CASH, STUB_BONUS, STUB_DISCOUNT, STUB_PASSWORD);
        }*/
        return null;
    }

    public UserEntity findUserByMail(String name, String email) throws ConnectorException {
        LOGGER.debug("finding user by name and email = \"" + name + "\", \"" + email + "\"");
        UserRepository userRepository = new UserRepository();

        LinkedList<UserEntity> list = userRepository.prepareQuery(new UserByNameAndEmailSpecification(name, email));

        userRepository.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    public boolean validateUser(String userName) {
        RequestParameterValidator validator = new RequestParameterValidator();
        return validator.validateUserName(userName);
    }

    public boolean validatePassword(String password) {
        RequestParameterValidator validator = new RequestParameterValidator();
        return validator.validateUserName(password);
    }

    public boolean validateEmail(String email) {
        RequestParameterValidator validator = new RequestParameterValidator();
        return validator.validateEmail(email);
    }

    public boolean addUser(String login, String email, String password) throws ConnectorException {
        return addUser(new UserEntity(login, email, 0, 0, password));
    }

    public boolean addUser(UserEntity entity) throws ConnectorException {

        UserRepository userRepository = new UserRepository();
        boolean result;
        try {
            userRepository.add(entity);
            result = true;
        }
        catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        }
        userRepository.close();
        return result;
    }
}
