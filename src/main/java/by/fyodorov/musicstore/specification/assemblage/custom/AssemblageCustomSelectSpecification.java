package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.RepositorySpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public interface AssemblageCustomSelectSpecification extends RepositorySpecification {
    LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException;
}
