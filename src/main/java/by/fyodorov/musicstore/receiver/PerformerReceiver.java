package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.repository.PerformerRepository;
import by.fyodorov.musicstore.specification.performer.PerformerByIdSpecification;
import by.fyodorov.musicstore.specification.performer.custom.PerformerAllCustomSelectSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;

public class PerformerReceiver {
    private static Logger LOGGER = LogManager.getLogger(PerformerReceiver.class);

    public PerformerEntity findPerformerId(int id) throws ConnectorException {
        LOGGER.debug("finding performers by id = \"" + id + "\"");
        PerformerRepository albumRepository = new PerformerRepository();
        LinkedList<PerformerEntity> list;
        try {
            list = albumRepository.prepareQuery(new PerformerByIdSpecification(id));
        } finally {
            albumRepository.close();
        }
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;

    }

    public LinkedList<String> findAllPerformers() throws ConnectorException {
        PerformerRepository repository = new PerformerRepository();
        LinkedList<String> performers = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = repository.customQuery(new PerformerAllCustomSelectSpecification());
            for (HashMap<String, String> map : arguments) {
                performers.add(map.get(PerformerAllCustomSelectSpecification.PERFORMER_NAME_KEY));
            }
        } finally {
            repository.close();
        }
        return performers;
    }
}
