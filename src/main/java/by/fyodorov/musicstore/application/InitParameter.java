package by.fyodorov.musicstore.application;

/**
 * enum with key for servlet context init
 */
public enum InitParameter {
    DATA_BASE_INIT("dbInit"),
    MAIL_INIT("mailInit"),
    PAGE_MAX("pageMax"),
    TRACK_EXTENSION(".mp3"),
    FILE_PATH("/tracks/");

    private String value;

    InitParameter(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
