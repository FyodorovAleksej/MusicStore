package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.AlbumEntity;
import by.fyodorov.musicstore.repository.AlbumRepository;
import by.fyodorov.musicstore.specification.album.AlbumByNameSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class AlbumReceiver implements CommandReceiver {
    private static Logger LOGGER = LogManager.getLogger(AlbumReceiver.class);

    public LinkedList<AlbumEntity> findAllAlbums(String pattern) throws ConnectorException {
        LOGGER.debug("finding album like pattern = \"" + pattern + "\"");
        AlbumRepository albumRepository = new AlbumRepository();
        LinkedList<AlbumEntity> list = albumRepository.prepareQuery(new AlbumByNameSpecification(pattern));
        albumRepository.close();
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
        albumRepository.close();
        return result;
    }
}
