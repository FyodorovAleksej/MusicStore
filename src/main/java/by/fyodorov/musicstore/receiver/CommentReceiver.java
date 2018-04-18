package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.CommentEntity;
import by.fyodorov.musicstore.repository.CommentRepository;
import by.fyodorov.musicstore.specification.comment.CommentByTrackIdSpecification;
import by.fyodorov.musicstore.specification.comment.custom.CommentAddByTextAndUserSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class CommentReceiver {
    private static Logger LOGGER = LogManager.getLogger(CommentReceiver.class);

    public LinkedList<CommentEntity> findCommentForTrackId(int id) throws ConnectorException {
        LOGGER.debug("finding comments for track with id = " + id);
        CommentRepository commentRepository = new CommentRepository();
        LinkedList<CommentEntity> list;
        try {
            list = commentRepository.prepareQuery(new CommentByTrackIdSpecification(id));
        }
        finally {
            commentRepository.close();
        }
        return list;

    }

    public boolean addComment(String text, String user, String trackName) throws ConnectorException {
        CommentRepository commentRepository = new CommentRepository();
        int result;
        try {
            result = commentRepository.prepareUpdate(new CommentAddByTextAndUserSpecification(text, user, trackName));
        }
        finally {
            commentRepository.close();
        }
        return result > 0;
    }
}
