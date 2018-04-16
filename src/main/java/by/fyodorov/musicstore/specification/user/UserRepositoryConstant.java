package by.fyodorov.musicstore.specification.user;

public enum UserRepositoryConstant {
    USER_ID("userId"),
    USER_USERNAME("nickName"),
    USER_EMAIL("email"),
    USER_ROLE("role"),
    USER_CASH("cash"),
    USER_BONUS("bonus"),
    USER_DISCOUNT("discount"),
    USER_PASSWORD("password"),

    USER_BD_SCHEME("musicstore"),
    USER_BD_TABLE("users");

    private String name;

    UserRepositoryConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
