package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.specification.track.TrackRepositorySpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public interface TrackCustomSelectSpecification extends TrackRepositorySpecification {
    LinkedList<HashMap<String, String>> fromSet(ResultSet set);
}
