package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.specification.track.TrackCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

public class TrackEditCustomUpdateSpecification implements TrackCustomUpdateSpecification {
    private static final String EDIT_SQL = String.format(
            "UPDATE %s.%s SET %s = ? , %s = ? , %s = ? , %s = " +
                    "(SELECT %s.%s FROM %s WHERE %s.%s = ?) " +
                    "WHERE %s.%s = ? ;",
            TRACK_BD_SCHEME, TRACK_BD_TABLE,
            TRACK_NAME, TRACK_GENRE, TRACK_PRICE, TRACK_PERFORMER_FK,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            TRACK_BD_TABLE, TRACK_NAME);

    private String oldName;
    private String newName;
    private String newGenre;
    private int newPrice;
    private String newPerformer;

    public TrackEditCustomUpdateSpecification(String oldName, String newName, String newGenre, int newPrice, String newPerformer) {
        this.oldName = oldName;
        this.newName = newName;
        this.newGenre = newGenre;
        this.newPrice = newPrice;
        this.newPerformer = newPerformer;
    }

    @Override
    public String toSqlClauses() {
        return EDIT_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[5];
        res[0] = newName;
        res[1] = newGenre;
        res[2] = Integer.toString(newPrice);
        res[3] = newPerformer;
        res[4] = oldName;
        return res;
    }
}
