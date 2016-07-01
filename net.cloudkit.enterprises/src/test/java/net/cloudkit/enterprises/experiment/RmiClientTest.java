package net.cloudkit.enterprises.experiment;

import net.cloudkit.enterprises.interfaces.member.remoting.MemberRemoteService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "classpath:/app-config.xml"
@ContextConfiguration(locations = {"classpath*:application-context*.xml"})
@ActiveProfiles("development")
public class RmiClientTest {

    private static final Logger logger = Logger.getLogger(RmiClientTest.class);

    @Resource
    private MemberRemoteService memberRemoteService;

    @Test
    public void test() {
        // production
        // System.setProperty("spring.profiles.active", "development");

        // TODO
        /*
        User user = new User();
        Object object = userRemoteService.findByEmail("simple@qq.com");
        BeanUtils.copyProperties(object, user);
        System.out.println(user.getUsername());
        */

        System.out.println(memberRemoteService.sayHello());
    }

    public static void main(String[] args) {

    }

}
