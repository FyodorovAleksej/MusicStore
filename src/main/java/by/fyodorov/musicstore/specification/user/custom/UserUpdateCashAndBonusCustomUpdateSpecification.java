package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.model.UserBonusType;
import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;

import static by.fyodorov.musicstore.model.UserBonusType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserUpdateCashAndBonusCustomUpdateSpecification implements UserCustomUpdateSpecification {
    private static final String UPDATE_CASH_AND_BONUS = String.format(
            "UPDATE %s SET %s.%s = ? , " +
                    "%s.%s = %s.%s & ? " +
                    "WHERE %s.%s = ? ;\n",
            USER_BD_TABLE,
            USER_BD_TABLE, USER_CASH,
            USER_BD_TABLE, USER_BONUS,
            USER_BD_TABLE, USER_BONUS,
            USER_BD_TABLE, USER_USERNAME);

    private String userName;
    private int bonus;
    private String newCash;

    public UserUpdateCashAndBonusCustomUpdateSpecification(String userName, String newCash, UserBonusType bonus) {
        this.userName = userName;
        this.newCash = newCash;
        this.bonus = bonus.getValue();
    }

    @Override
    public String toSqlClauses() {
        return UPDATE_CASH_AND_BONUS;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[3];
        result[0] = newCash;
        result[1] = Integer.toString((USER_BONUS_ASSEMBLAGE.getValue() | USER_BONUS_ALBUM.getValue() | USER_BONUS_TRACK.getValue()) ^ bonus);
        result[2] = userName;
        return result;
    }
}
