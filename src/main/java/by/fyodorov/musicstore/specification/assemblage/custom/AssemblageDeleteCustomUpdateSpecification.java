package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.specification.assemblage.AssemblageCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;

public class AssemblageDeleteCustomUpdateSpecification implements AssemblageCustomUpdateSpecification {
    private static final String DELETE_SQL =
            String.format("DELETE FROM %s.%s " +
                            "WHERE %s = ? ;",
                    ASSEMBLAGE_BD_SCHEME, ASSEMBLAGE_BD_TABLE,
                    ASSEMBLAGE_NAME);

    private String assemblageName;

    public AssemblageDeleteCustomUpdateSpecification(String assemblageName) {
        this.assemblageName = assemblageName;
    }

    @Override
    public String toSqlClauses() {
        return DELETE_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[1];
        res[0] = assemblageName;
        return res;
    }
}
