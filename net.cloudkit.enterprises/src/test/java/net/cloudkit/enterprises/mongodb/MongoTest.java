package net.cloudkit.enterprises.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "classpath:/app-config.xml"
@ContextConfiguration(locations = {"classpath*:application-context*.xml"})
@ActiveProfiles("development")
public class MongoTest {

    @Autowired
    MongoOperations mongoTemplate;

    @Test
    public void test() {
        // production
        // System.setProperty("spring.profiles.active", "development");

        // TODO
        /*
        mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), Users.class, USER_COLLECTION);
        mongoTemplate.remove(new Query(Criteria.where("name").is(name)),Users.class,USER_COLLECTION);
        mongoTemplate.updateFirst(new Query(Criteria.where("name").is(name)), Update.update(key, value), Users.class);
        mongoTemplate.findAll(Users.class);
        */
    }

    public static void main(String[] args) {

    }


}
