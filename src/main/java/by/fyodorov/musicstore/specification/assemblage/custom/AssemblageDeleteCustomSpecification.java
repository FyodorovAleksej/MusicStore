package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;

public class AssemblageDeleteCustomSpecification implements AssemblageCustomSelectSpecification {
    private static final String DELETE_SQL =
            String.format("DELETE FROM %s.%s " +
                            "WHERE %s = ? ;",
                    ASSEMBLAGE_BD_SCHEME, ASSEMBLAGE_BD_TABLE,
                    ASSEMBLAGE_NAME);

    private String assemblageName;

    public AssemblageDeleteCustomSpecification(String assemblageName) {
        this.assemblageName = assemblageName;
    }

    @Deprecated
    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        return null;
    }

    @Override
    public String toSqlClauses() {
        return DELETE_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] res =  new String[1];
        res[0] = assemblageName;
        return res;
    }
}
