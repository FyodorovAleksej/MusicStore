package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.specification.assemblage.AssemblageCustomSelectSpecification;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class AssemblageWithUserCustomSelectSpecification extends AssemblageCustomSelectSpecification {
    private static final String TEMPO_TABLE = "X";

    private static final String SELECT_ASSEMBLAGE_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, " +
                    "CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s " +
                    "LEFT JOIN %s ON %s.%s = ? " +
                    "JOIN (SELECT %s, %s FROM %s) as %s " +
                    "ON %s.%s = %s.%s ;",

            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_GENRE,
            TEMPO_TABLE, USER_USERNAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_DATE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ASSEMBLAGE_BD_TABLE,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            USER_USERNAME, USER_ID,
            USER_BD_TABLE,
            TEMPO_TABLE,
            TEMPO_TABLE, USER_ID,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_OWNER_ID);

    private String userName;

    public AssemblageWithUserCustomSelectSpecification(String userName) {
        this.userName = userName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ASSEMBLAGE_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[1];
        result[0] = userName;
        return result;
    }
}
