package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.specification.assemblage.AssemblageCustomSelectSpecification;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class AssemblageInfoForUserCustomSelectSpecification implements AssemblageCustomSelectSpecification {
    private static final String TEMP_USER = "X";

    private static final String SELECT_ASSEMBLAGE_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, " +
                    "CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s LEFT JOIN %s ON %s.%s = ? " +
                    "JOIN %s AS %s ON %s.%s = %s.%s " +
                    "WHERE %s.%s = ? ;",

            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_GENRE,
            USER_BD_TABLE, USER_USERNAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_DATE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ASSEMBLAGE_BD_TABLE, USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            USER_BD_TABLE,
            TEMP_USER,
            TEMP_USER, USER_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_OWNER_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME);

    private String assemblageName;
    private String userName;

    public AssemblageInfoForUserCustomSelectSpecification(String assemblageName, String userName) {
        this.userName = userName;
        this.assemblageName = assemblageName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ASSEMBLAGE_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = assemblageName;
        return result;
    }
}
