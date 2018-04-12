package test.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.repository.PerformerRepository;
import by.fyodorov.musicstore.specification.performer.PerformerByIdSpecification;
import by.fyodorov.musicstore.specification.performer.PerformerByNameSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;

public class PerformerRepositoryTest {
    @Test
    public void addPerformerTest() throws Exception {
        ConnectionPool.getInstance("db.properties");

        PerformerRepository repository = new PerformerRepository();
        repository.add(new PerformerEntity("muzzy"));
        repository.close();

        PerformerRepository repository1 = new PerformerRepository();
        LinkedList<PerformerEntity> list = repository1.prepareQuery(new PerformerByNameSpecification("muzzy"));
        PerformerEntity performer = list.getFirst();
        repository1.close();

        PerformerRepository repository3 = new PerformerRepository();
        LinkedList<PerformerEntity> list2 = repository3.prepareQuery(new PerformerByIdSpecification(performer.getId()));
        Assert.assertEquals(performer, list2.getFirst());
        repository3.close();

        PerformerRepository repository2 = new PerformerRepository();
        repository2.remove(performer);
        repository2.close();
    }
}
