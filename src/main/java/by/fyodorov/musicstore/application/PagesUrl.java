package by.fyodorov.musicstore.application;

public enum PagesUrl {
    MAIN_PAGE("/index.jsp"),
    LOGIN_PAGE("/jsp/login.jsp"),
    SIGN_UP_PAGE("/jsp/authority/signUp.jsp"),
    REGISTER_PAGE("/jsp/authority/register.jsp"),
    TRACK_INFO_PAGE("/jsp/track/info.jsp"),

    TRACK_OWN_VIEW_PAGE("/jsp/own/ownTrack.jsp"),
    ALBUM_OWN_VIEW_PAGE("/jsp/own/ownAlbum.jsp"),
    ASSEMBLAGE_OWN_VIEW_PAGE("/jsp/own/ownAssemblage.jsp"),

    TRACK_SEARCH_PAGE("/jsp/search/searchTrack.jsp"),
    ALBUM_SEARCH_PAGE("/jsp/search/searchAlbum.jsp"),
    ASSEMBLAGE_SEARCH_PAGE("/jsp/search/searchAssemblage.jsp"),

    USER_ADMIN_PAGE("/jsp/administrate/viewUsers.jsp"),
    USER_INFO_PAGE("/jsp/administrate/editUser.jsp"),
    USER_INFO_WITH_ARG_PAGE("/infoUser?infoUser="),

    ALBUM_INFO_WITH_ARG_PAGE("/albumInfo?albumInfoName="),
    ALBUM_INFO_PAGE("/jsp/album/info.jsp"),
    ASSEMBLAGE_INFO_WITH_ARG_PAGE("/assemblageInfo?assemblageInfoName="),
    ASSEMBLAGE_INFO_PAGE("/jsp/assemblage/info.jsp"),
    TRACK_INFO_WITH_ARG_PAGE("/trackInfo?trackInfoName="),

    TRACK_ADD_PAGE("/jsp/administrate/addTrack.jsp"),
    TRACK_EDIT_PAGE("/jsp/administrate/editTrack.jsp"),

    ALBUM_ADD_PAGE("/jsp/album/addAlbum.jsp"),
    ASSEMBLAGE_ADD_PAGE("/jsp/assemblage/addAssemblage.jsp"),

    ALBUM_EDIT_PAGE("/jsp/album/editAlbum.jsp"),
    ASSEMBLAGE_EDIT_PAGE("/jsp/assemblage/editAssemblage.jsp"),

    ALL_TRACKS_PAGE("/tracks"),
    ALL_ALBUMS_PAGE("/albums"),
    ALL_ASSEMBLAGES_PAGE("/assemblages");

    private String path;

    PagesUrl(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
