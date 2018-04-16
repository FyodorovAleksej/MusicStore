package by.fyodorov.musicstore.specification.performer;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryConstant.*;


public class PerformerByNameSpecification implements PerformerRepositorySpecification {
    private static final String SELECT_BY_NAME =
            "SELECT " + PERFORMER_ID + ", "
                    + PERFORMER_NAME + " " +
                    "FROM "   + PERFORMER_BD_SCHEME + "." + PERFORMER_BD_TABLE + " " +
                    "WHERE "  + PERFORMER_NAME + " LIKE ?;";

    private String name;

    public PerformerByNameSpecification(String name) {
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
