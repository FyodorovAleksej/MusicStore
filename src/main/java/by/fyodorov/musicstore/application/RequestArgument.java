package by.fyodorov.musicstore.application;

public enum RequestArgument {
    LOGIN("login", "\\w+"),
    PASSWORD("password", null),
    LOGIN_RESULT("loginResult", null),
    REGISTER_RESULT("registerResult", null),
    SIGN_UP_RESULT("signUpResult", null),
    UUID("uuid", "[\\w\\-]+"),
    UUID_RESULT("uuidResult", null),
    COMMAND_NAME("command", null),

    REMEMBER_CHECK("rememberCheck", "true|false"),
    EMAIL("email", ".+@.+\\..+$"),
    REP_PASSWORD("repeatPassword", null),

    TRACK_NAME("trackName", "\\w+"),
    TRACK_FIND_RESULT("trackFindResult", null),
    TRACK_LIST("trackList", null),
    TRACK_ALL("trackAll", null),

    ALBUM_SEARCH_LIST("albumSearchList", null),
    ASSEMBLAGE_SEARCH_LIST("assemblageSearchList", null),
    TRACK_SEARCH_LIST("trackSearchList", null),


    ASSEMBLAGE_FIND_RESULT("assemblageFindResult", null),
    ASSEMBLAGE_OWN_LIST("assemblageOwnList", null),

    ALBUM_FIND_RESULT("albumFindResult", null),
    ALBUM_OWN_LIST("albumOwnList", null),

    TRACK_OWN_LIST("tracksOwn", null),

    TRACK_NAME_INFO("trackInfoName", null),

    TRACK_INFO_NAME("trackName", null),
    TRACK_INFO_GENRE("trackGenre", null),
    TRACK_INFO_PRICE("trackPrice", null),
    TRACK_INFO_DATE("trackDate", null),

    COMMENT_TEXT("newComment", null),

    PERFORMER_INFO_NAME("performerName", null),

    TRACK_INFO_COMMENT_LIST("commentList", null),

    USER_ADMIN_LIST("usersList", null),

    FILE_KEY("inputFile", null),

    TRACK_ADD_NAME("trackName", null),
    TRACK_ADD_GENRE("trackGenre", null),
    TRACK_ADD_PERFORMER("trackPerformer", null),
    TRACK_ADD_PRICE("trackPrice", "\\d+"),

    SESSION_LOGIN("userName", LOGIN.getPattern()),
    SESSION_ROLE("userRole", null);

    private String name;
    private String pattern;

    RequestArgument(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }


}
