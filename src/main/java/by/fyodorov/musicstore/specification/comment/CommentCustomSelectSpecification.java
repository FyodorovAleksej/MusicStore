package by.fyodorov.musicstore.specification.comment;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.comment.CommentRepositorySpecification;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public interface CommentCustomSelectSpecification extends CommentRepositorySpecification {
    LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException;
}
