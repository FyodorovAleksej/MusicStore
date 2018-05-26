package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class AssemblageOfUserByNameCustomSelect implements AssemblageCustomSelectSpecification {
    public static final String ASSEMBLAGE_NAME_KEY = "assemblageName";
    public static final String ASSEMBLAGE_GENRE_KEY = "assemblageGenre";
    public static final String ASSEMBLAGE_DATE_KEY = "assemblageDate";
    public static final String ASSEMBLAGE_OWNER_KEY = "assemblageOwner";
    private static final String TEMP_USER = "X";

    private static final String SELECT_USERS_ASSEMBLAGES = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s FROM %s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "JOIN %s AS %s ON %s.%s = %s.%s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "WHERE %s.%s = ? ;\n",
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_GENRE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_DATE,
            TEMP_USER, USER_USERNAME,
            USER_HAS_ASSEMBLAGES_BD_TABLE,
            ASSEMBLAGE_BD_TABLE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_ID,
            USER_HAS_ASSEMBLAGES_BD_TABLE, USER_HAS_ASSEMBLAGES_ASSEMBLAGE_ID,
            USER_BD_TABLE,
            TEMP_USER,
            USER_HAS_ASSEMBLAGES_BD_TABLE, USER_HAS_ASSEMBLAGES_OWNER_ID,
            TEMP_USER, USER_ID,
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
                String assemblageName = set.getString(ASSEMBLAGE_NAME.toString());
                String assemblageGenre = set.getString(ASSEMBLAGE_GENRE.toString());
                String assemblageDate = set.getString(ASSEMBLAGE_DATE.toString());
                String assemblageOwner = set.getString(USER_USERNAME.toString());

                arguments.put(ASSEMBLAGE_NAME_KEY, assemblageName);
                arguments.put(ASSEMBLAGE_GENRE_KEY, assemblageGenre);
                arguments.put(ASSEMBLAGE_DATE_KEY, assemblageDate);
                arguments.put(ASSEMBLAGE_OWNER_KEY, assemblageOwner);

                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Assemblage DB", e);
        }
        return result;
    }
}
