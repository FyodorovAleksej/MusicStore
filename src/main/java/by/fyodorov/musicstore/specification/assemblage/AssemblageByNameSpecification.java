package by.fyodorov.musicstore.specification.assemblage;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;

public class AssemblageByNameSpecification implements AssemblageRepositorySpecification {
    private static final String SELECT_BY_NAME =
            String.format(
                    "SELECT %s, %s, %s, %s, %s, %s " +
                            "FROM %s.%s " +
                            "WHERE %s LIKE ?;",
                    ASSEMBLAGE_ID, ASSEMBLAGE_NAME,
                    ASSEMBLAGE_GENRE, ASSEMBLAGE_PRICE,
                    ASSEMBLAGE_DATE, ASSEMBLAGE_OWNER_ID,
                    ASSEMBLAGE_BD_SCHEME, ASSEMBLAGE_BD_TABLE,
                    ASSEMBLAGE_NAME);

    private String name;

    public AssemblageByNameSpecification(String name) {
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
