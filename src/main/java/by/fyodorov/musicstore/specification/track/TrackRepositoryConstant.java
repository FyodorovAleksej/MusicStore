package by.fyodorov.musicstore.specification.track;

public enum TrackRepositoryConstant {
    TRACK_ID("trackId"),
    TRACK_NAME("name"),
    TRACK_GENRE("genre"),
    TRACK_PRICE("price"),
    TRACK_DATE("date"),
    TRACK_PERFORMER_FK("performers_id"),

    BD_SCHEME("musicstore"),
    BD_TABLE("tracks");

    private String name;

    TrackRepositoryConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
