package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomSelectSpecification;

import static by.fyodorov.musicstore.model.UserBonusType.USER_BONUS_TRACK;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserCashAfterTrackOperationCustomSelectSpecification extends UserCustomSelectSpecification {
    private static final String USER_CASH_AFTER_SELECT = String.format(
            "SELECT (%s.%s - IF(%s.%s & %s, 0, (1 - %s.%s) * " +
                    "(SELECT %s.%s FROM %s WHERE %s.%s = ?))) AS %s " +
                    "FROM %s WHERE %s.%s = ? ;\n",

            USER_BD_TABLE, USER_CASH,
            USER_BD_TABLE, USER_BONUS,
            USER_BONUS_TRACK,
            USER_BD_TABLE, USER_DISCOUNT,
            TRACK_BD_TABLE, TRACK_PRICE,
            TRACK_BD_TABLE,
            TRACK_BD_TABLE, TRACK_NAME,
            SUMMARY_TABLE,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME);

    private String userName;
    private String trackName;

    public UserCashAfterTrackOperationCustomSelectSpecification(String userName, String trackName) {
        this.userName = userName;
        this.trackName = trackName;
    }

    @Override
    public String toSqlClauses() {
        return USER_CASH_AFTER_SELECT;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = trackName;
        result[1] = userName;
        return result;
    }
}
