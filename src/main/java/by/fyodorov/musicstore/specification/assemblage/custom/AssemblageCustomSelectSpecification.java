package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.specification.assemblage.AssemblageRepositorySpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public interface AssemblageCustomSelectSpecification extends AssemblageRepositorySpecification {
    LinkedList<HashMap<String, String>> fromSet(ResultSet set);
}
