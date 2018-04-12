package test.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.repository.TrackRepository;
import by.fyodorov.musicstore.specification.track.TrackByNameSpecification;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.LinkedList;

public class TrackRepositoryTest {

    @Test
    public void addTrackTest() throws Exception {
        ConnectionPool.getInstance("db.properties");

        TrackRepository repository = new TrackRepository();
        repository.add(new TrackEntity(0, "spectrum", "electro", 12, new Date(), 1));
        repository.close();

        TrackRepository repository1 = new TrackRepository();
        LinkedList<TrackEntity> list = repository1.prepareQuery(new TrackByNameSpecification("spectrum"));
        System.out.println(list.getFirst());
        repository1.close();
    }
}
