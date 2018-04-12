package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.specification.album.AlbumRepositorySpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public interface AlbumCustomSelectSpecification extends AlbumRepositorySpecification {
    LinkedList<HashMap<String, String>> fromSet(ResultSet set);
}
