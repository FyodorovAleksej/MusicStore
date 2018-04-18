package by.fyodorov.musicstore.application;

import by.fyodorov.musicstore.model.TrackEntity;

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

    TRACK_OWN_LIST("tracksOwn", null),

    TRACK_NAME_INFO("trackInfoName", null),

    TRACK_INFO_NAME("trackName", null),
    TRACK_INFO_GENRE("trackGenre", null),
    TRACK_INFO_PRICE("trackPrice", null),
    TRACK_INFO_DATE("trackDate", null),

    COMMENT_TEXT("newComment", null),

    PERFORMER_INFO_NAME("performerName", null),

    TRACK_INFO_COMMENT_LIST("commentList", null),

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
