package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;

public class AssemblageEditCustomSelectSpecification implements AssemblageCustomSelectSpecification {
    private static final String EDIT_SQL = String.format(
            "UPDATE %s.%s SET %s = ? , %s = ? , %s = ? " +
                    "WHERE %s.%s = ? ;",
            ASSEMBLAGE_BD_SCHEME, ASSEMBLAGE_BD_TABLE,
            ASSEMBLAGE_NAME, ASSEMBLAGE_GENRE, ASSEMBLAGE_PRICE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME);

    private String oldName;
    private String newName;
    private String newGenre;
    private int newPrice;

    public AssemblageEditCustomSelectSpecification(String oldName, String newName, String newGenre, int newPrice) {
        this.oldName = oldName;
        this.newName = newName;
        this.newGenre = newGenre;
        this.newPrice = newPrice;
    }

    @Deprecated
    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        return null;
    }

    @Override
    public String toSqlClauses() {
        return EDIT_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[4];
        res[0] = newName;
        res[1] = newGenre;
        res[2] = Integer.toString(newPrice);
        res[3] = oldName;
        return res;
    }
}
