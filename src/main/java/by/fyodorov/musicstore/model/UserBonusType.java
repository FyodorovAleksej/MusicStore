package by.fyodorov.musicstore.model;

public enum UserBonusType {
    USER_BONUS_TRACK(1, "free track"),
    USER_BONUS_ALBUM(2, "free album"),
    USER_BONUS_ASSEMBLAGE(4, "free assemblage");

    private static final String SEPARATOR = ",";
    private int value;
    private String bonus;

    UserBonusType(int value, String bonus) {
        this.value = value;
        this.bonus = bonus;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public static int toBonusType(String bonuses) {
        if (bonuses == null) {
            return 0;
        }
        int result = 0;
        for (UserBonusType type : UserBonusType.values()) {
            if (bonuses.contains(type.bonus)) {
                result = result | type.value;
            }
        }
        return result;
    }

    public static String fromBonusType(int code) {
        StringBuilder result = new StringBuilder();
        for (UserBonusType type : UserBonusType.values()) {
            if ((code & type.value) != 0) {
                if (!result.toString().isEmpty()) {
                    result.append(SEPARATOR);
                }
                result.append(type.bonus);
            }
        }
        return result.toString();
    }
}
