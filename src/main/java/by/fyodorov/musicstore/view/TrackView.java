package by.fyodorov.musicstore.view;

import by.fyodorov.musicstore.model.TrackEntity;

public class TrackView {
    private String name;
    private String performer;
    private String date;
    private String genre;
    private int price;
    private int summary;

    public TrackView(String name, String performer, String date, String genre, int price, int summary) {
        this.name = name;
        this.performer = performer;
        this.date = date;
        this.genre = genre;
        this.price = price;
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public String getPerformer() {
        return performer;
    }

    public String getDate() {
        return date;
    }

    public String getGenre() {
        return genre;
    }

    public int getPrice() {
        return price;
    }

    public int getSummary() {
        return summary;
    }

    @Override
    public int hashCode() {
        return  name.hashCode() +
                performer.hashCode() +
                date.hashCode() +
                genre.hashCode() +
                Integer.hashCode(price) +
                Integer.hashCode(summary);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        TrackView view = (TrackView)o;
        return  this.name.equals(view.name) &&
                this.performer.equals(view.performer) &&
                this.date.equals(view.date) &&
                this.genre.equals(view.genre) &&
                this.price == view.price &&
                this.summary == view.summary;
    }

    @Override
    public String toString() {
        return  "name: " + name +
                "; performer: " + performer +
                "; date: " + date +
                "; genre: " + genre +
                "; price: " + price +
                "; summary: " + summary;
    }
}
