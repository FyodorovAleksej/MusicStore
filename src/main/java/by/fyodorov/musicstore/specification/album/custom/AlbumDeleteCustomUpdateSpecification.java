package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.specification.album.AlbumCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;

public class AlbumDeleteCustomUpdateSpecification implements AlbumCustomUpdateSpecification {
    private static final String DELETE_SQL =
            String.format("DELETE FROM %s.%s " +
                            "WHERE %s = ? ;",
                    ALBUM_BD_SCHEME, ALBUM_BD_TABLE,
                    ALBUM_NAME);

    private String albumName;

    public AlbumDeleteCustomUpdateSpecification(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toSqlClauses() {
        return DELETE_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[1];
        res[0] = albumName;
        return res;
    }
}
