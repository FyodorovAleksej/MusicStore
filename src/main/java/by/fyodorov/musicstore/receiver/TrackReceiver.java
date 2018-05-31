package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.repository.CommentRepository;
import by.fyodorov.musicstore.repository.TrackRepository;
import by.fyodorov.musicstore.specification.comment.CommentCustomSelectSpecification;
import by.fyodorov.musicstore.specification.comment.custom.CommentByTrackNameCustomSelectSpecification;
import by.fyodorov.musicstore.specification.track.TrackByNameSpecification;
import by.fyodorov.musicstore.specification.track.TrackCustomSelectSpecification;
import by.fyodorov.musicstore.specification.track.TrackCustomUpdateSpecification;
import by.fyodorov.musicstore.specification.track.custom.*;
import by.fyodorov.musicstore.view.CommentView;
import by.fyodorov.musicstore.view.TrackView;
import by.fyodorov.musicstore.view.TrackWithoutPriceView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;

public class TrackReceiver implements CommandReceiver {
    private static Logger LOGGER = LogManager.getLogger(TrackReceiver.class);


    public TrackEntity findTrack(String name) throws ConnectorException {
        LOGGER.debug("finding track = \"" + name + "\"");
        TrackRepository trackRepository = new TrackRepository();
        LinkedList<TrackEntity> list;
        try {
            list = trackRepository.prepareQuery(new TrackByNameSpecification(name));
        } finally {
            trackRepository.close();
        }
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    public LinkedList<CommentView> findComments(String trackName) throws ConnectorException {
        CommentRepository commentRepository = new CommentRepository();
        CommentCustomSelectSpecification specification = new CommentByTrackNameCustomSelectSpecification(trackName);
        LinkedList<CommentView> comments = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = commentRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                comments.add(new CommentView(
                        map.get(CommentByTrackNameCustomSelectSpecification.USER_USERNAME_KEY),
                        map.get(CommentByTrackNameCustomSelectSpecification.COMMENT_TEXT_KEY),
                        map.get(CommentByTrackNameCustomSelectSpecification.COMMENT_DATE_KEY)));
            }
        } finally {
            commentRepository.close();
        }
        return comments;
    }

    public LinkedList<TrackView> findTrackInfo(String userName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackCustomSelectSpecification specification = new TrackForUserCustomSelectSpecification(userName);
        LinkedList<TrackView> tracks = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = trackRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                int price = Integer.valueOf(map.get(TrackForUserCustomSelectSpecification.TRACK_PRICE_KEY));
                tracks.add(new TrackView(
                        map.get(TrackForUserCustomSelectSpecification.TRACK_NAME_KEY),
                        map.get(TrackForUserCustomSelectSpecification.PERFORMER_KEY),
                        map.get(TrackForUserCustomSelectSpecification.TRACK_DATE_KEY),
                        map.get(TrackForUserCustomSelectSpecification.TRACK_GENRE_KEY),
                        price,
                        Integer.valueOf(map.getOrDefault(TrackForUserCustomSelectSpecification.TRACK_PRICE_SUMMARY_KEY, Integer.toString(price)))));
            }
        } finally {
            trackRepository.close();
        }
        return tracks;
    }

    public LinkedList<TrackView> findTrackLimitInfo(String userName, int skip, int limit) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackLimitCustomSelectSpecification specification = new TrackLimitCustomSelectSpecification(userName, skip, limit);
        LinkedList<TrackView> tracks = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = trackRepository.prepareSelectWithLimit(specification);
            for (HashMap<String, String> map : arguments) {
                int price = Integer.valueOf(map.get(TrackForUserCustomSelectSpecification.TRACK_PRICE_KEY));
                tracks.add(new TrackView(
                        map.get(TrackLimitCustomSelectSpecification.TRACK_NAME_KEY),
                        map.get(TrackLimitCustomSelectSpecification.PERFORMER_KEY),
                        map.get(TrackLimitCustomSelectSpecification.TRACK_DATE_KEY),
                        map.get(TrackLimitCustomSelectSpecification.TRACK_GENRE_KEY),
                        price,
                        Integer.valueOf(map.getOrDefault(TrackLimitCustomSelectSpecification.TRACK_PRICE_SUMMARY_KEY, Integer.toString(price)))));
            }
        } finally {
            trackRepository.close();
        }
        return tracks;
    }

    public Integer findTrackCount() throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackCustomSelectSpecification specification = new TrackCountCustomSelectSpecification();
        try {
            LinkedList<HashMap<String, String>> arguments = trackRepository.customQuery(specification);
            return Integer.valueOf(arguments.getFirst().get(TrackCountCustomSelectSpecification.TRACK_COUNT_KEY));
        } finally {
            trackRepository.close();
        }
    }

    public LinkedList<TrackWithoutPriceView> findTracksForUser(String userName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackCustomSelectSpecification specification = new TrackOfUserByNameCustomSelectSpecification(userName);
        LinkedList<TrackWithoutPriceView> tracks = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = trackRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                tracks.add(new TrackWithoutPriceView(
                        map.get(TrackOfUserByNameCustomSelectSpecification.TRACK_NAME_KEY),
                        map.get(TrackOfUserByNameCustomSelectSpecification.PERFORMER_KEY),
                        map.get(TrackOfUserByNameCustomSelectSpecification.TRACK_DATE_KEY),
                        map.get(TrackOfUserByNameCustomSelectSpecification.TRACK_GENRE_KEY)));
            }
        } finally {
            trackRepository.close();
        }
        return tracks;
    }

    public LinkedList<TrackWithoutPriceView> findTracksWithoutPrice() throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackCustomSelectSpecification specification = new TrackWithoutPriceCustomSelectSpecification();
        LinkedList<TrackWithoutPriceView> tracks = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = trackRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                tracks.add(new TrackWithoutPriceView(
                        map.get(TrackOfUserByNameCustomSelectSpecification.TRACK_NAME_KEY),
                        map.get(TrackOfUserByNameCustomSelectSpecification.PERFORMER_KEY),
                        map.get(TrackOfUserByNameCustomSelectSpecification.TRACK_DATE_KEY),
                        map.get(TrackOfUserByNameCustomSelectSpecification.TRACK_GENRE_KEY)));
            }
        } finally {
            trackRepository.close();
        }
        return tracks;
    }

    public LinkedList<TrackWithoutPriceView> findTracksInAlbum(String albumName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackCustomSelectSpecification specification = new TrackInAlbumCustomSelectSpecification(albumName);
        LinkedList<TrackWithoutPriceView> tracks = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = trackRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                tracks.add(new TrackWithoutPriceView(
                        map.get(TrackInAlbumForUserCustomSelectSpecification.TRACK_NAME_KEY),
                        map.get(TrackInAlbumForUserCustomSelectSpecification.PERFORMER_KEY),
                        map.get(TrackInAlbumForUserCustomSelectSpecification.TRACK_DATE_KEY),
                        map.get(TrackInAlbumForUserCustomSelectSpecification.TRACK_GENRE_KEY)));
            }
        } finally {
            trackRepository.close();
        }
        return tracks;
    }

    public LinkedList<TrackWithoutPriceView> findTracksInAssemblage(String assemblageName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackCustomSelectSpecification specification = new TrackInAssemblageCustomSelectSpecification(assemblageName);
        LinkedList<TrackWithoutPriceView> tracks = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = trackRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                tracks.add(new TrackWithoutPriceView(
                        map.get(TrackInAssemblageForUserCustomSelectSpecification.TRACK_NAME_KEY),
                        map.get(TrackInAssemblageForUserCustomSelectSpecification.PERFORMER_KEY),
                        map.get(TrackInAssemblageForUserCustomSelectSpecification.TRACK_DATE_KEY),
                        map.get(TrackInAssemblageForUserCustomSelectSpecification.TRACK_GENRE_KEY)));
            }
        } finally {
            trackRepository.close();
        }
        return tracks;
    }

    public void addNewTrack(String trackName, String genre, int price, String performerName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        TrackAddCustomUpdateSpecification specification = new TrackAddCustomUpdateSpecification(trackName, genre, price, performerName);
        TrackRepository.modifyLock();
        try {
            trackRepository.prepareUpdate(specification);
        } finally {
            TrackRepository.modifyUnlock();
            trackRepository.close();
        }
    }

    public boolean editTrack(String oldName, String trackName, String genre, int price, String performerName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        boolean result;
        TrackCustomUpdateSpecification specification = new TrackEditCustomUpdateSpecification(oldName, trackName, genre, price, performerName);
        TrackRepository.modifyLock();
        try {
            result = trackRepository.prepareUpdate(specification) > 0;
        } finally {
            TrackRepository.modifyUnlock();
            trackRepository.close();
        }
        return result;
    }

    public boolean removeTrack(String trackName) throws ConnectorException {
        TrackRepository trackRepository = new TrackRepository();
        boolean result;
        TrackCustomUpdateSpecification specification = new TrackRemoveCustomUpdateSpecification(trackName);
        TrackRepository.modifyLock();
        try {
            result = trackRepository.prepareUpdate(specification) > 0;
        } finally {
            TrackRepository.modifyUnlock();
            trackRepository.close();
        }
        return result;
    }
}
