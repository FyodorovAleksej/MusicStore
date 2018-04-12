package by.fyodorov.musicstore.application;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    GUEST("guest");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
