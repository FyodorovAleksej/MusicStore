package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.AssemblageEntity;
import by.fyodorov.musicstore.repository.AssemblageRepository;
import by.fyodorov.musicstore.specification.assemblage.AssemblageByNameSpecification;
import by.fyodorov.musicstore.specification.assemblage.custom.*;
import by.fyodorov.musicstore.view.AssemblageView;
import by.fyodorov.musicstore.view.AssemblageWithoutPriceView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

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

    public LinkedList<AssemblageView> findAssemblageInfo(String userName) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        AssemblageCustomSelectSpecification specification = new AssemblageWithUserCustomSelectSpecification(userName);
        LinkedList<AssemblageView> assemblages = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = assemblageRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                int price = Integer.valueOf(map.get(AssemblageWithUserCustomSelectSpecification.ASSEMBLAGE_PRICE_KEY));
                assemblages.add(new AssemblageView(
                        map.get(AssemblageWithUserCustomSelectSpecification.ASSEMBLAGE_NAME_KEY),
                        map.get(AssemblageWithUserCustomSelectSpecification.ASSEMBLAGE_DATE_KEY),
                        map.get(AssemblageWithUserCustomSelectSpecification.ASSEMBLAGE_GENRE_KEY),
                        map.get(AssemblageWithUserCustomSelectSpecification.ASSEMBLAGE_OWNER_KEY),
                        price,
                        Integer.valueOf(map.getOrDefault(AssemblageWithUserCustomSelectSpecification.ASSEMBLAGE_PRICE_SUMMARY_KEY, Integer.toString(price)))));
            }
        }
        finally {
            assemblageRepository.close();
        }
        return assemblages;
    }

    public LinkedList<AssemblageWithoutPriceView> findAssemblageForUser(String userName) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        AssemblageCustomSelectSpecification specification = new AssemblageOfUserByNameCustomSelect(userName);
        LinkedList<AssemblageWithoutPriceView> assemblages = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = assemblageRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                assemblages.add(new AssemblageWithoutPriceView(
                        map.get(AssemblageOfUserByNameCustomSelect.ASSEMBLAGE_NAME_KEY),
                        map.get(AssemblageOfUserByNameCustomSelect.ASSEMBLAGE_GENRE_KEY),
                        map.get(AssemblageOfUserByNameCustomSelect.ASSEMBLAGE_DATE_KEY),
                        map.get(AssemblageOfUserByNameCustomSelect.ASSEMBLAGE_OWNER_KEY)));
            }
        }
        finally {
            assemblageRepository.close();
        }
        return assemblages;
    }

    public Optional<AssemblageView> assemblageInfoForUser(String assemblageName, String userName) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        AssemblageCustomSelectSpecification specification = new AssemblageInfoForUserCustomSelectSpecification(assemblageName, userName);
        Optional<AssemblageView> result = Optional.empty();
        try {
            LinkedList<HashMap<String, String>> arguments = assemblageRepository.customQuery(specification);
            if (!arguments.isEmpty()) {
                HashMap<String, String> map = arguments.getFirst();
                int price = Integer.valueOf(map.get(AssemblageInfoForUserCustomSelectSpecification.ASSEMBLAGE_PRICE_KEY));
                result = Optional.of(new AssemblageView(
                        map.get(AssemblageInfoForUserCustomSelectSpecification.ASSEMBLAGE_NAME_KEY),
                        map.get(AssemblageInfoForUserCustomSelectSpecification.ASSEMBLAGE_DATE_KEY),
                        map.get(AssemblageInfoForUserCustomSelectSpecification.ASSEMBLAGE_GENRE_KEY),
                        map.get(AssemblageInfoForUserCustomSelectSpecification.ASSEMBLAGE_OWNER_KEY),
                        price,
                        Integer.valueOf(map.getOrDefault(AssemblageInfoForUserCustomSelectSpecification.ASSEMBLAGE_PRICE_SUMMARY_KEY, Integer.toString(price)))));
            }
        }
        finally {
            assemblageRepository.close();
        }
        return result;
    }

    public boolean addNewAssemblage(String assemblage, String genre, int price, String performer) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        try {
            assemblageRepository.prepareUpdate(new AssemblageAddCustomSelectSpecification(assemblage, genre, price, performer));
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

    public boolean addNewAssemblage(String assemblage, String genre, int price, String owner, String[] tracks) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        AssemblageRepository.modifyLock();
        try {
            result = assemblageRepository.prepareUpdate(new AssemblageAddCustomSelectSpecification(assemblage, genre, price, owner)) > 0;
            for (String track : tracks) {
                result = result && assemblageRepository.prepareUpdate(new AssemblageInsertTrackCustomSelectSpecification(assemblage, track)) > 0;
            }
        }
        catch (ConnectorException e) {
            result = false;
        }
        finally {
            AssemblageRepository.modifyUnlock();
            assemblageRepository.close();
        }
        return result;
    }

    public boolean editAssemblage(String oldName, String newName, String genre, int price, String[] tracks) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        AssemblageRepository.modifyLock();
        try {
            result = assemblageRepository.prepareUpdate(new AssemblageEditCustomSelectSpecification(oldName, newName, genre, price)) > 0;
            result = result && assemblageRepository.prepareUpdate(new AssemblageClearCustomSelectSpecification(newName)) > 0;
            for (String track : tracks) {
                result = result && assemblageRepository.prepareUpdate(new AssemblageInsertTrackCustomSelectSpecification(newName, track)) > 0;
            }
        }
        catch (ConnectorException e) {
            result = false;
        }
        finally {
            AssemblageRepository.modifyUnlock();
            assemblageRepository.close();
        }
        return result;
    }

    public boolean assemblageClear(String assemblageName) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        AssemblageRepository.modifyLock();
        try {
            result = assemblageRepository.prepareUpdate(new AssemblageClearCustomSelectSpecification(assemblageName)) > 0;
        }
        finally {
            AssemblageRepository.modifyUnlock();
            assemblageRepository.close();
        }
        return result;
    }

    public boolean removeAssemblage(String assemblageName) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        AssemblageRepository.modifyLock();
        try {
            result = assemblageRepository.prepareUpdate(new AssemblageDeleteCustomSpecification(assemblageName)) > 0;
        }
        finally {
            AssemblageRepository.modifyUnlock();
            assemblageRepository.close();
        }
        return result;
    }

}
