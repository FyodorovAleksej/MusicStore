package by.fyodorov.musicstore.specification.comment;

import static by.fyodorov.musicstore.specification.comment.CommentRepositoryConstant.*;

public class CommentByUserSpecification implements CommentRepositorySpecification {
    private static final String SELECT_BY_ID =
            "SELECT " + COMMENT_ID + ", "
                    + COMMENT_TEXT + ", "
                    + COMMENT_DATE + ", "
                    + COMMENT_USER_ID + ", "
                    + COMMENT_TRACK_ID + ", "
                    + COMMENT_PERFORMER_ID + " " +
                    "FROM "   + BD_SCHEME + "." + BD_TABLE + " " +
                    "WHERE "  + COMMENT_USER_ID + " = ?;";

    private int userId;

    public CommentByUserSpecification(int userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_ID;
    }

    @Override
    public String[] getArguments() {
        String[] arguments = new String[1];
        arguments[0] = Integer.toString(userId);
        return arguments;

    }
}
