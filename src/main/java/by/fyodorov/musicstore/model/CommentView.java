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

    @Override
    public int hashCode() {
        return  userName.hashCode() +
                text.hashCode() +
                date.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        CommentView view = (CommentView)o;
        return  this.userName.equals(view.userName) &&
                this.text.equals(view.date) &&
                this.date.equals(view.date);
    }

    @Override
    public String toString() {
        return  "userName: " + userName +
                "; text: \"" + text +
                "\"; date: " + date;
    }
}
