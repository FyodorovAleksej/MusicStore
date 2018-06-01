package by.fyodorov.musicstore.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * class entity of album
 */
public class AlbumEntity extends EntityBase {
    private static int DEFAULT_ID = -1;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private int id;
    private String name;
    private String genre;
    private int price;
    private Date date;
    private int performerId;

    /**
     * create album entity
     * @param id - id of album
     * @param name - name of album
     * @param genre - genre of album
     * @param price - price of album
     * @param date - date of album
     * @param performerId - performer id of album
     */
    public AlbumEntity(int id, String name, String genre, int price, java.util.Date date, int performerId) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.price = price;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        this.date = Date.valueOf(format.format(date));
        this.performerId = performerId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public int getPerformerId() {
        return performerId;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setName(int price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPerformerId(int performerId) {
        this.performerId = performerId;
    }


    @Override
    public int hashCode() {
        return Integer.hashCode(id) +
                name.hashCode() +
                genre.hashCode() +
                Integer.hashCode(price) +
                date.hashCode() +
                Integer.hashCode(performerId);
    }

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "name = \"" + name + "\"\n" +
                "genre = \"" + genre + "\"\n" +
                "price = " + price + "\n" +
                "date = \"" + date + "\"\n" +
                "performerId = " + performerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        AlbumEntity album = (AlbumEntity) obj;
        return this.id == album.id &&
                this.name.equals(album.name) &&
                this.genre.equals(album.genre) &&
                this.price == album.price &&
                this.date.equals(album.date) &&
                this.performerId == album.performerId;
    }
}
