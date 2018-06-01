package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.repository.AlbumRepository;
import by.fyodorov.musicstore.specification.album.AlbumCustomSelectSpecification;
import by.fyodorov.musicstore.specification.album.custom.*;
import by.fyodorov.musicstore.view.AlbumView;
import by.fyodorov.musicstore.view.AlbumWithoutPriceView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

/**
 * receiver for performing operations with albums
 */
public class AlbumReceiver {
    /**
     * finding albums with price for user
     * @param userName - username of current user
     * @return - list of albums
     * @throws ConnectorException - when can't perform select request
     */
    public LinkedList<AlbumView> findAlbumInfo(String userName) throws ConnectorException {
        AlbumRepository albumRepository = new AlbumRepository();
        AlbumCustomSelectSpecification specification = new AlbumWithUserCustomSelectSpecification(userName);
        LinkedList<AlbumView> albums = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = albumRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                int price = Integer.valueOf(map.get(AlbumWithUserCustomSelectSpecification.ALBUM_PRICE_KEY));
                albums.add(new AlbumView(
                        map.get(AlbumWithUserCustomSelectSpecification.ALBUM_NAME_KEY),
                        map.get(AlbumWithUserCustomSelectSpecification.ALBUM_PERFORMER_KEY),
                        map.get(AlbumWithUserCustomSelectSpecification.ALBUM_DATE_KEY),
                        map.get(AlbumWithUserCustomSelectSpecification.ALBUM_GENRE_KEY),
                        price,
                        Integer.valueOf(map.getOrDefault(AlbumWithUserCustomSelectSpecification.ALBUM_PRICE_SUMMARY_KEY, Integer.toString(price)))));
            }
        } finally {
            albumRepository.close();
        }
        return albums;
    }

    /**
     * finding albums without price for user
     * @param userName - username of current user
     * @return - list of albums
     * @throws ConnectorException - when can't perform select request
     */
    public LinkedList<AlbumWithoutPriceView> findAlbumForUser(String userName) throws ConnectorException {
        AlbumRepository assemblageRepository = new AlbumRepository();
        AlbumCustomSelectSpecification specification = new AlbumOfUserByNameCustomSelectSpecification(userName);
        LinkedList<AlbumWithoutPriceView> albums = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = assemblageRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                albums.add(new AlbumWithoutPriceView(
                        map.get(AlbumOfUserByNameCustomSelectSpecification.ALBUM_NAME_KEY),
                        map.get(AlbumOfUserByNameCustomSelectSpecification.ALBUM_PERFORMER_KEY),
                        map.get(AlbumOfUserByNameCustomSelectSpecification.ALBUM_DATE_KEY),
                        map.get(AlbumOfUserByNameCustomSelectSpecification.ALBUM_GENRE_KEY)));
            }
        } finally {
            assemblageRepository.close();
        }
        return albums;
    }

    /**
     * info of album for current user
     * @param albumName - album name for info
     * @param userName - username of current user
     * @return - optional of album for info
     * @throws ConnectorException - if can't execute select query
     */
    public Optional<AlbumView> albumInfoForUser(String albumName, String userName) throws ConnectorException {
        AlbumRepository albumRepository = new AlbumRepository();
        AlbumCustomSelectSpecification specification = new AlbumInfoForUserCustomSelectSpecification(albumName, userName);
        Optional<AlbumView> result = Optional.empty();
        try {
            LinkedList<HashMap<String, String>> arguments = albumRepository.customQuery(specification);
            if (!arguments.isEmpty()) {
                HashMap<String, String> map = arguments.getFirst();
                int price = Integer.valueOf(map.get(AlbumInfoForUserCustomSelectSpecification.ALBUM_PRICE_KEY));
                result = Optional.of(new AlbumView(
                        map.get(AlbumInfoForUserCustomSelectSpecification.ALBUM_NAME_KEY),
                        map.get(AlbumInfoForUserCustomSelectSpecification.ALBUM_PERFORMER_KEY),
                        map.get(AlbumInfoForUserCustomSelectSpecification.ALBUM_DATE_KEY),
                        map.get(AlbumInfoForUserCustomSelectSpecification.ALBUM_GENRE_KEY),
                        price,
                        Integer.valueOf(map.getOrDefault(AlbumInfoForUserCustomSelectSpecification.ALBUM_PRICE_SUMMARY_KEY, Integer.toString(price)))));
            }
        } finally {
            albumRepository.close();
        }
        return result;
    }

    /**
     * adding new album
     * @param album - name of new album
     * @param genre - genre of new album
     * @param price - price of new album
     * @param performer - performer of new album
     * @param tracks - array of track of new album
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean addNewAlbum(String album, String genre, int price, String performer, String[] tracks) throws ConnectorException {
        AlbumRepository albumRepository = new AlbumRepository();
        boolean result;
        AlbumRepository.modifyLock();
        try {
            result = albumRepository.prepareUpdate(new AlbumAddCustomUpdateSpecification(album, genre, price, performer)) > 0;
            for (String track : tracks) {
                result = result && albumRepository.prepareUpdate(new AlbumInsertTrackCustomUpdateSpecification(album, track)) > 0;
            }
        } catch (ConnectorException e) {
            result = false;
        } finally {
            AlbumRepository.modifyUnlock();
            albumRepository.close();
        }
        return result;
    }

    /**
     * editing album
     * @param oldName - old name of album
     * @param newName - new name of album
     * @param genre - new genre of album
     * @param price - new price of album
     * @param performer - new performer of album
     * @param tracks - new array of track of album
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean editAlbum(String oldName, String newName, String genre, int price, String performer, String[] tracks) throws ConnectorException {
        AlbumRepository albumRepository = new AlbumRepository();
        boolean result;
        AlbumRepository.modifyLock();
        try {
            result = albumRepository.prepareUpdate(new AlbumEditCustomUpdateSpecification(oldName, newName, genre, price, performer)) > 0;
            result = result && albumRepository.prepareUpdate(new AlbumClearCustomUpdateSpecification(newName)) >= 0;
            for (String track : tracks) {
                result = result && albumRepository.prepareUpdate(new AlbumInsertTrackCustomUpdateSpecification(newName, track)) > 0;
            }
        } catch (ConnectorException e) {
            result = false;
        } finally {
            AlbumRepository.modifyUnlock();
            albumRepository.close();
        }
        return result;
    }

    /**
     * remove album
     * @param albumName - album name for remove
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean removeAlbum(String albumName) throws ConnectorException {
        AlbumRepository albumRepository = new AlbumRepository();
        boolean result;
        AlbumRepository.modifyLock();
        try {
            result = albumRepository.prepareUpdate(new AlbumDeleteCustomUpdateSpecification(albumName)) > 0;
        } finally {
            AlbumRepository.modifyUnlock();
            albumRepository.close();
        }
        return result;
    }
}
