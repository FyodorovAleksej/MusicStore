package by.fyodorov.musicstore.specification.track;

public enum TrackRepositoryType {
    TRACK_ID("trackId"),
    TRACK_NAME("name"),
    TRACK_GENRE("genre"),
    TRACK_PRICE("price"),
    TRACK_DATE("date"),
    TRACK_PERFORMER_FK("performers_id"),

    TRACK_BD_SCHEME("musicstore"),
    TRACK_BD_TABLE("tracks");

    private String name;

    TrackRepositoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
