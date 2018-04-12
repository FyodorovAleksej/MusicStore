package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.CommentEntity;
import by.fyodorov.musicstore.repository.CommentRepository;
import by.fyodorov.musicstore.specification.comment.CommentByTrackIdSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class CommentReceiver {
    private static Logger LOGGER = LogManager.getLogger(CommentReceiver.class);

    public LinkedList<CommentEntity> findCommentForTrackId(int id) throws ConnectorException {
        LOGGER.debug("finding comments for track with id = " + id);
        CommentRepository commentRepository = new CommentRepository();
        LinkedList<CommentEntity> list = commentRepository.prepareQuery(new CommentByTrackIdSpecification(id));
        commentRepository.close();
        return list;

    }

    public boolean addComment(CommentEntity entity) throws ConnectorException {
        CommentRepository commentRepository = new CommentRepository();
        boolean result;
        try {
            commentRepository.add(entity);
            result = true;
        } catch (ConnectorException e) {
            LOGGER.catching(e);
            result = false;
        }
        commentRepository.close();
        return result;
    }
}
