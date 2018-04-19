package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.AlbumEntity;
import by.fyodorov.musicstore.repository.AlbumRepository;
import by.fyodorov.musicstore.specification.album.AlbumByNameSpecification;
import by.fyodorov.musicstore.specification.album.custom.AlbumCustomSelectSpecification;
import by.fyodorov.musicstore.specification.album.custom.AlbumOfUserByNameCustomSelect;
import by.fyodorov.musicstore.view.AlbumWithoutPriceView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;

public class AlbumReceiver implements CommandReceiver {
    private static Logger LOGGER = LogManager.getLogger(AlbumReceiver.class);

    public LinkedList<AlbumEntity> findAllAlbums(String pattern) throws ConnectorException {
        LOGGER.debug("finding album like pattern = \"" + pattern + "\"");
        AlbumRepository albumRepository = new AlbumRepository();
        LinkedList<AlbumEntity> list;
        try {
            list = albumRepository.prepareQuery(new AlbumByNameSpecification(pattern));
        }
        finally {
            albumRepository.close();
        }
        return list;
    }

    public boolean addAlbum(AlbumEntity entity) throws ConnectorException {
        AlbumRepository albumRepository = new AlbumRepository();
        boolean result;
        try {
            albumRepository.add(entity);
            result = true;
        }
        catch (ConnectorException e) {
            result = false;
        }
        finally {
            albumRepository.close();
        }
        return result;
    }

    public LinkedList<AlbumWithoutPriceView> findAlbumForUser(String userName) throws ConnectorException {
        AlbumRepository assemblageRepository = new AlbumRepository();
        AlbumCustomSelectSpecification specification = new AlbumOfUserByNameCustomSelect(userName);
        LinkedList<AlbumWithoutPriceView> assemblages = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = assemblageRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                assemblages.add(new AlbumWithoutPriceView(
                        map.get(AlbumOfUserByNameCustomSelect.ALBUM_NAME_KEY),
                        map.get(AlbumOfUserByNameCustomSelect.ALBUM_PERFORMER_KEY),
                        map.get(AlbumOfUserByNameCustomSelect.ALBUM_DATE_KEY),
                        map.get(AlbumOfUserByNameCustomSelect.ALBUM_GENRE_KEY)));
            }
        }
        finally {
            assemblageRepository.close();
        }
        return assemblages;
    }
}
