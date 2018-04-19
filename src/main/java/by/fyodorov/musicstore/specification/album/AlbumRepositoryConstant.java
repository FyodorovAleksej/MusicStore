package by.fyodorov.musicstore.specification.album;

public enum AlbumRepositoryConstant {
    ALBUM_ID("albumId"),
    ALBUM_NAME("name"),
    ALBUM_GENRE("genre"),
    ALBUM_PRICE("price"),
    ALBUM_DATE("date"),
    ALBUM_PERFORMER_ID("performers_id"),

    ALBUM_BD_SCHEME("musicstore"),
    ALBUM_BD_TABLE("albums"),

    ALBUM_HAS_TRACKS_BD_SCHEME("musicstore"),
    ALBUM_HAS_TRACKS_BD_TABLE("albums_has_tracks"),

    ALBUM_HAS_TRACKS_ALBUM_ID("albums_albumId"),
    ALBUM_HAS_TRACKS_ALBUM_PERFORMER_ID("albums_performers_id"),
    ALBUM_HAS_TRACKS_TRACK_ID("tracks_trackId"),
    ALBUM_HAS_TRACKS_TRACK_PERFORMER_ID("tracks_performers_id");

    private String name;

    AlbumRepositoryConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
