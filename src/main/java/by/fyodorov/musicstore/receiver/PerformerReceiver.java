package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.repository.PerformerRepository;
import by.fyodorov.musicstore.specification.performer.PerformerByIdSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class PerformerReceiver {
    private static Logger LOGGER = LogManager.getLogger(PerformerReceiver.class);

    public PerformerEntity findPerformerId(int id) throws ConnectorException {
        LOGGER.debug("finding performers by id = \"" + id + "\"");
        PerformerRepository albumRepository = new PerformerRepository();
        LinkedList<PerformerEntity> list;
        try {
            list = albumRepository.prepareQuery(new PerformerByIdSpecification(id));
        }
        finally {
            albumRepository.close();
        }
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;

    }

    public boolean addPerformer(PerformerEntity entity) throws ConnectorException {
        PerformerRepository performerRepository = new PerformerRepository();
        boolean result;
        try {
            performerRepository.add(entity);
            result = true;
        }
        catch (ConnectorException e) {
            result = false;
        }
        finally {
            performerRepository.close();
        }
        return result;
    }
}
