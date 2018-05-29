package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class AssemblageAddCustomSelectSpecification implements AssemblageCustomSelectSpecification {
    private static final String ASSEMBLAGE_ADD = String.format(
            "INSERT INTO %s.%s (%s, %s, %s, %s, %s) " +
            "VALUES (?, ?, ?, CURDATE(), " +
            "(SELECT %s.%s FROM %s WHERE %s.%s = ?));",
            ASSEMBLAGE_BD_SCHEME, ASSEMBLAGE_BD_TABLE,
            ASSEMBLAGE_NAME, ASSEMBLAGE_GENRE, ASSEMBLAGE_PRICE, ASSEMBLAGE_DATE, ASSEMBLAGE_OWNER_ID,
            USER_BD_TABLE, USER_ID,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME);

    private String assemblageName;
    private String assemblageGenre;
    private int assemblagePrice;
    private String userName;

    public AssemblageAddCustomSelectSpecification(String assemblageName, String assemblageGenre, int assemblagePrice, String userName) {
        this.assemblageName = assemblageName;
        this.assemblageGenre = assemblageGenre;
        this.assemblagePrice = assemblagePrice;
        this.userName = userName;
    }

    @Deprecated
    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        return null;
    }

    @Override
    public String toSqlClauses() {
        return ASSEMBLAGE_ADD;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[4];
        res[0] = assemblageName;
        res[1] = assemblageGenre;
        res[2] = Integer.toString(assemblagePrice);
        res[3] = userName;
        return res;
    }
}
