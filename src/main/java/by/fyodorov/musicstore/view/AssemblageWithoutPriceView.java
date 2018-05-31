package by.fyodorov.musicstore.view;

public class AssemblageWithoutPriceView {
    private String name;
    private String genre;
    private String date;
    private String owner;

    public AssemblageWithoutPriceView(String name, String date, String genre, String owner) {
        this.name = name;
        this.date = date;
        this.genre = genre;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getDate() {
        return date;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public int hashCode() {
        return name.hashCode() +
                owner.hashCode() +
                date.hashCode() +
                genre.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        AssemblageWithoutPriceView view = (AssemblageWithoutPriceView) o;
        return this.name.equals(view.name) &&
                this.date.equals(view.date) &&
                this.genre.equals(view.genre) &&
                this.owner.equals(view.owner);
    }

    @Override
    public String toString() {
        return "name: " + name +
                "; date: " + date +
                "; genre: " + genre +
                "; owner: " + owner;
    }
}
