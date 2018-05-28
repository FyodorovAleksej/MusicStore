package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class AssemblageInfoForUserCustomSelectSpecification implements AssemblageCustomSelectSpecification {
    private static final String SUMMARY_COLUMN = "summary";
    private static final String TEMP_USER = "X";

    private static final String SELECT_ASSEMBLAGE_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, " +
                    "CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s LEFT JOIN %s ON %s.%s = ? " +
                    "JOIN %s AS %s ON %s.%s = %s.%s " +
                    "WHERE %s.%s = ? ;",

            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_GENRE,
            USER_BD_TABLE, USER_USERNAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_DATE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ASSEMBLAGE_BD_TABLE, USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            USER_BD_TABLE,
            TEMP_USER,
            TEMP_USER, USER_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_OWNER_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME);

    public static final String ASSEMBLAGE_NAME_KEY = "trackName";
    public static final String ASSEMBLAGE_GENRE_KEY = "trackGenre";
    public static final String ASSEMBLAGE_OWNER_KEY = "ownerName";
    public static final String ASSEMBLAGE_DATE_KEY = "trackDate";
    public static final String ASSEMBLAGE_PRICE_KEY = "trackPrice";
    public static final String ASSEMBLAGE_PRICE_SUMMARY_KEY = "trackSummaryPrice";

    private String assemblageName;
    private String userName;

    public AssemblageInfoForUserCustomSelectSpecification(String assemblageName, String userName) {
        this.userName = userName;
        this.assemblageName = assemblageName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ASSEMBLAGE_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = assemblageName;
        return result;
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String trackName = set.getString(ASSEMBLAGE_NAME.toString());
                String genre = set.getString(ASSEMBLAGE_GENRE.toString());
                String owner = set.getString(USER_USERNAME.toString());
                String date = set.getDate(ASSEMBLAGE_DATE.toString()).toString();
                String price = Integer.toString(set.getInt(ASSEMBLAGE_PRICE.toString()));
                String summary = Integer.toString(set.getInt(SUMMARY_COLUMN));

                arguments.put(ASSEMBLAGE_NAME_KEY, trackName);
                arguments.put(ASSEMBLAGE_GENRE_KEY, genre);
                arguments.put(ASSEMBLAGE_OWNER_KEY, owner);
                arguments.put(ASSEMBLAGE_DATE_KEY, date);
                arguments.put(ASSEMBLAGE_PRICE_KEY, price);
                arguments.put(ASSEMBLAGE_PRICE_SUMMARY_KEY, summary);

                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Assemblage DB", e);
        }
        return result;
    }
}
