package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.repository.AlbumRepository;
import by.fyodorov.musicstore.repository.AssemblageRepository;
import by.fyodorov.musicstore.repository.TrackRepository;
import by.fyodorov.musicstore.repository.UserRepository;
import by.fyodorov.musicstore.specification.album.custom.AlbumOfUserByNameCustomSelect;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageOfUserByNameCustomSelect;
import by.fyodorov.musicstore.specification.track.custom.TrackOfUserByNameCustomSelect;
import by.fyodorov.musicstore.specification.user.UserByNameAndEmailSpecification;
import by.fyodorov.musicstore.specification.user.UserByNameAndPasswordSpecification;
import by.fyodorov.musicstore.specification.user.UserByNameSpecification;
import by.fyodorov.musicstore.specification.user.custom.*;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import by.fyodorov.musicstore.view.TrackWithoutPriceView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.model.UserBonusEnum.*;

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
        finally {
            userRepository.close();
        }
        return result;
    }

    public boolean buyTrack(String userName, String trackName) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        TrackRepository trackRepository = new TrackRepository();
        boolean result = false;
        TrackRepository.modifyLock();
        UserRepository.modifyLock();
        try {
            LinkedList<HashMap<String, String>> tracks = trackRepository.customQuery(new TrackOfUserByNameCustomSelect(userName));
            boolean exist = findInMap(tracks, trackName, TrackOfUserByNameCustomSelect.TRACK_NAME_KEY);
            if (!exist) {
                LinkedList<HashMap<String, String>> summary = userRepository.customQuery(new UserCashAfterTrackOperationCustomSelect(userName, trackName));
                if (!summary.isEmpty()) {
                    String cashResult = summary.getFirst().get(UserCashAfterTrackOperationCustomSelect.SUMMARY_KEY);
                    if (Integer.valueOf(cashResult) >= 0) {
                        result = userRepository.prepareUpdate(new UserUpdateCashAndBonusCustomSpecification(userName, cashResult, USER_BONUS_TRACK)) > 0;
                        result = result && userRepository.prepareUpdate(new UserBuyTrackCustomSpecification(userName, trackName)) > 0;
                    }
                }
            }
        }
        catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        }
        finally {
            TrackRepository.modifyUnlock();
            UserRepository.modifyUnlock();
            trackRepository.close();
            userRepository.close();
        }
        return result;
    }

    public boolean buyAlbum(String userName, String albumName) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        AlbumRepository albumRepository = new AlbumRepository();
        boolean result = false;
        AlbumRepository.modifyLock();
        UserRepository.modifyLock();
        try {
            LinkedList<HashMap<String, String>> albums = albumRepository.customQuery(new AlbumOfUserByNameCustomSelect(userName));
            boolean exist = findInMap(albums, albumName, AlbumOfUserByNameCustomSelect.ALBUM_NAME_KEY);
            if (!exist) {
                LinkedList<HashMap<String, String>> summary = userRepository.customQuery(new UserCashAfterAlbumOperationCustomSelect(userName, albumName));
                if (!summary.isEmpty()) {
                    String cashResult = summary.getFirst().get(UserCashAfterAlbumOperationCustomSelect.SUMMARY_KEY);
                    if (Integer.valueOf(cashResult) >= 0) {
                        result = userRepository.prepareUpdate(new UserUpdateCashAndBonusCustomSpecification(userName, cashResult, USER_BONUS_ALBUM)) > 0;
                        result = result && userRepository.prepareUpdate(new UserBuyAlbumCustomSpecification(userName, albumName)) > 0;
                    }
                }
            }
        }
        catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        }
        finally {
            AlbumRepository.modifyUnlock();
            UserRepository.modifyUnlock();
            albumRepository.close();
            userRepository.close();
        }
        return result;
    }

    public boolean buyAssemblage(String userName, String assemblageName) throws ConnectorException {
        UserRepository userRepository = new UserRepository();
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result = false;
        AssemblageRepository.modifyLock();
        UserRepository.modifyLock();
        try {
            LinkedList<HashMap<String, String>> assemblages = assemblageRepository.customQuery(new AssemblageOfUserByNameCustomSelect(userName));
            boolean exist = findInMap(assemblages, assemblageName, AssemblageOfUserByNameCustomSelect.ASSEMBLAGE_NAME_KEY);
            if (!exist) {
                LinkedList<HashMap<String, String>> summary = userRepository.customQuery(new UserCashAfterAssemblageOperationCustomSelect(userName, assemblageName));
                if (!summary.isEmpty()) {
                    String cashResult = summary.getFirst().get(UserCashAfterAssemblageOperationCustomSelect.SUMMARY_KEY);
                    if (Integer.valueOf(cashResult) >= 0) {
                        result = userRepository.prepareUpdate(new UserUpdateCashAndBonusCustomSpecification(userName, cashResult, USER_BONUS_ASSEMBLAGE)) > 0;
                        result = result && userRepository.prepareUpdate(new UserBuyAssemblageCustomSpecification(userName, assemblageName)) > 0;
                    }
                }
            }
        }
        catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        }
        finally {
            AssemblageRepository.modifyUnlock();
            UserRepository.modifyUnlock();
            assemblageRepository.close();
            userRepository.close();
        }
        return result;
    }

    private boolean findInMap(LinkedList<HashMap<String, String>> mapList, String value, String key) {
        for (HashMap<String, String> map : mapList) {
            if (value.equals(map.get(key))) {
                return true;
            }
        }
        return false;
    }
}
