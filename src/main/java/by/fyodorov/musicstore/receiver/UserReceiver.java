package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.repository.AlbumRepository;
import by.fyodorov.musicstore.repository.AssemblageRepository;
import by.fyodorov.musicstore.repository.TrackRepository;
import by.fyodorov.musicstore.repository.UserRepository;
import by.fyodorov.musicstore.specification.album.custom.AlbumOfUserByNameCustomSelectSpecification;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageOfUserByNameCustomSelectSpecification;
import by.fyodorov.musicstore.specification.track.custom.TrackOfUserByNameCustomSelectSpecification;
import by.fyodorov.musicstore.specification.user.UserByNameAndEmailSpecification;
import by.fyodorov.musicstore.specification.user.UserByNameAndPasswordSpecification;
import by.fyodorov.musicstore.specification.user.UserByNameSpecification;
import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;
import by.fyodorov.musicstore.specification.user.custom.*;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import by.fyodorov.musicstore.view.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.model.UserBonusType.*;

/**
 * receiver for performing operations with users
 */
public class UserReceiver {
    private static final Logger LOGGER = LogManager.getLogger(UserReceiver.class);

    /**
     * checking user for login
     * @param name - login
     * @param password - password
     * @return - true - user confirm
     *          false - user doesn't confirm
     * @throws ConnectorException - if can't execute select query
     */
    public boolean checkUser(String name, String password) throws ConnectorException {
        LOGGER.debug("checking user = \"" + name + "\", password = \"" + password + "\"");
        UserRepository userRepository = new UserRepository();

        LinkedList<UserEntity> list = userRepository.prepareQuery(new UserByNameAndPasswordSpecification(name, password));

        userRepository.close();
        return !list.isEmpty();
    }

    /**
     * finding user by name to existing
     * @param name - user name
     * @return - true - user with this name is already exist
     *          false - username is free
     * @throws ConnectorException - if can't execute select query
     */
    public UserEntity findUser(String name) throws ConnectorException {
        LOGGER.debug("finding user = \"" + name + "\"");
        UserRepository userRepository = new UserRepository();
        LinkedList<UserEntity> list = userRepository.prepareQuery(new UserByNameSpecification(name));

        userRepository.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    /**
     * getting all users
     * @return - list of all users info
     * @throws ConnectorException - when can't execute select query
     */
    public LinkedList<UserView> findAllUsers() throws ConnectorException {
        LOGGER.debug("finding users");
        UserRepository userRepository = new UserRepository();
        LinkedList<UserView> users = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> argList = userRepository.customQuery(new UserAllCustomSelectSpecification());
            for (HashMap<String, String> map : argList) {
                users.add(new UserView(
                        Integer.valueOf(map.get(UserAllCustomSelectSpecification.USER_ID_KEY)),
                        map.get(UserAllCustomSelectSpecification.USER_NAME_KEY),
                        map.get(UserAllCustomSelectSpecification.USER_EMAIL_KEY),
                        map.get(UserAllCustomSelectSpecification.USER_ROLE_KEY)
                ));
            }
        } finally {
            userRepository.close();
        }
        return users;
    }

    /**
     * finding user by name and email for confirm registration
     * @param name - user name
     * @param email - user email
     * @return - user entity for continue registration
     * @throws ConnectorException - when can't execute select query
     */
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

    /**
     * validate userName
     * @param userName - userName
     * @return - is valid?
     */
    public boolean validateUser(String userName) {
        RequestParameterValidator validator = new RequestParameterValidator();
        return validator.validateUserName(userName);
    }

    /**
     * validate password
     * @param password - password
     * @return - is valid?
     */
    public boolean validatePassword(String password) {
        RequestParameterValidator validator = new RequestParameterValidator();
        return validator.validateUserName(password);
    }

    /**
     * vaidate email
     * @param email - email
     * @return - is valid?
     */
    public boolean validateEmail(String email) {
        RequestParameterValidator validator = new RequestParameterValidator();
        return validator.validateEmail(email);
    }

    /**
     * adding new user
     * @param entity - entity of user
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean addUser(UserEntity entity) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        boolean result;
        try {
            UserCustomUpdateSpecification specification = new UserAddCustomUpdateSpecification(entity.getUserName(), entity.getEmail(), entity.getPassword());
            userRepository.prepareUpdate(specification);
            result = true;
        } catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        } finally {
            userRepository.close();
        }
        return result;
    }

    /**
     * buy track
     * @param userName - user name of current user
     * @param trackName - track name to buy
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean buyTrack(String userName, String trackName) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        TrackRepository trackRepository = new TrackRepository();
        boolean result = false;
        TrackRepository.modifyLock();
        UserRepository.modifyLock();
        try {
            LinkedList<HashMap<String, String>> tracks = trackRepository.customQuery(new TrackOfUserByNameCustomSelectSpecification(userName));
            boolean exist = findInMap(tracks, trackName, TrackOfUserByNameCustomSelectSpecification.TRACK_NAME_KEY);
            if (!exist) {
                LinkedList<HashMap<String, String>> summary = userRepository.customQuery(new UserCashAfterTrackOperationCustomSelectSpecification(userName, trackName));
                if (!summary.isEmpty()) {
                    String cashResult = summary.getFirst().get(UserCashAfterTrackOperationCustomSelectSpecification.SUMMARY_KEY);
                    result = userRepository.prepareUpdate(new UserUpdateCashAndBonusCustomUpdateSpecification(userName, cashResult, USER_BONUS_TRACK)) > 0;
                    result = result && userRepository.prepareUpdate(new UserBuyTrackCustomUpdateSpecification(userName, trackName)) > 0;
                }
            }
        } catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        } finally {
            TrackRepository.modifyUnlock();
            UserRepository.modifyUnlock();
            trackRepository.close();
            userRepository.close();
        }
        return result;
    }

    /**
     * buy album
     * @param userName - user name of current user
     * @param albumName - album name to buy
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean buyAlbum(String userName, String albumName) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        AlbumRepository albumRepository = new AlbumRepository();
        boolean result = false;
        AlbumRepository.modifyLock();
        UserRepository.modifyLock();
        try {
            LinkedList<HashMap<String, String>> albums = albumRepository.customQuery(new AlbumOfUserByNameCustomSelectSpecification(userName));
            boolean exist = findInMap(albums, albumName, AlbumOfUserByNameCustomSelectSpecification.ALBUM_NAME_KEY);
            if (!exist) {
                LinkedList<HashMap<String, String>> summary = userRepository.customQuery(new UserCashAfterAlbumOperationCustomSelectSpecification(userName, albumName));
                if (!summary.isEmpty()) {
                    String cashResult = summary.getFirst().get(UserCashAfterAlbumOperationCustomSelectSpecification.SUMMARY_KEY);
                    result = userRepository.prepareUpdate(new UserUpdateCashAndBonusCustomUpdateSpecification(userName, cashResult, USER_BONUS_ALBUM)) > 0;
                    result = result && userRepository.prepareUpdate(new UserBuyAlbumCustomUpdateSpecification(userName, albumName)) > 0;
                    result = result && userRepository.prepareUpdate(new UserInsertTracksFromAlbumCustomUpdateSpecification(userName, albumName)) >= 0;
                }
            }
        } catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        } finally {
            AlbumRepository.modifyUnlock();
            UserRepository.modifyUnlock();
            albumRepository.close();
            userRepository.close();
        }
        return result;
    }

    /**
     * buy assemblage
     * @param userName - user name of current user
     * @param assemblageName - assemblage name to buy
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean buyAssemblage(String userName, String assemblageName) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result = false;
        AssemblageRepository.modifyLock();
        UserRepository.modifyLock();
        try {
            LinkedList<HashMap<String, String>> assemblages = assemblageRepository.customQuery(new AssemblageOfUserByNameCustomSelectSpecification(userName));
            boolean exist = findInMap(assemblages, assemblageName, AssemblageOfUserByNameCustomSelectSpecification.ASSEMBLAGE_NAME_KEY);
            if (!exist) {
                LinkedList<HashMap<String, String>> summary = userRepository.customQuery(new UserCashAfterAssemblageOperationCustomSelectSpecification(userName, assemblageName));
                if (!summary.isEmpty()) {
                    String cashResult = summary.getFirst().get(UserCashAfterAssemblageOperationCustomSelectSpecification.SUMMARY_KEY);
                    result = userRepository.prepareUpdate(new UserUpdateCashAndBonusCustomUpdateSpecification(userName, cashResult, USER_BONUS_ASSEMBLAGE)) > 0;
                    result = result && userRepository.prepareUpdate(new UserBuyAssemblageCustomUpdateSpecification(userName, assemblageName)) > 0;
                    result = result && userRepository.prepareUpdate(new UserInsertTracksFromAssemblageCustomUpdateSpecification(userName, assemblageName)) >= 0;

                }
            }
        } catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        } finally {
            AssemblageRepository.modifyUnlock();
            UserRepository.modifyUnlock();
            assemblageRepository.close();
            userRepository.close();
        }
        return result;
    }

    /**
     * edit user
     * @param userName - user name to edit
     * @param role - new role of user
     * @param bonus - new bonus of user
     * @param discount - new discount of user
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean updateUser(String userName, String role, String bonus, int discount) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        boolean result;
        try {
            result = userRepository.prepareUpdate(new UserEditCustomUpdateSpecification(userName, role, bonus, discount)) > 0;
        } finally {
            userRepository.close();
        }
        return result;
    }

    /**
     * finding key and value in map
     * @param mapList - list of map
     * @param value - value to find
     * @param key - key to find;
     * @return - true - value with this key is exist
     *          false - value with this key isn't exist
     */
    private boolean findInMap(LinkedList<HashMap<String, String>> mapList, String value, String key) {
        for (HashMap<String, String> map : mapList) {
            if (value.equals(map.get(key))) {
                return true;
            }
        }
        return false;
    }
}
