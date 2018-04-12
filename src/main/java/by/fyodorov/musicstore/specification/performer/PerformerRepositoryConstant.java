package by.fyodorov.musicstore.specification.performer;

public enum PerformerRepositoryConstant {
    PERFORMER_ID("performerId"),
    PERFORMER_NAME("name"),

    BD_SCHEME("musicstore"),
    BD_TABLE("performers");

    private String name;

    PerformerRepositoryConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
