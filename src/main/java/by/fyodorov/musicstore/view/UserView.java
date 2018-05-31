package by.fyodorov.musicstore.view;

public class UserView {
    private int id;
    private String name;
    private String email;
    private String role;

    public UserView(int id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getUserName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }
}
