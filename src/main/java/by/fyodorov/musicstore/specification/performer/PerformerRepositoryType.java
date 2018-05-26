package by.fyodorov.musicstore.specification.performer;

public enum PerformerRepositoryType {
    PERFORMER_ID("performerId"),
    PERFORMER_NAME("performerName"),

    PERFORMER_BD_SCHEME("musicstore"),
    PERFORMER_BD_TABLE("performers");

    private String name;

    PerformerRepositoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
