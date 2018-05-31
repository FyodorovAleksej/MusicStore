package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserAddCustomUpdateSpecification implements UserCustomUpdateSpecification {
    private static final String ADD_USER_SQL =
            String.format(
                    "INSERT INTO %s.%s (%s, %s, %s, %s, %s, %s, %s) " +
                            "VALUES (?, ?, 'user', 0, NULL, 0, SHA(?));",
                    USER_BD_SCHEME, USER_BD_TABLE,
                    USER_USERNAME, USER_EMAIL,
                    USER_ROLE, USER_CASH,
                    USER_BONUS, USER_DISCOUNT,
                    USER_PASSWORD);

    private String userName;
    private String email;
    private String password;

    public UserAddCustomUpdateSpecification(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toSqlClauses() {
        return ADD_USER_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[3];
        result[0] = userName;
        result[1] = email;
        result[2] = password;
        return result;
    }
}
