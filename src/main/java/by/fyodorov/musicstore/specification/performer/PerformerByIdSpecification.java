package by.fyodorov.musicstore.specification.performer;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;

public class PerformerByIdSpecification implements PerformerRepositorySpecification {
    private static final String SELECT_BY_ID =
            String.format("SELECT %s, %s " +
                            "FROM %s.%s WHERE %s = ?;",
                    PERFORMER_ID, PERFORMER_NAME,
                    PERFORMER_BD_SCHEME, PERFORMER_BD_TABLE,
                    PERFORMER_ID);

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
