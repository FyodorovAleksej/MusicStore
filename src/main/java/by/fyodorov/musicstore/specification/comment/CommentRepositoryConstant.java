package by.fyodorov.musicstore.specification.comment;

public enum CommentRepositoryConstant {
    COMMENT_ID("commentsId"),
    COMMENT_TEXT("text"),
    COMMENT_DATE("date"),
    COMMENT_USER_ID("users_userId"),
    COMMENT_TRACK_ID("tracks_trackId"),
    COMMENT_PERFORMER_ID("tracks_performers_id"),

    BD_SCHEME("musicstore"),
    BD_TABLE("comments");

    private String name;

    CommentRepositoryConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
