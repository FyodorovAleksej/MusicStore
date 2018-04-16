package by.fyodorov.musicstore.specification.performer;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryConstant.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryConstant.PERFORMER_NAME;

public class PerformerByIdSpecification implements PerformerRepositorySpecification {
    private static final String SELECT_BY_ID =
            "SELECT " + PERFORMER_ID + ", "
                    + PERFORMER_NAME + " " +
                    "FROM "   + PERFORMER_BD_SCHEME + "." + PERFORMER_BD_TABLE + " " +
                    "WHERE "  + PERFORMER_ID + " = ?;";

    private int id;

    public PerformerByIdSpecification(int id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_ID;
    }

    @Override
    public String[] getArguments() {
        String[] arguments = new String[1];
        arguments[0] = Integer.toString(id);
        return arguments;

    }
}
