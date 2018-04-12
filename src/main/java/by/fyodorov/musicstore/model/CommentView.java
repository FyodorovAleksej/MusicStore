package by.fyodorov.musicstore.model;

import java.util.Date;

public class CommentView {
    private String userName;
    private String text;
    private String date;

    public CommentView(String userName, String text, String date) {
        this.userName = userName;
        this.text = text;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
