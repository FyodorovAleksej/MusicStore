package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;
import by.fyodorov.musicstore.specification.user.UserRepositorySpecification;
import by.fyodorov.musicstore.specification.user.UserCustomSelectSpecification;
import by.fyodorov.musicstore.specification.user.custom.UserAddCustomUpdateSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserRepository {
    private static Logger LOGGER = LogManager.getLogger(UserRepository.class);

    private static final Lock MODIFY_LOCK = new ReentrantLock();

    private SqlUtil util;

    public UserRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public LinkedList<UserEntity> prepareQuery(UserRepositorySpecification specification) throws ConnectorException {
        LOGGER.debug("custom user query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        LinkedList<UserEntity> list = new LinkedList<>();

        try {
            while (set.next()) {
                int id = set.getInt(USER_ID.toString());
                String userName = set.getString(USER_USERNAME.toString());
                String email = set.getString(USER_EMAIL.toString());
                String role = set.getString(USER_ROLE.toString());
                int cash = set.getInt(USER_CASH.toString());
                String bonus = set.getString(USER_BONUS.toString());
                int discount = set.getInt(USER_DISCOUNT.toString());
                String password = set.getString(USER_PASSWORD.toString());

                list.add(new UserEntity(id, userName, email, role, cash, bonus, discount, password));
            }
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from User DB", e);
        }
        list.forEach(LOGGER::debug);
        return list;
    }

    public LinkedList<HashMap<String, String>> customQuery(UserCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }

    public int prepareUpdate(UserCustomUpdateSpecification specification) throws ConnectorException {
        LOGGER.debug("custom update");
        return util.execUpdatePrepare(specification.toSqlClauses(), specification.getArguments());
    }

    public void close() throws ConnectorException {
        util.closeConnection();
    }

    public static void modifyLock() {
        MODIFY_LOCK.lock();
    }

    public static void modifyUnlock() {
        MODIFY_LOCK.unlock();
    }
}
