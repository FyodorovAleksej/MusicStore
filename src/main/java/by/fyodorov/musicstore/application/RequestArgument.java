package by.fyodorov.musicstore.application;

/**
 * enum with arguments on jsp pages
 */
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

    ALL_COUNT("allCount", "\\d+"),
    SEARCH_PAGE("page", "\\d+"),

    ALBUM_SEARCH_LIST("albumSearchList", null),
    ASSEMBLAGE_SEARCH_LIST("assemblageSearchList", null),
    TRACK_SEARCH_LIST("trackSearchList", null),

    NEXT_PAGE("nextPage", "\\d+"),
    PREVIOUS_PAGE("previousPage", "\\d+"),
    CURRENT_PAGE("currentPage", "\\d+"),
    NEXT_HIDE("nextHidden", "()|(hidden)"),
    PREVIOUS_HIDE("previousHidden", "()|(hidden)"),

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

    USER_NAME_INFO("infoUser", null),
    USER_INFO("userEditName", null),
    USER_ROLE_INFO("userEditRole", null),
    USER_ROLE_USER_INFO("userEditUserRole", null),
    USER_ROLE_ADMIN_INFO("userEditAdminRole", null),
    USER_BONUS_INFO("userBonus", null),
    USER_BONUS_TRACK_INFO("userEditTrackBonus", null),
    USER_BONUS_ALBUM_INFO("userEditAlbumBonus", null),
    USER_BONUS_ASSEMBLAGE_INFO("userEditAssemblageBonus", null),
    USER_DISCOUNT_INFO("userEditDiscount", "\\d+"),

    ALBUM_SESSION_NAME("albumSessionName", null),
    ALBUM_NAME_FOR_INFO("albumInfoName", null),
    ALBUM_INFO_NAME("albumName", null),
    ALBUM_INFO_GENRE("albumInfoGenre", null),
    ALBUM_INFO_PRICE("albumInfoPrice", null),
    ALBUM_INFO_DATE("albumInfoDate", null),
    ALBUM_INFO_PERFORMER("albumInfoPerformer", null),
    ALBUM_INFO_SUMMARY("albumInfoSummary", null),

    ALBUM_TRACKS_LIST("albumTrackList", null),

    ALBUM_ADD_NAME("albumName", null),
    ALBUM_ADD_GENRE("albumGenre", null),
    ALBUM_ADD_PRICE("albumPrice", null),
    ALBUM_ADD_PERFORMER("albumPerformer", null),
    ALBUM_ADD_TRACKS("chooseTrack", null),

    ASSEMBLAGE_SESSION_NAME("assemblageSessionName", null),
    ASSEMBLAGE_NAME_FOR_INFO("assemblageInfoName", null),
    ASSEMBLAGE_INFO_NAME("assemblageName", null),
    ASSEMBLAGE_INFO_GENRE("assemblageInfoGenre", null),
    ASSEMBLAGE_INFO_PRICE("assemblageInfoPrice", null),
    ASSEMBLAGE_INFO_DATE("assemblageInfoDate", null),
    ASSEMBLAGE_INFO_OWNER("assemblageInfoPrice", null),
    ASSEMBLAGE_INFO_SUMMARY("assemblageInfoSummary", null),

    ASSEMBLAGE_TRACKS_LIST("assemblageTrackList", null),

    ASSEMBLAGE_ADD_NAME("assemblageName", null),
    ASSEMBLAGE_ADD_GENRE("assemblageGenre", null),
    ASSEMBLAGE_ADD_PRICE("assemblagePrice", null),
    ASSEMBLAGE_ADD_TRACKS("chooseTrack", null),


    ALBUM_EDIT_OLD_NAME("albumOldName", null),
    ALBUM_EDIT_OLD_CLASSIC_GENRE("classicGenre", null),
    ALBUM_EDIT_OLD_ELECTRO_GENRE("electroGenre", null),
    ALBUM_EDIT_OLD_POP_GENRE("popGenre", null),
    ALBUM_EDIT_OLD_ROCK_GENRE("rockGenre", null),
    ALBUM_EDIT_OLD_JAZZ_GENRE("jazzGenre", null),
    ALBUM_EDIT_OLD_BLUES_GENRE("bluesGenre", null),
    ALBUM_EDIT_OLD_PRICE("albumOldPrice", null),
    ALBUM_EDIT_OLD_PERFORMER("albumOldPerformer", null),

    ALBUM_EDIT_NAME("albumName", null),
    ALBUM_EDIT_GENRE("albumGenre", null),
    ALBUM_EDIT_PRICE("albumPrice", null),
    ALBUM_EDIT_PERFORMER("albumPerformer", null),
    ALBUM_EDIT_TRACKS("chooseTrack", null),

    ASSEMBLAGE_EDIT_OLD_NAME("assemblageOldName", null),
    ASSEMBLAGE_EDIT_OLD_CLASSIC_GENRE("classicGenre", null),
    ASSEMBLAGE_EDIT_OLD_ELECTRO_GENRE("electroGenre", null),
    ASSEMBLAGE_EDIT_OLD_POP_GENRE("popGenre", null),
    ASSEMBLAGE_EDIT_OLD_ROCK_GENRE("rockGenre", null),
    ASSEMBLAGE_EDIT_OLD_JAZZ_GENRE("jazzGenre", null),
    ASSEMBLAGE_EDIT_OLD_BLUES_GENRE("bluesGenre", null),
    ASSEMBLAGE_EDIT_OLD_PRICE("assemblageOldPrice", null),
    ASSEMBLAGE_EDIT_OLD_PERFORMER("assemblageOldPerformer", null),

    ASSEMBLAGE_EDIT_NAME("assemblageName", null),
    ASSEMBLAGE_EDIT_GENRE("assemblageGenre", null),
    ASSEMBLAGE_EDIT_PRICE("assemblagePrice", null),
    ASSEMBLAGE_EDIT_TRACKS("chooseTrack", null),

    FILE_KEY("inputFile", null),

    TRACK_ADD_NAME("trackName", null),
    TRACK_ADD_GENRE("trackGenre", null),
    TRACK_ADD_PERFORMER("trackPerformer", null),
    TRACK_ADD_PRICE("trackPrice", "\\d+"),

    TRACK_EDIT_OLD_NAME("trackOldName", null),
    TRACK_EDIT_OLD_PRICE("trackOldPrice", null),
    TRACK_EDIT_OLD_PERFORMER("trackOldPerformer", null),
    TRACK_EDIT_OLD_CLASSIC_GENRE("classicGenre", null),
    TRACK_EDIT_OLD_ELECTRO_GENRE("electroGenre", null),
    TRACK_EDIT_OLD_POP_GENRE("popGenre", null),
    TRACK_EDIT_OLD_ROCK_GENRE("rockGenre", null),
    TRACK_EDIT_OLD_JAZZ_GENRE("jazzGenre", null),
    TRACK_EDIT_OLD_BLUES_GENRE("bluesGenre", null),

    TRACK_EDIT_NAME("trackName", null),
    TRACK_EDIT_GENRE("trackGenre", null),
    TRACK_EDIT_PERFORMER("trackPerformer", null),
    TRACK_EDIT_PRICE("trackPrice", "\\d+"),


    PERFORMER_LIST("performersList", null),


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
