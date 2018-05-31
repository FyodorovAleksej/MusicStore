package by.fyodorov.musicstore.specification.assemblage;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.USER_USERNAME;

public abstract class AssemblageCustomSelectSpecification implements AssemblageRepositorySpecification {
    public static final String ASSEMBLAGE_NAME_KEY = "assemblageName";
    public static final String ASSEMBLAGE_GENRE_KEY = "assemblageGenre";
    public static final String ASSEMBLAGE_OWNER_KEY = "ownerName";
    public static final String ASSEMBLAGE_DATE_KEY = "assemblageDate";
    public static final String ASSEMBLAGE_PRICE_KEY = "assemblagePrice";
    public static final String ASSEMBLAGE_PRICE_SUMMARY_KEY = "assemblageSummaryPrice";

    public static final String SUMMARY_COLUMN = "summary";

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
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from Assemblage DB", e);
        }
        return result;
    }
}
