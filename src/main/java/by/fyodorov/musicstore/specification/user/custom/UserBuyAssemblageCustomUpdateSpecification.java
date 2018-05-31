package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserBuyAssemblageCustomUpdateSpecification implements UserCustomUpdateSpecification {
    private static final String BUY_TRANSACTION = String.format(
            "INSERT INTO %s (%s.%s, %s.%s, %s.%s) " +
                    "SELECT %s.%s, %s.%s, %s.%s " +
                    "FROM %s, %s WHERE %s.%s = ? " +
                    "AND %s.%s = ? ;",

            USER_HAS_ASSEMBLAGES_BD_TABLE,
            USER_HAS_ASSEMBLAGES_BD_TABLE, USER_HAS_ASSEMBLAGES_USER_ID,
            USER_HAS_ASSEMBLAGES_BD_TABLE, USER_HAS_ASSEMBLAGES_ASSEMBLAGE_ID,
            USER_HAS_ASSEMBLAGES_BD_TABLE, USER_HAS_ASSEMBLAGES_OWNER_ID,
            USER_BD_TABLE, USER_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_OWNER_ID,
            USER_BD_TABLE,
            ASSEMBLAGE_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME);

    private String userName;
    private String assemblageName;

    public UserBuyAssemblageCustomUpdateSpecification(String userName, String assemblageName) {
        this.userName = userName;
        this.assemblageName = assemblageName;
    }

    @Override
    public String toSqlClauses() {
        return BUY_TRANSACTION;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = assemblageName;
        return result;
    }
}