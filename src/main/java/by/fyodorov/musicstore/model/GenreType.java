package by.fyodorov.musicstore.model;

/**
 * enum of genres of tracks, albums, assemblages
 */
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

    /**
     * transform genres string to int code
     * @param genres - string with all genres in format : "pop,rock,jazz"
     * @return - bit code of used genres : 4|8|16 = 28
     */
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

    /**
     * transform bit code of genre to string with genres
     * @param code - code to tranform : 30
     * @return - genres of code if format : "electro,pop,rock,jazz"
     */
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
