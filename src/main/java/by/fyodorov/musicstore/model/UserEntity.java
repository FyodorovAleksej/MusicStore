package by.fyodorov.musicstore.model;

public class UserEntity extends EntityBase {
    private final static int DEFAULT_ID = -1;
    private final static String DEFAULT_ROLE = "user";
    private final static String DEFAULT_BONUS = "NULL";

    private int id;
    private String userName;
    private String email;
    private String role;
    private int cash;
    private String bonus;
    private int discount;
    private String password;


    public UserEntity(int id, String userName, String email, String role, int cash, String bonus, int discount, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.cash = cash;
        this.bonus = bonus;
        this.discount = discount;
        this.password = password;
    }

    public UserEntity(String userName, String email, int cash, int discount, String password) {
        this.id = DEFAULT_ID;
        this.userName = userName;
        this.email = email;
        this.role = DEFAULT_ROLE;
        this.cash = cash;
        this.bonus = DEFAULT_BONUS;
        this.discount = discount;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getCash() {
        return cash;
    }

    public int getDiscount() {
        return discount;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getBonus() {
        return bonus;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        UserEntity user = (UserEntity) obj;
        return user.id == this.id &&
                user.userName.equals(this.userName) &&
                user.email.equals(this.email) &&
                user.role.equals(this.role) &&
                user.cash == this.cash &&
                user.bonus.equals(this.bonus) &&
                user.discount == this.discount &&
                user.password.equals(this.password);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id) +
                userName.hashCode() +
                email.hashCode() +
                role.hashCode() +
                Integer.hashCode(cash) +
                bonus.hashCode() +
                Integer.hashCode(discount) +
                password.hashCode();
    }

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "username = \"" + userName + "\"\n" +
                "email = \"" + email + "\"\n" +
                "role = \"" + role + "\"\n" +
                "cash = " + cash + "\n" +
                "bonus = \"" + bonus + "\"\n" +
                "discount = " + discount + "\n" +
                "password = \"" + password + "\"";
    }

    public String getPassword() {
        return password;
    }
}
