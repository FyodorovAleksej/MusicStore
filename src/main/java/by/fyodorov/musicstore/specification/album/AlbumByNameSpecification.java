package by.fyodorov.musicstore.specification.album;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;

public class AlbumByNameSpecification implements AlbumRepositorySpecification {
    private static final String SELECT_BY_NAME =
            String.format(
                    "SELECT %s, %s, %s, %s, %s, %s " +
                            "FROM %s.%s WHERE %s LIKE ?;",
                    ALBUM_ID, ALBUM_NAME,
                    ALBUM_GENRE, ALBUM_PRICE,
                    ALBUM_DATE, ALBUM_PERFORMER_ID,
                    ALBUM_BD_SCHEME, ALBUM_BD_TABLE,
                    ALBUM_NAME);

    private String name;

    public AlbumByNameSpecification(String name) {
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
