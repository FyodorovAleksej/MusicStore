package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.specification.user.custom.UserCustomSelectSpecification;
import by.fyodorov.musicstore.specification.user.UserRepositorySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.user.UserRepositoryConstant.*;

public class UserRepository {
    private static Logger LOGGER = LogManager.getLogger(UserRepository.class);

    private static final String ADD_USER_SQL =
            "INSERT INTO " + USER_BD_SCHEME + "." + USER_BD_TABLE + " ("
                    + USER_USERNAME + ", "
                    + USER_EMAIL + ", "
                    + USER_ROLE + ", "
                    + USER_CASH + ", "
                    + USER_BONUS + ", "
                    + USER_DISCOUNT + ", "
                    + USER_PASSWORD + ") " +
                    "VALUES (\'%1$s\', \'%2$s\', \'%3$s\', \'%4$s\', %5$s, \'%6$s\', SHA(\'%7$s\'));";

    private static final String REMOVE_USER_SQL =
            "DELETE FROM " + USER_BD_SCHEME + "." + USER_BD_TABLE + " WHERE " +
                    USER_ID + " = \'%1$s\';";

    private SqlUtil util;

    public UserRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public void add(UserEntity user) throws ConnectorException {
        LOGGER.debug("adding new user");
        util.execUpdate(String.format(ADD_USER_SQL,
                user.getUserName(),
                user.getEmail(),
                user.getRole(),
                user.getCash(),
                user.getBonus(),
                user.getDiscount(),
                user.getPassword()));
    }

    public void remove(UserEntity user) throws ConnectorException {
        LOGGER.debug("remove user");
        util.execUpdate(String.format(REMOVE_USER_SQL, user.getId()));
    }
    public void update(UserEntity user) {
        LOGGER.debug("update user");
        //util.execUpdate();
    }

    /*
    public List<UserEntity> query(UserRepositorySpecification specification) throws ConnectorException {
        ResultSet set = util.exec(specification.toSqlClauses());
        LinkedList<UserEntity> list = new LinkedList<>();
        return list;
    }*/

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
        }
        catch (SQLException e) {
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


    public void close() throws ConnectorException {
        util.closeConnection();
    }
}
