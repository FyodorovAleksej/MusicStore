package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.CommentView;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.model.TrackView;
import by.fyodorov.musicstore.repository.CommentRepository;
import by.fyodorov.musicstore.repository.TrackRepository;
import by.fyodorov.musicstore.specification.comment.custom.CommentCustomSelectSpecification;
import by.fyodorov.musicstore.specification.comment.custom.CommentSelectWithUserByTrackNameSpecification;
import by.fyodorov.musicstore.specification.track.TrackByNameSpecification;
import by.fyodorov.musicstore.specification.track.custom.TrackCustomSelectSpecification;
import by.fyodorov.musicstore.specification.track.custom.TrackCustomSelectWithUserSpecification;
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

    public LinkedList<CommentView> findComments(String trackName) throws ConnectorException {
        CommentRepository commentRepository = new CommentRepository();
        CommentCustomSelectSpecification specification = new CommentSelectWithUserByTrackNameSpecification(trackName);
        LinkedList<CommentView> comments = new LinkedList<>();
        LinkedList<HashMap<String, String>> arguments = commentRepository.customQuery(specification);
        for (HashMap<String, String> map : arguments) {
            comments.add(new CommentView(
                    map.get(CommentSelectWithUserByTrackNameSpecification.USER_USERNAME_KEY),
                    map.get(CommentSelectWithUserByTrackNameSpecification.COMMENT_TEXT_KEY),
                    map.get(CommentSelectWithUserByTrackNameSpecification.COMMENT_DATE_KEY)));
        }
        return comments;
    }

    public LinkedList<TrackView> findTrackInfo(String userName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackCustomSelectSpecification specification = new TrackCustomSelectWithUserSpecification(userName);
        LinkedList<TrackView> tracks = new LinkedList<>();
        LinkedList<HashMap<String, String>> arguments = trackRepository.customQuery(specification);
        for (HashMap<String, String> map : arguments) {
            int price = Integer.valueOf(map.get(TrackCustomSelectWithUserSpecification.TRACK_PRICE_KEY));
            tracks.add(new TrackView(
                    map.get(TrackCustomSelectWithUserSpecification.TRACK_NAME_KEY),
                    map.get(TrackCustomSelectWithUserSpecification.PERFORMER_KEY),
                    map.get(TrackCustomSelectWithUserSpecification.TRACK_DATE_KEY),
                    map.get(TrackCustomSelectWithUserSpecification.TRACK_GENRE_KEY),
                    price,
                    Integer.valueOf(map.getOrDefault(TrackCustomSelectWithUserSpecification.TRACK_PRICE_SUMMARY_KEY, Integer.toString(price)))));
        }
        return tracks;
    }
}
