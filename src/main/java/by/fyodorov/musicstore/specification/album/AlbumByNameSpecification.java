package by.fyodorov.musicstore.specification.album;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;

public class AlbumByNameSpecification implements AlbumRepositorySpecification {
    private static final String SELECT_BY_NAME =
            "SELECT " + ALBUM_ID + ", "
                    + ALBUM_NAME + ", "
                    + ALBUM_GENRE + ", "
                    + ALBUM_PRICE + ", "
                    + ALBUM_DATE + ", "
                    + ALBUM_PERFORMER_ID + " " +
                    "FROM " + ALBUM_BD_SCHEME + "." + ALBUM_BD_TABLE + " " +
                    "WHERE " + ALBUM_NAME + " LIKE ?;";

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
