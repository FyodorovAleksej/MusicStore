package by.fyodorov.musicstore.specification.user;

import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserByNameAndPasswordSpecification implements UserRepositorySpecification {
    private static final String SELECT_BY_NAME_AND_PASSWORD =
            "SELECT " + USER_ID + ", "
                    + USER_USERNAME + ", "
                    + USER_EMAIL + ", "
                    + USER_ROLE + ", "
                    + USER_CASH + ", "
                    + USER_BONUS + ", "
                    + USER_DISCOUNT + ", "
                    + USER_PASSWORD + " " +
                    "FROM " + USER_BD_SCHEME + "." + USER_BD_TABLE + " " +
                    "WHERE "  + USER_USERNAME + " = ? AND " + USER_PASSWORD + " = SHA(?);";
    private String name;
    private String password;

    public UserByNameAndPasswordSpecification(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String[] getArguments() {
        String[] arguments = new String[2];
        arguments[0] = name;
        arguments[1] = password;
        return arguments;

    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_NAME_AND_PASSWORD;
    }
}
