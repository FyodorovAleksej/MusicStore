package by.fyodorov.musicstore.view;

public class TrackView {
    private String name;
    private String performer;
    private String date;
    private String genre;
    private int price;
    private int summary;
    private boolean check;

    public TrackView(String name, String performer, String date, String genre, int price, int summary) {
        this.name = name;
        this.performer = performer;
        this.date = date;
        this.genre = genre;
        this.price = price;
        this.summary = summary;
        this.check = false;
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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public int hashCode() {
        return  name.hashCode() +
                performer.hashCode() +
                date.hashCode() +
                genre.hashCode() +
                Integer.hashCode(price) +
                Integer.hashCode(summary) +
                Boolean.hashCode(check);
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
                this.summary == view.summary &&
                this.check == view.check;
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
