package by.fyodorov.musicstore.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AssemblageEntity extends EntityBase {
    private static int DEFAULT_ID = -1;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private int id;
    private String name;
    private String genre;
    private int price;
    private Date date;
    private int userId;

    public AssemblageEntity(int id, String name, String genre, int price, java.util.Date date, int userId) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.price = price;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        this.date = Date.valueOf(format.format(date));
        this.userId = userId;
    }

    public AssemblageEntity(String name, String genre, int price, java.util.Date date, int userId) {
        this(DEFAULT_ID, name, genre, price, date, userId);
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

    public int getUserId() {
        return userId;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public int hashCode() {
        return Integer.hashCode(id) +
                name.hashCode() +
                genre.hashCode() +
                Integer.hashCode(price) +
                date.hashCode() +
                Integer.hashCode(userId);
    }

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "name = \"" + name + "\"\n" +
                "genre = \"" + genre + "\"\n" +
                "price = " + price + "\n" +
                "date = \"" + date + "\"\n" +
                "userId = " + userId;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        AssemblageEntity assemblage = (AssemblageEntity) obj;
        return assemblage.id == this.id &&
                assemblage.name.equals(this.name) &&
                assemblage.genre.equals(this.genre) &&
                assemblage.price == this.price &&
                assemblage.date.equals(this.date) &&
                assemblage.userId == this.userId;
    }
}
