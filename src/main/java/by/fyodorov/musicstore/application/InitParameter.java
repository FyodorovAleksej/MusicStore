package by.fyodorov.musicstore.application;

public enum InitParameter {
    DATA_BASE_INIT("dbInit"),
    MAIL_INIT("mailInit");

    private String value;

    InitParameter(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
