package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.repository.AssemblageRepository;
import by.fyodorov.musicstore.specification.assemblage.AssemblageCustomSelectSpecification;
import by.fyodorov.musicstore.specification.assemblage.custom.*;
import by.fyodorov.musicstore.view.AssemblageView;
import by.fyodorov.musicstore.view.AssemblageWithoutPriceView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

/**
 * receiver for performing operations with assemblages
 */
public class AssemblageReceiver {
    /**
     * getting list of info with price of assemblages for current user
     * @param userName - username of current user
     * @return - list of assemblages info
     * @throws ConnectorException - if can't execute select query
     */
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
        } finally {
            assemblageRepository.close();
        }
        return assemblages;
    }

    /**
     * getting list of info without price of assemblages for current user
     * @param userName - username of current user
     * @return - list of assemblages info
     * @throws ConnectorException - if can't execute select query
     */
    public LinkedList<AssemblageWithoutPriceView> findAssemblageForUser(String userName) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        AssemblageCustomSelectSpecification specification = new AssemblageOfUserByNameCustomSelectSpecification(userName);
        LinkedList<AssemblageWithoutPriceView> assemblages = new LinkedList<>();
        try {
            LinkedList<HashMap<String, String>> arguments = assemblageRepository.customQuery(specification);
            for (HashMap<String, String> map : arguments) {
                assemblages.add(new AssemblageWithoutPriceView(
                        map.get(AssemblageOfUserByNameCustomSelectSpecification.ASSEMBLAGE_NAME_KEY),
                        map.get(AssemblageOfUserByNameCustomSelectSpecification.ASSEMBLAGE_GENRE_KEY),
                        map.get(AssemblageOfUserByNameCustomSelectSpecification.ASSEMBLAGE_DATE_KEY),
                        map.get(AssemblageOfUserByNameCustomSelectSpecification.ASSEMBLAGE_OWNER_KEY)));
            }
        } finally {
            assemblageRepository.close();
        }
        return assemblages;
    }

    /**
     * getting assemblage info for current user
     * @param assemblageName - name of assemblage for info
     * @param userName - username of current user
     * @return - optional with assemblage info
     * @throws ConnectorException - if can't execute select query
     */
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
        } finally {
            assemblageRepository.close();
        }
        return result;
    }

    /***
     * adding new assemblage
     * @param assemblage - name of new assemblage
     * @param genre - genre of new assemblage
     * @param price - price of new assemblage
     * @param owner - owner of new assemblage
     * @param tracks - array of track for current user
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean addNewAssemblage(String assemblage, String genre, int price, String owner, String[] tracks) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        AssemblageRepository.modifyLock();
        try {
            result = assemblageRepository.prepareUpdate(new AssemblageAddCustomUpdateSpecification(assemblage, genre, price, owner)) > 0;
            for (String track : tracks) {
                result = result && assemblageRepository.prepareUpdate(new AssemblageInsertTrackCustomUpdateSpecification(assemblage, track)) > 0;
            }
        } catch (ConnectorException e) {
            result = false;
        } finally {
            AssemblageRepository.modifyUnlock();
            assemblageRepository.close();
        }
        return result;
    }

    /**
     * editing assemblage
     * @param oldName - old name of assemblage
     * @param newName - new name of assemblage
     * @param genre - new genre of assemblage
     * @param price - new price of assemblage
     * @param tracks - new array of tracks of assemblage
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean editAssemblage(String oldName, String newName, String genre, int price, String[] tracks) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        AssemblageRepository.modifyLock();
        try {
            result = assemblageRepository.prepareUpdate(new AssemblageEditCustomUpdateSpecification(oldName, newName, genre, price)) > 0;
            result = result && assemblageRepository.prepareUpdate(new AssemblageClearCustomUpdateSpecification(newName)) > 0;
            for (String track : tracks) {
                result = result && assemblageRepository.prepareUpdate(new AssemblageInsertTrackCustomUpdateSpecification(newName, track)) > 0;
            }
        } catch (ConnectorException e) {
            result = false;
        } finally {
            AssemblageRepository.modifyUnlock();
            assemblageRepository.close();
        }
        return result;
    }

    /**
     * removing assemblage
     * @param assemblageName - assemblage name for removing
     * @return - true - update successful
     *          false - update unsuccessful
     * @throws ConnectorException - if can't execute update query
     */
    public boolean removeAssemblage(String assemblageName) throws ConnectorException {
        AssemblageRepository assemblageRepository = new AssemblageRepository();
        boolean result;
        AssemblageRepository.modifyLock();
        try {
            result = assemblageRepository.prepareUpdate(new AssemblageDeleteCustomUpdateSpecification(assemblageName)) > 0;
        } finally {
            AssemblageRepository.modifyUnlock();
            assemblageRepository.close();
        }
        return result;
    }

}
