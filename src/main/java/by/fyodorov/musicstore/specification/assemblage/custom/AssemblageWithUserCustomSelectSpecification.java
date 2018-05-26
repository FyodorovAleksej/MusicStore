package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class AssemblageWithUserCustomSelectSpecification implements AssemblageCustomSelectSpecification {

    public static final String ASSEMBLAGE_NAME_KEY = "assemblageName";
    public static final String ASSEMBLAGE_GENRE_KEY = "assemblageGenre";
    public static final String ASSEMBLAGE_OWNER_KEY = "ownerName";
    public static final String ASSEMBLAGE_DATE_KEY = "assemblageDate";
    public static final String ASSEMBLAGE_PRICE_KEY = "assemblagePrice";
    public static final String ASSEMBLAGE_PRICE_SUMMARY_KEY = "assemblageSummaryPrice";

    private static final String TEMPO_TABLE = "X";
    private static final String SUMMARY_COLUMN = "summary";
    private static final String SELECT_ASSEMBLAGE_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, " +
                    "CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s " +
                    "LEFT JOIN %s ON %s.%s = ? " +
                    "JOIN (SELECT %s, %s FROM %s) as %s " +
                    "ON %s.%s = %s.%s ;",

            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_GENRE,
            TEMPO_TABLE, USER_USERNAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_DATE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ASSEMBLAGE_BD_TABLE,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            USER_USERNAME, USER_ID,
            USER_BD_TABLE,
            TEMPO_TABLE,
            TEMPO_TABLE, USER_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_OWNER_ID);

    private String userName;

    public AssemblageWithUserCustomSelectSpecification(String userName) {
        this.userName = userName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ASSEMBLAGE_INFO_FOR_USERNAME;
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
                String genre = set.getString(ASSEMBLAGE_GENRE.toString());
                String owner = set.getString(USER_USERNAME.toString());
                String date = set.getDate(ASSEMBLAGE_DATE.toString()).toString();
                String price = Integer.toString(set.getInt(ASSEMBLAGE_PRICE.toString()));
                String summary = Integer.toString(set.getInt(SUMMARY_COLUMN));

                arguments.put(ASSEMBLAGE_NAME_KEY, assemblageName);
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
