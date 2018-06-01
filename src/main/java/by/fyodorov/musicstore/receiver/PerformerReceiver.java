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
import java.util.Optional;

/**
 * receiver for perform operations with performers
 */
public class PerformerReceiver {
    private static final Logger LOGGER = LogManager.getLogger(PerformerReceiver.class);

    /**
     * finding performer by id
     * @param id - id of performer
     * @return - optional of performer
     * @throws ConnectorException - when can't execute select query
     */
    public Optional<PerformerEntity> findPerformerId(int id) throws ConnectorException {
        LOGGER.debug("finding performers by id = \"" + id + "\"");
        PerformerRepository albumRepository = new PerformerRepository();
        LinkedList<PerformerEntity> list;
        Optional<PerformerEntity> result = Optional.empty();
        try {
            list = albumRepository.prepareQuery(new PerformerByIdSpecification(id));
        } finally {
            albumRepository.close();
        }
        if (!list.isEmpty()) {
            result = Optional.of(list.getFirst());
        }
        return result;

    }

    /**
     * getting list of all performers
     * @return - list of all performers
     * @throws ConnectorException - when can't execute select query
     */
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
