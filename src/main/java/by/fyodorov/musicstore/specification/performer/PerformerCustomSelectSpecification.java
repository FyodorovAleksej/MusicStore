package by.fyodorov.musicstore.specification.performer;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.performer.PerformerRepositorySpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public interface PerformerCustomSelectSpecification extends PerformerRepositorySpecification {
    LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException;
}
