package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.repository.CommentRepository;
import by.fyodorov.musicstore.specification.comment.custom.CommentAddByTextAndUserCustomUpdateSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommentReceiver {
    private static Logger LOGGER = LogManager.getLogger(CommentReceiver.class);

    public boolean addComment(String text, String user, String trackName) throws ConnectorException {
        CommentRepository commentRepository = new CommentRepository();
        int result;
        try {
            result = commentRepository.prepareUpdate(new CommentAddByTextAndUserCustomUpdateSpecification(text, user, trackName));
        } finally {
            commentRepository.close();
        }
        return result > 0;
    }
}
