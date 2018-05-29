package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageCustomSelectSpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;

public class AlbumClearCustomSelectSpecification implements AlbumCustomSelectSpecification {
    private static final String DELETE_SQL =
            String.format("DELETE FROM %s.%s " +
                            "WHERE %s = " +
                            "(SELECT %s.%s FROM %s WHERE %s.%s = ? );",
                    ALBUM_HAS_TRACKS_BD_SCHEME, ALBUM_BD_TABLE,
                    ALBUM_HAS_TRACKS_ALBUM_ID,
                    ALBUM_BD_TABLE, ALBUM_ID,
                    ALBUM_BD_TABLE,
                    ALBUM_BD_TABLE, ALBUM_NAME);

    private String albumName;

    public AlbumClearCustomSelectSpecification(String albumName) {
        this.albumName = albumName;
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
        res[0] = albumName;
        return res;
    }
}
