package by.fyodorov.musicstore.util;

import by.fyodorov.musicstore.model.UserEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * map for saving users, who not confirm email. Singleton
 */
public class RegistrantKeyMap {
    private static final int MAX_COUNT = 10;

    private static RegistrantKeyMap instance;
    private static Lock instanceLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    private HashMap<String, UserEntity> waitRegisterMap;

    /**
     * creating key map
     */
    private RegistrantKeyMap() {
        this.waitRegisterMap = new HashMap<>();
    }

    /**
     * getting singleton
     * @return - instance of key map
     */
    public static RegistrantKeyMap getInstance() {
        if (!isCreated.get()) {
            instanceLock.lock();
            if (instance == null) {
                instance = new RegistrantKeyMap();
                isCreated.set(true);
            }
            instanceLock.unlock();
        }
        return instance;
    }

    /**
     * adding user for saving
     * @param entity - entity of user
     * @return - uuid for confirm email. Used as key to storage in map
     */
    public String addValue(UserEntity entity) {
        String uuid;
        int count = 0;
        do {
            uuid = UUID.randomUUID().toString();
            count++;
        } while (waitRegisterMap.containsKey(uuid) && count < MAX_COUNT);

        if (count == MAX_COUNT) {
            throw new RuntimeException("can't generate uuid");
        }

        waitRegisterMap.put(uuid, entity);
        return uuid;
    }

    /**
     * getting user entity from map
     * @param uuid - uuid to continue
     * @return - stored user entity, if uuid exist. And null - if uuid not exist in map
     */
    public UserEntity continueRegister(String uuid) {
        Optional<UserEntity> optional = Optional.ofNullable(waitRegisterMap.remove(uuid));
        return optional.orElse(null);
    }

    /**
     * checking login in map
     * @param login - login for checking
     * @return - true - if login exist in map
     *          false - if login not exist in map
     */
    public boolean checkLogin(String login) {
        if (login == null) {
            return false;
        }
        Collection<UserEntity> collection = waitRegisterMap.values();
        for (UserEntity user : collection) {
            if (user.getUserName().equals(login)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checking email in map
     * @param email - email for checking
     * @return - true - if email exist in map
     *          false - if email not exist in map
     */
    public boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        Collection<UserEntity> collection = waitRegisterMap.values();
        for (UserEntity user : collection) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
