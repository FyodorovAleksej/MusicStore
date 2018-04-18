package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.AssemblageEntity;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageCustomSelectSpecification;
import by.fyodorov.musicstore.specification.assemblage.AssemblageRepositorySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryConstant.*;

public class AssemblageRepository {
    private static Logger LOGGER = LogManager.getLogger(AssemblageRepository.class);
    private static Lock MODIFY_LOCK = new ReentrantLock();

    private static final String ADD_ASSEMBLAGE_SQL =
            "INSERT INTO " + ASSEMBLAGE_BD_SCHEME + "." + ASSEMBLAGE_BD_TABLE + " ("
                    + ASSEMBLAGE_NAME + ", "
                    + ASSEMBLAGE_GENRE + ", "
                    + ASSEMBLAGE_PRICE + ", "
                    + ASSEMBLAGE_DATE + ", "
                    + ASSEMBLAGE_OWNER_ID + ") " +
                    "VALUES (\'%1$s\', \'%2$s\', \'%3$s\', \'%4$s\', %5$s);";

    private static final String REMOVE_ASSEMBLAGE_SQL =
            "DELETE FROM " + ASSEMBLAGE_BD_SCHEME + "." + ASSEMBLAGE_BD_TABLE + " WHERE " +
                    ASSEMBLAGE_ID + " = %1$s;";

    private SqlUtil util;

    public AssemblageRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public void add(AssemblageEntity assemblage) throws ConnectorException {
        LOGGER.debug("adding new assemblage");
        util.execUpdate(String.format(ADD_ASSEMBLAGE_SQL,
                assemblage.getName(),
                assemblage.getGenre(),
                assemblage.getPrice(),
                assemblage.getDate(),
                assemblage.getUserId()));
    }

    public void remove(AssemblageEntity assemblage) throws ConnectorException {
        LOGGER.debug("remove assemblage");
        util.execUpdate(String.format(REMOVE_ASSEMBLAGE_SQL, assemblage.getId()));
    }
    public void update(AssemblageEntity assemblage) {
        LOGGER.debug("update assemblage");
        //util.execUpdate();
    }

    /*
    public List<UserEntity> query(UserRepositorySpecification specification) throws ConnectorException {
        ResultSet set = util.exec(specification.toSqlClauses());
        LinkedList<UserEntity> list = new LinkedList<>();
        return list;
    }*/

    public LinkedList<AssemblageEntity> prepareQuery(AssemblageRepositorySpecification specification) throws ConnectorException {
        LOGGER.debug("custom user query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        LinkedList<AssemblageEntity> list = new LinkedList<>();
        try {
            while (set.next()) {
                int id = set.getInt(ASSEMBLAGE_ID.toString());
                String name = set.getString(ASSEMBLAGE_NAME.toString());
                String genre = set.getString(ASSEMBLAGE_GENRE.toString());
                int price = set.getInt(ASSEMBLAGE_PRICE.toString());
                Date date = set.getDate(ASSEMBLAGE_DATE.toString());
                int ownerId = set.getInt(ASSEMBLAGE_OWNER_ID.toString());

                list.add(new AssemblageEntity(id, name, genre, price, date, ownerId));
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Assemblage DB", e);
        }
        list.forEach(LOGGER::debug);
        return list;
    }

    public LinkedList<HashMap<String, String>> customQuery(AssemblageCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
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
