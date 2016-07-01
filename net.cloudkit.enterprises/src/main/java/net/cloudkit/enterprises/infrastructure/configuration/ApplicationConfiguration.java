/*
 * Copyright (C) 2015. The CloudKit Open Source Project
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

package net.cloudkit.enterprises.infrastructure.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * ApplicationConfiguration.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/11/19 16:10
 */
@Configuration
@ComponentScan(basePackages={"net.cloudkit.ecosystem.enterprises", "net.cloudkit.ecosystem.enterprises.interfaces"})
@ImportResource("classpath*:application-context.xml")
public class ApplicationConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    public ApplicationConfiguration() {
        System.out.println("AppConfig!");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDefaultAutoCommit(false);
        return basicDataSource;
    }

    public PropertiesFactoryBean propertiesFactoryBean() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Properties properties = new Properties();
        properties.put("text", 123456);
        propertiesFactoryBean.setProperties(properties);

        propertiesFactoryBean.setLocations();
        propertiesFactoryBean.setFileEncoding("UTF-8");
        return propertiesFactoryBean;
    }



    /**
     * Actor system singleton for this application.
     */
    /*
    @Bean(name = "ActorSystem")
    public ActorSystem actorSystem() {
        System.out.println("system");
        try {
            ActorSystem system = ActorSystem.create("AkkaJavaSpring");
            System.out.println("system");

            // initialize the application context in the Akka Spring Extension
            SpringExtProvider.get(system).initialize(applicationContext);
            return system;
        } catch (Exception e) {
            System.err.println("ActorSystem initialized exception: " + e);
        }

        System.err.println("no exception!");
        return null;
    }
    */
}
