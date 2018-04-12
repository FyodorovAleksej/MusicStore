package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.CommentView;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.repository.CommentRepository;
import by.fyodorov.musicstore.repository.TrackRepository;
import by.fyodorov.musicstore.specification.comment.custom.CommentCustomSelectSpecification;
import by.fyodorov.musicstore.specification.comment.custom.CommentSelectWithUserByTrackNameSpecification;
import by.fyodorov.musicstore.specification.track.TrackByNameSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class TrackReceiver implements CommandReceiver {
    private static Logger LOGGER = LogManager.getLogger(TrackReceiver.class);


    public TrackEntity findTrack(String name) throws ConnectorException {
        LOGGER.debug("finding track = \"" + name + "\"");
        TrackRepository trackRepository = new TrackRepository();

        LinkedList<TrackEntity> list = trackRepository.prepareQuery(new TrackByNameSpecification(name));

        trackRepository.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    public LinkedList<TrackEntity> findAllTracks(String pattern) throws ConnectorException {
        LOGGER.debug("finding track like pattern = \"" + pattern + "\"");
        TrackRepository trackRepository = new TrackRepository();
        LinkedList<TrackEntity> list = trackRepository.prepareQuery(new TrackByNameSpecification(pattern));
        trackRepository.close();
        return list;
    }

    public boolean addTrack(String name, String genre, int price, int performerId) throws ConnectorException {
        return addTrack(new TrackEntity(name, genre, price, new Date(), performerId));
    }

    public boolean addTrack(TrackEntity entity) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        boolean result;
        try {
            trackRepository.add(entity);
            result = true;
        }
        catch (ConnectorException e) {
            result = false;
        }
        trackRepository.close();
        return result;
    }

    public LinkedList<CommentView> getComments(String trackName) throws ConnectorException {
        CommentRepository commentRepository = new CommentRepository();
        CommentCustomSelectSpecification specification = new CommentSelectWithUserByTrackNameSpecification(trackName);
        LinkedList<CommentView> comments = new LinkedList<>();
        LinkedList<HashMap<String, String>> arguments = commentRepository.customSelect(specification);
        for (HashMap<String, String> map : arguments) {
            comments.add(new CommentView(
                    map.get(CommentSelectWithUserByTrackNameSpecification.USER_USERNAME_KEY),
                    map.get(CommentSelectWithUserByTrackNameSpecification.COMMENT_TEXT_KEY),
                    map.get(CommentSelectWithUserByTrackNameSpecification.COMMENT_DATE_KEY)));
        }
        return comments;
    }
}
