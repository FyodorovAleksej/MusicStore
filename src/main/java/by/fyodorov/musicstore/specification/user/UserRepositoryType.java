package by.fyodorov.musicstore.specification.user;

public enum UserRepositoryType {
    USER_ID("userId"),
    USER_USERNAME("nickName"),
    USER_EMAIL("email"),
    USER_ROLE("role"),
    USER_CASH("cash"),
    USER_BONUS("bonus"),
    USER_DISCOUNT("discount"),
    USER_PASSWORD("password"),

    USER_BD_SCHEME("musicstore"),
    USER_BD_TABLE("users"),

    USER_HAS_TRACKS_BD_SCHEME("musicstore"),
    USER_HAS_TRACKS_BD_TABLE("users_has_tracks"),

    USER_HAS_TRACKS_USER_ID("users_userId"),
    USER_HAS_TRACKS_TRACK_ID("tracks_trackId"),
    USER_HAS_TRACKS_PERFORMER_ID("tracks_performers_id"),

    USER_HAS_ALBUMS_BD_SCHEME("musicstore"),
    USER_HAS_ALBUMS_BD_TABLE("users_has_albums"),

    USER_HAS_ALBUMS_USER_ID("users_userId"),
    USER_HAS_ALBUMS_ALBUM_ID("albums_albumId"),
    USER_HAS_ALBUMS_PERFORMER_ID("albums_performers_id"),

    USER_HAS_ASSEMBLAGES_BD_SCHEME("musicstore"),
    USER_HAS_ASSEMBLAGES_BD_TABLE("users_has_assemblages"),

    USER_HAS_ASSEMBLAGES_USER_ID("users_userId"),
    USER_HAS_ASSEMBLAGES_ASSEMBLAGE_ID("assemblages_assemblageId"),
    USER_HAS_ASSEMBLAGES_OWNER_ID("assemblages_users_userId");

    private String name;

    UserRepositoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
