package by.fyodorov.musicstore.application;

public enum PagesUrl {
    MAIN_PAGE("/index.jsp"),
    LOGIN_PAGE("/jsp/login.jsp"),
    SIGN_UP_PAGE("/jsp/authority/signUp.jsp"),
    REGISTER_PAGE("/jsp/authority/register.jsp"),
    TRACK_INFO_PAGE("/jsp/track/info.jsp"),

    TRACK_OWN_VIEW_PAGE("/jsp/track/ownTrack.jsp"),

    TRACK_INFO_WITH_ARG_PAGE("/trackInfo?trackInfoName=");

    private String path;

    PagesUrl(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
