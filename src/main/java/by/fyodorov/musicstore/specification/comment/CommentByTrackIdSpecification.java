package by.fyodorov.musicstore.specification.comment;

import static by.fyodorov.musicstore.specification.comment.CommentRepositoryType.*;

public class CommentByTrackIdSpecification implements CommentRepositorySpecification {
    private static final String SELECT_BY_ID =
            "SELECT " + COMMENT_ID + ", "
                    + COMMENT_TEXT + ", "
                    + COMMENT_DATE + ", "
                    + COMMENT_USER_ID + ", "
                    + COMMENT_TRACK_ID + ", "
                    + COMMENT_PERFORMER_ID + " " +
                    "FROM "   + COMMENT_BD_SCHEME + "." + COMMENT_BD_TABLE + " " +
                    "WHERE "  + COMMENT_TRACK_ID + " = ?;";

    private int trackId;

    public CommentByTrackIdSpecification(int trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_ID;
    }

    @Override
    public String[] getArguments() {
        String[] arguments = new String[1];
        arguments[0] = Integer.toString(trackId);
        return arguments;

    }
}
