package by.fyodorov.musicstore.specification.performer.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;

public class PerformerAllCustomSelectSpecification implements PerformerCustomSelectSpecification {
    public static final String PERFORMER_NAME_KEY = "performerName";
    private static final String SELECT_SQL =
            String.format("SELECT %s " +
                            "FROM %s.%s ;",
                    PERFORMER_NAME,
                    PERFORMER_BD_SCHEME, PERFORMER_BD_TABLE);

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String performerName = set.getString(PERFORMER_NAME.toString());

                arguments.put(PERFORMER_NAME_KEY, performerName);

                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Performer DB", e);
        }
        return result;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_SQL;
    }

    @Override
    public String[] getArguments() {
        return new String[0];
    }
}
