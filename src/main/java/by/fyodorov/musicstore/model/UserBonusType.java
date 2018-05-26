package by.fyodorov.musicstore.model;

public enum UserBonusType {
    USER_BONUS_TRACK(1),
    USER_BONUS_ALBUM(2),
    USER_BONUS_ASSEMBLAGE(4);

    private int value;

    UserBonusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
