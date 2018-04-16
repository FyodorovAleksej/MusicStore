package by.fyodorov.musicstore.specification.album;

public enum AlbumRepositoryConstant {
    ALBUM_ID("albumId"),
    ALBUM_NAME("name"),
    ALBUM_GENRE("genre"),
    ALBUM_PRICE("price"),
    ALBUM_DATE("date"),
    ALBUM_PERFORMER_ID("performers_id"),

    ALBUM_BD_SCHEME("musicstore"),
    ABUM_BD_TABLE("albums");

    private String name;

    AlbumRepositoryConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
