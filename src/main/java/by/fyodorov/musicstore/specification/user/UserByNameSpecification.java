package by.fyodorov.musicstore.specification.user;

import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserByNameSpecification implements UserRepositorySpecification {
    private static final String SELECT_BY_NAME =
            "SELECT " + USER_ID + ", "
                      + USER_USERNAME + ", "
                      + USER_EMAIL + ", "
                      + USER_ROLE + ", "
                      + USER_CASH + ", "
                      + USER_BONUS + ", "
                      + USER_DISCOUNT + ", "
                      + USER_PASSWORD + " " +
            "FROM "   + USER_BD_SCHEME + "." + USER_BD_TABLE + " " +
            "WHERE "  + USER_USERNAME + " = ?;";

    private String name;

    public UserByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_NAME;
    }

    @Override
    public String[] getArguments() {
        String[] arguments = new String[1];
        arguments[0] = name;
        return arguments;

    }
}
