//package net.cloudkit.ecosystem.enterprises;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.util.Arrays;
//import java.util.concurrent.CountDownLatch;
//
//@SpringBootApplication
//@EnableScheduling
//@EnableWebMvc
//@ComponentScan
//// @RestController
//@EnableAutoConfiguration
//@EnableIntegration
//public class Application {
//
//    private static final Logger logger = LoggerFactory.getLogger(Application.class);
//
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
//
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
//
//    @Bean
//    Receiver receiver(CountDownLatch latch) {
//        return new Receiver(latch);
//    }
//
//    @Bean
//    CountDownLatch latch() {
//        return new CountDownLatch(1);
//    }
//
//    @Bean
//    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
//        return new StringRedisTemplate(connectionFactory);
//    }
//
//    @RequestMapping("/")
//    public String home() {
//        return "Hello";
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        ApplicationContext ctx = SpringApplication.run(Application.class, args);
//
//        System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//        // TODO
//        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//        CountDownLatch latch = ctx.getBean(CountDownLatch.class);
//
//        logger.info("Sending message...");
//        template.convertAndSend("chat", "Hello from Redis!");
//
//        latch.await();
//
//        // TODO
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
//        System.exit(0);
//    }
//}
//
//class Receiver {
//    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
//
//    private CountDownLatch latch;
//
//    @Autowired
//    public Receiver(CountDownLatch latch) {
//        this.latch = latch;
//    }
//
//    public void receiveMessage(String message) {
//        logger.info("Received <" + message + ">");
//        latch.countDown();
//    }
//}
