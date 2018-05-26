package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;

public class AlbumOfUserByNameCustomSelect implements AlbumCustomSelectSpecification {
    public static final String ALBUM_NAME_KEY = "albumName";
    public static final String ALBUM_PERFORMER_KEY = "albumPerformer";
    public static final String ALBUM_GENRE_KEY = "albumGenre";
    public static final String ALBUM_DATE_KEY = "albumDate";

    private static final String SELECT_USERS_ALBUMS = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s FROM %s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "WHERE %s.%s = ? ;\n",

            ALBUM_BD_TABLE, ALBUM_NAME,
            ALBUM_BD_TABLE, ALBUM_GENRE,
            ALBUM_BD_TABLE, ALBUM_DATE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            USER_HAS_ALBUMS_BD_TABLE,
            ALBUM_BD_TABLE,
            ALBUM_BD_TABLE, ALBUM_ID,
            USER_HAS_ALBUMS_BD_TABLE, USER_HAS_ALBUMS_ALBUM_ID,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            USER_HAS_ALBUMS_BD_TABLE, USER_HAS_ALBUMS_PERFORMER_ID,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_ID,
            USER_HAS_ALBUMS_BD_TABLE, USER_HAS_ALBUMS_USER_ID,
            USER_BD_TABLE, USER_USERNAME);

    private String userName;

    public AlbumOfUserByNameCustomSelect(String userName) {
        this.userName = userName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_USERS_ALBUMS;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[1];
        result[0] = userName;
        return result;
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String albumName = set.getString(ALBUM_NAME.toString());
                String albumGenre = set.getString(ALBUM_GENRE.toString());
                String albumDate = set.getString(ALBUM_DATE.toString());
                String albumPerformer = set.getString(PERFORMER_NAME.toString());

                arguments.put(ALBUM_NAME_KEY, albumName);
                arguments.put(ALBUM_GENRE_KEY, albumGenre);
                arguments.put(ALBUM_DATE_KEY, albumDate);
                arguments.put(ALBUM_PERFORMER_KEY, albumPerformer);

                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Album DB", e);
        }
        return result;
    }
}
