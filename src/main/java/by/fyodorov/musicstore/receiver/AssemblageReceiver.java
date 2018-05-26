package by.fyodorov.musicstore.receiver;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.AssemblageEntity;
import by.fyodorov.musicstore.repository.AlbumRepository;
import by.fyodorov.musicstore.repository.AssemblageRepository;
import by.fyodorov.musicstore.specification.album.custom.AlbumCustomSelectSpecification;
import by.fyodorov.musicstore.specification.album.custom.AlbumWithUserCustomSelectSpecification;
import by.fyodorov.musicstore.specification.assemblage.AssemblageByNameSpecification;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageCustomSelectSpecification;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageOfUserByNameCustomSelect;
import by.fyodorov.musicstore.specification.assemblage.custom.AssemblageWithUserCustomSelectSpecification;
import by.fyodorov.musicstore.view.AlbumView;
import by.fyodorov.musicstore.view.AssemblageView;
import by.fyodorov.musicstore.view.AssemblageWithoutPriceView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
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


}
