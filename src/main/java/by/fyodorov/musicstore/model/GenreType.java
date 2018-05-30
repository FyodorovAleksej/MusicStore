package by.fyodorov.musicstore.model;

public enum GenreType {
    CLASSIC_GENRE(1, "classic"),
    ELECTRO_GENRE(2, "electro"),
    POP_GENRE(4, "pop"),
    ROCK_GENRE(8, "rock"),
    JAZZ_GENRE(16, "jazz"),
    BLUES_GENRE(32, "blues");

    private static final String SEPARATOR = ",";
    private int value;
    private String genre;

    GenreType(int value, String genre) {
        this.value = value;
        this.genre = genre;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public static int toGenreType(String genres) {
        if (genres == null) {
            return 0;
        }
        int result = 0;
        for (GenreType type : GenreType.values()) {
            if (genres.contains(type.genre)) {
                result = result | type.value;
            }
        }
        return result;
    }

    public static String fromBonusType(int code) {
        StringBuilder result = new StringBuilder();
        for (GenreType type : GenreType.values()) {
            if ((code & type.value) != 0) {
                if (!result.toString().isEmpty()) {
                    result.append(SEPARATOR);
                }
                result.append(type.genre);
            }
        }
        return result.toString();
    }
}
