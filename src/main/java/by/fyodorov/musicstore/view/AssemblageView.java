package by.fyodorov.musicstore.view;

public class AssemblageView {
    private String name;
    private String genre;
    private String date;
    private String owner;
    private int price;
    private int summary;

    public AssemblageView(String name, String date, String genre, String owner, int price, int summary) {
        this.name = name;
        this.date = date;
        this.genre = genre;
        this.owner = owner;
        this.price = price;
        this.summary = summary;
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

    public int getPrice() {
        return price;
    }

    public int getSummary() {
        return summary;
    }

    @Override
    public int hashCode() {
        return  name.hashCode() +
                owner.hashCode() +
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
        AssemblageView view = (AssemblageView)o;
        return  this.name.equals(view.name) &&
                this.date.equals(view.date) &&
                this.genre.equals(view.genre) &&
                this.owner.equals(view.owner) &&
                this.price == view.price &&
                this.summary == view.summary;
    }

    @Override
    public String toString() {
        return  "name: " + name +
                "; date: " + date +
                "; genre: " + genre +
                "; owner: " + owner +
                "; price: " + price +
                "; summary: " + summary;
    }
}

