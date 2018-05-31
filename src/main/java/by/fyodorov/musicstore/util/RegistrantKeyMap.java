package by.fyodorov.musicstore.util;

import by.fyodorov.musicstore.model.UserEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegistrantKeyMap {
    private static final int MAX_COUNT = 10;

    private static RegistrantKeyMap instance;
    private static Lock instanceLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    private HashMap<String, UserEntity> waitRegisterMap;

    private RegistrantKeyMap() {
        this.waitRegisterMap = new HashMap<>();
    }

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

    public UserEntity continueRegister(String uuid) {
        Optional<UserEntity> optional = Optional.ofNullable(waitRegisterMap.remove(uuid));
        return optional.orElse(null);
    }

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
