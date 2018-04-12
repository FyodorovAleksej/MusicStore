package by.fyodorov.musicstore.specification.assemblage;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryConstant.*;

public class AssemblageByNameSpecification implements AssemblageRepositorySpecification {
    private static final String SELECT_BY_NAME =
            "SELECT " + ASSEMBLAGE_ID + ", "
                    + ASSEMBLAGE_NAME + ", "
                    + ASSEMBLAGE_GENRE + ", "
                    + ASSEMBLAGE_PRICE + ", "
                    + ASSEMBLAGE_DATE + ", "
                    + ASSEMBLAGE_OWNER_ID + " " +
                    "FROM "   + BD_SCHEME + "." + BD_TABLE + " " +
                    "WHERE "  + ASSEMBLAGE_NAME + " LIKE ?;";

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
