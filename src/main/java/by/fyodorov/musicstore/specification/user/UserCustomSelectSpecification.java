package by.fyodorov.musicstore.specification.user;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class UserCustomSelectSpecification implements UserRepositorySpecification {
    public static final String SUMMARY_KEY = "summary";
    public static final String SUMMARY_TABLE = "SUMMARY";

    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String trackPrice = Integer.toString(set.getInt(SUMMARY_TABLE));
                arguments.put(SUMMARY_KEY, trackPrice);

                result.add(arguments);
            }
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from User DB", e);
        }
        return result;
    }
}
