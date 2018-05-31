package by.fyodorov.musicstore.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CommentEntity extends EntityBase {
    private static final int DEFAULT_ID = -1;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private int commentId;
    private String text;
    private Date date;
    private int userId;
    private int trackId;
    private int performerId;

    public CommentEntity(int commentId, String text, java.util.Date date, int userId, int trackId, int performerId) {
        this.commentId = commentId;
        this.text = text;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        this.date = Date.valueOf(format.format(date));
        this.userId = userId;
        this.trackId = trackId;
        this.performerId = performerId;
    }

    public CommentEntity(String text, java.util.Date date, int userId, int trackId, int performerId) {
        this(DEFAULT_ID, text, date, userId, trackId, performerId);
    }


    public int getCommentId() {
        return commentId;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public int getTrackId() {
        return trackId;
    }

    public int getPerformerId() {
        return performerId;
    }


    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public void setPerformerId(int performerId) {
        this.performerId = performerId;
    }

    @Override
    public String toString() {
        return "id = " + commentId + "\n" +
                "text = \"" + text + "\"\n" +
                "date = \"" + date + "\"\n" +
                "userId = " + userId + "\n" +
                "trackId = " + trackId + "\n" +
                "performerId = " + performerId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(commentId) +
                text.hashCode() +
                date.hashCode() +
                Integer.hashCode(userId) +
                Integer.hashCode(trackId) +
                Integer.hashCode(performerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        CommentEntity comment = (CommentEntity) obj;
        return this.commentId == comment.commentId &&
                this.text.equals(comment.text) &&
                this.date.equals(comment.date) &&
                this.userId == comment.userId &&
                this.trackId == comment.trackId &&
                this.performerId == comment.performerId;
    }
}
