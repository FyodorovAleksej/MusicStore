package by.fyodorov.musicstore.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TrackEntity extends EntityBase {
    private static final int DEFAULT_ID = -1;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private int id;
    private String name;
    private String genre;
    private int price;
    private Date date;
    private int performerId;

    public TrackEntity(int id, String name, String genre, int price, java.util.Date date, int performerId) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.price = price;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        this.date = Date.valueOf(format.format(date));
        this.performerId = performerId;
    }

    public TrackEntity(String name, String genre, int price, java.util.Date date, int performerId) {
        this(DEFAULT_ID, name, genre, price, date, performerId);
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

    public void setPrice(int price) {
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
        return  Integer.hashCode(id) +
                name.hashCode() +
                genre.hashCode() +
                Integer.hashCode(price) +
                date.hashCode() +
                Integer.hashCode(performerId);
    }

    @Override
    public String toString() {
        return  "id = "            + id          + "\n" +
                "name = "          + name        + "\n" +
                "genre = "         + genre       + "\n" +
                "price = "         + price       + "\n" +
                "date = "          + date        + "\n" +
                "performers_id = " + performerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        TrackEntity track = (TrackEntity)obj;
        return (track.id == this.id &&
                track.name.equals(this.name) &&
                track.genre.equals(this.genre) &&
                track.price == this.price &&
                track.performerId == this.performerId);
    }
}
