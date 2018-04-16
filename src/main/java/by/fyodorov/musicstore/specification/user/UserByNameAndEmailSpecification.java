package by.fyodorov.musicstore.specification.user;

import static by.fyodorov.musicstore.specification.user.UserRepositoryConstant.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryConstant.USER_USERNAME;

public class UserByNameAndEmailSpecification implements UserRepositorySpecification {
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
                    "WHERE "  + USER_USERNAME + " = ?" +
                    "OR " + USER_EMAIL + " = ?";

    private String name;
    private String email;

    public UserByNameAndEmailSpecification(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_NAME;
    }

    @Override
    public String[] getArguments() {
        String[] arguments = new String[2];
        arguments[0] = name;
        arguments[1] = email;
        return arguments;

    }
}
