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
import java.util.Optional;

/**
 * receiver for performing operations with tracks
 */
public class TrackReceiver {
    private static final Logger LOGGER = LogManager.getLogger(TrackReceiver.class);

    /**
     * finding track by name
     * @param name - name of track
     * @return - optional of track
     * @throws ConnectorException - when can't execute select query
     */
    public Optional<TrackEntity> findTrack(String name) throws ConnectorException {
        LOGGER.debug("finding track = \"" + name + "\"");
        TrackRepository trackRepository = new TrackRepository();
        LinkedList<TrackEntity> list;
        Optional<TrackEntity> result = Optional.empty();
        try {
            list = trackRepository.prepareQuery(new TrackByNameSpecification(name));
        } finally {
            trackRepository.close();
        }
        if (!list.isEmpty()) {
            result = Optional.of(list.getFirst());
        }
        return result;
    }

    /**
     * finding comments for track
     * @param trackName - track name for getting comments
     * @return - list of comments
     * @throws ConnectorException - when can't executing select query
     */
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

    /**
     * finding info of track with price for current user
     * @param userName - username of current user
     * @return - list of tracks info with price
     * @throws ConnectorException - when can't perform select query
     */
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

    /**
     * finding info of track with price for current user with limit
     * @param userName - username of current user
     * @param skip - skip rows for select
     * @param limit - limit of rows for select
     * @return - limited list of tracks info with price
     * @throws ConnectorException - when can't perform select query
            */
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

    /**
     * getting count of tracks in DB
     * @return - count of tracks
     * @throws ConnectorException - when can't execute select query
     */
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

    /**
     * finding list of tracks info without price for user
     * @param userName - name of current user
     * @return - list of user's tracks
     * @throws ConnectorException - when can't executing query
     */
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

    /**
     * finding all tracks info
     * @return - list of all tracks info without price
     * @throws ConnectorException - when can't execute select query
     */
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

    /**
     * finding tracks into album
     * @param albumName - name of album
     * @return - list of tracks info into album
     * @throws ConnectorException - when can't execute select query
     */
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

    /**
     * finding tracks into assemblage
     * @param assemblageName - name of assemblage
     * @return - list of tracks info into assemblage
     * @throws ConnectorException - when can't execute select query
     */
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

    /**
     * adding new track
     * @param trackName - name of new track
     * @param genre - genre of new track
     * @param price - price of new track
     * @param performerName - performer of new track
     * @throws ConnectorException - when can't execute update query
     */
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

    /**
     * editing track
     * @param oldName - old name of track
     * @param trackName - new name of track
     * @param genre - new genre of track
     * @param price - new price of track
     * @param performerName - new performer of track
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
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

    /**
     * remove track
     * @param trackName - name of track for removing
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
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
