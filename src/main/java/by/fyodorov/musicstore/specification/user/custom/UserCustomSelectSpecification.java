package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserRepositorySpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public interface UserCustomSelectSpecification extends UserRepositorySpecification {
    LinkedList<HashMap<String, String>> fromSet(ResultSet set);
}
