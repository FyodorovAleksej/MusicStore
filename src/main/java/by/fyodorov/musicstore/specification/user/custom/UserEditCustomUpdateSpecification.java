package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserEditCustomUpdateSpecification implements UserCustomUpdateSpecification {

    private static final String UPDATE_USER = String.format(
            "UPDATE %s.%s SET %s.%s = ? , " +
                    "%s.%s = ? , " +
                    "%s.%s = ? " +
                    "WHERE %s.%s = ? ;",
            USER_BD_SCHEME, USER_BD_TABLE,
            USER_BD_TABLE, USER_ROLE,
            USER_BD_TABLE, USER_BONUS,
            USER_BD_TABLE, USER_DISCOUNT,
            USER_BD_TABLE, USER_USERNAME);

    private String userName;
    private String role;
    private String bonus;
    private int discount;

    public UserEditCustomUpdateSpecification(String userName, String role, String bonus, int discount) {
        this.userName = userName;
        this.role = role;
        this.bonus = bonus;
        this.discount = discount;
    }

    @Override
    public String toSqlClauses() {
        return UPDATE_USER;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[4];
        result[0] = role;
        result[1] = bonus;
        result[2] = Integer.toString(discount);
        result[3] = userName;
        return result;
    }
}
