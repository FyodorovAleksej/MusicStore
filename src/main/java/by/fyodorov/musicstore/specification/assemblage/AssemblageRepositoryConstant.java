package by.fyodorov.musicstore.specification.assemblage;


public enum AssemblageRepositoryConstant {
    ASSEMBLAGE_ID("assemblageId"),
    ASSEMBLAGE_NAME("name"),
    ASSEMBLAGE_GENRE("genre"),
    ASSEMBLAGE_PRICE("price"),
    ASSEMBLAGE_DATE("date"),
    ASSEMBLAGE_OWNER_ID("users_userId"),

    ASSEMBLAGE_BD_SCHEME("musicstore"),
    ASSEMBLAGE_BD_TABLE("assemblages");

    private String name;

    AssemblageRepositoryConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
