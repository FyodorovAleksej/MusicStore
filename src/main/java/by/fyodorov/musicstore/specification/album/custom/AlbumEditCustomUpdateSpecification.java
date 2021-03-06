package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.specification.album.AlbumCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;

public class AlbumEditCustomUpdateSpecification implements AlbumCustomUpdateSpecification {
    private static final String EDIT_SQL = String.format(
            "UPDATE %s.%s SET %s = ? , %s = ? , %s = ? , %s = " +
                    "(SELECT %s.%s FROM %s WHERE %s.%s = ?) " +
                    "WHERE %s.%s = ? ;",
            ALBUM_BD_SCHEME, ALBUM_BD_TABLE,
            ALBUM_NAME, ALBUM_GENRE, ALBUM_PRICE, ALBUM_PERFORMER_ID,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            ALBUM_BD_TABLE, ALBUM_NAME);

    private String oldName;
    private String newName;
    private String newGenre;
    private int newPrice;
    private String newPerformer;

    public AlbumEditCustomUpdateSpecification(String oldName, String newName, String newGenre, int newPrice, String newPerformer) {
        this.oldName = oldName;
        this.newName = newName;
        this.newGenre = newGenre;
        this.newPrice = newPrice;
        this.newPerformer = newPerformer;
    }

    @Override
    public String toSqlClauses() {
        return EDIT_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[5];
        res[0] = newName;
        res[1] = newGenre;
        res[2] = Integer.toString(newPrice);
        res[3] = newPerformer;
        res[4] = oldName;
        return res;
    }
}
