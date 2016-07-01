/*
 * Copyright (C) 2016. The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cloudkit.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * CloudkitExampleApplication.java
 *
 * implements CommandLineRunner
 *
 * @SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
 * @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
 * @ConfigurationProperties(prefix = "application")
 * @Configuration
 * @EnableAutoConfiguration
 * @ComponentScan
 * @ImportResource
 *
 * @ConfigurationProperties(prefix="server")
 * public class ServerProperties {
 *
 *     private String name;
 *     private Host host;
 *
 *     // ... getter and setters
 *
 *     private static class Host {
 *
 *         private String ip;
 *         private int port;
 *         // ... getter and setters
 *     }
 * }
 */
// @EnableAutoConfiguration, @ComponentScan 这两个注解可以使用SpringBootApplication替代
@SpringBootApplication
public class CloudkitExampleApplication {

    private static final Logger logger = LoggerFactory.getLogger(CloudkitExampleApplication.class);

    @PostConstruct
    public void logSomething() {
        //logger.debug("Sample Debug Message");
        //logger.trace("Sample Trace Message");
        logger.info("Sample Info Message");
    }

    /*
    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                logger.info("ServletContext initialized");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                logger.info("ServletContext destroyed");
            }

        };
    }
    */

    /*
    @Autowired
    private StringRedisTemplate template;

    @Override
    public void run(String... args) throws Exception {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String key = "spring.boot.redis.test";
        if (!this.template.hasKey(key)) {
            ops.set(key, "foo");
        }
        System.out.println("Found key " + key + ", value=" + ops.get(key));
    }
    */

    /*
    @Autowired
    private HelloWorldService helloWorldService;

    @Override
    public void run(String... args) {
        System.out.println(
            this.helloWorldService.getHelloMessage()
        );
    }
    */

	public static void main(String[] args) {
        // @ConfigurationProperties(prefix="my")
        SpringApplication application = new SpringApplication(CloudkitExampleApplication.class);
        // application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
        // ServletRegistrationBean register = new ServletRegistrationBean(new ServletContainer(),"*/");
        // register.addInitParameter(ServletProperties.FILTER_CONTEXT_PATH, ServletContextInitializer.class.getName());
        Map<String, Object> defaultMap = new HashMap<>();
        defaultMap.put("name", "Web");
        // 还可以是Properties对象
        application.setDefaultProperties(defaultMap);
		SpringApplication.run(CloudkitExampleApplication.class, args);
	}
}
