package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserAllCustomSelect implements UserCustomSelectSpecification {
    public static final String USER_ID_KEY = "userId";
    public static final String USER_NAME_KEY = "userName";
    public static final String USER_EMAIL_KEY = "userEmail";
    public static final String USER_ROLE_KEY = "userRole";

    private static final String SELECT_USERS = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s " +
            "FROM %s.%s ;",
            USER_BD_TABLE, USER_ID,
            USER_BD_TABLE, USER_USERNAME,
            USER_BD_TABLE, USER_EMAIL,
            USER_BD_TABLE, USER_ROLE,
            USER_BD_SCHEME, USER_BD_TABLE);

    @Override
    public String toSqlClauses() {
        return SELECT_USERS;
    }

    @Override
    public String[] getArguments() {
        return new String[0];
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String id = Integer.toString(set.getInt(USER_ID.toString()));
                String userName = set.getString(USER_USERNAME.toString());
                String email = set.getString(USER_EMAIL.toString());
                String role = set.getString(USER_ROLE.toString());

                arguments.put(USER_ID_KEY, id);
                arguments.put(USER_NAME_KEY, userName);
                arguments.put(USER_EMAIL_KEY, email);
                arguments.put(USER_ROLE_KEY, role);

                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from User DB", e);
        }
        return result;
    }
}
