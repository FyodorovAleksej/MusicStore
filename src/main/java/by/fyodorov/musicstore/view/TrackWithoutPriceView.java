package by.fyodorov.musicstore.view;

public class TrackWithoutPriceView {
    private String name;
    private String performer;
    private String date;
    private String genre;
    private boolean check;

    public TrackWithoutPriceView(String name, String performer, String date, String genre) {
        this.name = name;
        this.performer = performer;
        this.date = date;
        this.genre = genre;
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

    public boolean isCheck() {return check;}


    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public int hashCode() {
        return name.hashCode() +
                performer.hashCode() +
                date.hashCode() +
                genre.hashCode() +
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
        TrackWithoutPriceView view = (TrackWithoutPriceView) o;
        return this.name.equals(view.name) &&
                this.performer.equals(view.performer) &&
                this.date.equals(view.date) &&
                this.genre.equals(view.genre) &&
                this.check == view.check;
    }

    @Override
    public String toString() {
        return "name: " + name +
                "; performer: " + performer +
                "; date: " + date +
                "; genre: " + genre;
    }
}
