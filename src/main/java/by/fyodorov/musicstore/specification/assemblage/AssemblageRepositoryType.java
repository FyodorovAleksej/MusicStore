package by.fyodorov.musicstore.specification.assemblage;


public enum AssemblageRepositoryType {
    ASSEMBLAGE_ID("assemblageId"),
    ASSEMBLAGE_NAME("name"),
    ASSEMBLAGE_GENRE("genre"),
    ASSEMBLAGE_PRICE("price"),
    ASSEMBLAGE_DATE("date"),
    ASSEMBLAGE_OWNER_ID("users_userId"),

    ASSEMBLAGE_BD_SCHEME("musicstore"),
    ASSEMBLAGE_BD_TABLE("assemblages"),

    ASSEMBLAGE_HAS_TRACKS_BD_SCHEME("musicstore"),
    ASSEMBLAGE_HAS_TRACKS_BD_TABLE("assemblages_has_tracks"),

    ASSEMBLAGE_HAS_TRACKS_ASSEMBLAGE_ID("assemblages_assemblagesId"),
    ASSEMBLAGE_HAS_TRACKS_ASSEMBLAGE_OWNER_ID("assemblages_users_userId"),
    ASSEMBLAGE_HAS_TRACKS_TRACK_ID("tracks_trackId"),
    ASSEMBLAGE_HAS_TRACKS_TRACK_PERFORMER_ID("tracks_performers_id");

    private String name;

    AssemblageRepositoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
