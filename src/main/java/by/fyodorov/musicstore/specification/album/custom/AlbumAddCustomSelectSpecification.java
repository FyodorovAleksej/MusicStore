package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageCustomSelectSpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.PERFORMER_BD_TABLE;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.PERFORMER_ID;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.PERFORMER_NAME;

public class AlbumAddCustomSelectSpecification implements AlbumCustomSelectSpecification {
    private static final String ALBUM_ADD = String.format(
            "INSERT INTO %s.%s (%s, %s, %s, %s, %s) " +
            "VALUES (?, ?, ?, CURDATE(), " +
            "(SELECT %s.%s FROM %s WHERE %s.%s = ?));",
            ALBUM_BD_SCHEME, ALBUM_BD_TABLE,
            ALBUM_NAME, ALBUM_GENRE, ALBUM_PRICE, ALBUM_DATE, ALBUM_PERFORMER_ID,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_NAME);

    private String albumName;
    private String albumGenre;
    private int albumPrice;
    private String performerName;

    public AlbumAddCustomSelectSpecification(String albumName, String albumGenre, int albumPrice, String performerName) {
        this.albumName = albumName;
        this.albumGenre = albumGenre;
        this.albumPrice = albumPrice;
        this.performerName = performerName;
    }

    @Deprecated
    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        return null;
    }

    @Override
    public String toSqlClauses() {
        return ALBUM_ADD;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[4];
        res[0] = albumName;
        res[1] = albumGenre;
        res[2] = Integer.toString(albumPrice);
        res[3] = performerName;
        return res;
    }
}
