package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.AssemblageEntity;
import by.fyodorov.musicstore.repository.AssemblageRepository;
import by.fyodorov.musicstore.specification.assemblage.AssemblageByNameSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class AssemblageReceiver {
    private static Logger LOGGER = LogManager.getLogger(AssemblageReceiver.class);

    public LinkedList<AssemblageEntity> findAllAssemblages(String pattern) throws ConnectorException {
        LOGGER.debug("finding assemblage like pattern = \"" + pattern + "\"");
        AssemblageRepository albumRepository = new AssemblageRepository();
        LinkedList<AssemblageEntity> list;
        try {
            list = albumRepository.prepareQuery(new AssemblageByNameSpecification(pattern));
        }
        finally {
            albumRepository.close();
        }
        return list;
    }

    public boolean addAssemblage(AssemblageEntity entity) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        try {
            assemblageRepository.add(entity);
            result = true;
        }
        catch (ConnectorException e) {
            result = false;
        }
        finally {
            assemblageRepository.close();
        }
        return result;
    }
}
