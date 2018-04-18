package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryConstant.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryConstant.*;

public class AssemblageOfUserByNameCustomSelect implements AssemblageCustomSelectSpecification {
    public static final String ASSEMBLAGE_NAME_KEY = "assemblageName";

    private static final String SELECT_USERS_ASSEMBLAGES = String.format(
            "SELECT %s.%s FROM %s " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "WHERE %s.%s = ? ;\n",
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
            USER_HAS_ASSEMBLAGES_BD_TABLE,
            ASSEMBLAGE_BD_TABLE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_ID,
            USER_HAS_ASSEMBLAGES_BD_TABLE, USER_HAS_ASSEMBLAGES_ASSEMBLAGE_ID,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_ID,
            USER_HAS_ASSEMBLAGES_BD_TABLE, USER_HAS_ASSEMBLAGES_USER_ID,
            USER_BD_TABLE, USER_USERNAME);

    private String userName;

    public AssemblageOfUserByNameCustomSelect(String userName) {
        this.userName = userName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_USERS_ASSEMBLAGES;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[1];
        result[0] = userName;
        return result;
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String trackName = set.getString(ASSEMBLAGE_NAME.toString());
                arguments.put(ASSEMBLAGE_NAME_KEY, trackName);

                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Assemblage DB", e);
        }
        return result;
    }
}
