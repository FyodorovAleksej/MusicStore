package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.repository.CommentRepository;
import by.fyodorov.musicstore.specification.comment.custom.CommentAddByTextAndUserCustomUpdateSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * receiver for performing comments operations
 */
public class CommentReceiver {
    /**
     * adding comment for track
     * @param text - text of comment
     * @param user - username of current user
     * @param trackName - track name for commenting
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
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
