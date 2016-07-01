package net.cloudkit.ecosystem.infrastructure.configuration;///*
// * Copyright (C) 2016. The CloudKit Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package net.cloudkit.example.infrastructure.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
///**
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2016/1/13 10:52
// */
//@Configuration
//@EnableActiviti
//@EnableTransactionManagement(proxyTargetClass = true)
//class JPAConfiguration {
//
//    @Bean
//    public OpenJpaVendorAdapter openJpaVendorAdapter() {
//        OpenJpaVendorAdapter openJpaVendorAdapter = new OpenJpaVendorAdapter();
//        openJpaVendorAdapter.setDatabasePlatform(H2Dictionary.class.getName());
//        return openJpaVendorAdapter;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setUsername("sa");
//        basicDataSource.setUrl("jdbc:h2:mem:activiti");
//        basicDataSource.setDefaultAutoCommit(false);
//        basicDataSource.setDriverClassName(org.h2.Driver.class.getName());
//        basicDataSource.setPassword("");
//        return basicDataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
//        OpenJpaVendorAdapter openJpaVendorAdapter, DataSource ds) {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setPersistenceXmlLocation("classpath:/org/activiti/spring/test/jpa/custom-persistence.xml");
//        emf.setJpaVendorAdapter(openJpaVendorAdapter);
//        emf.setDataSource(ds);
//        return emf;
//    }
//
//    @Bean
//    public PlatformTransactionManager jpaTransactionManager(
//        EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    @Bean
//    public AbstractActivitiConfigurer abstractActivitiConfigurer(
//        final EntityManagerFactory emf,
//        final PlatformTransactionManager transactionManager) {
//
//        return new AbstractActivitiConfigurer() {
//
//            @Override
//            public void postProcessSpringProcessEngineConfiguration(SpringProcessEngineConfiguration engine) {
//                engine.setTransactionManager(transactionManager);
//                engine.setJpaEntityManagerFactory(emf);
//                engine.setJpaHandleTransaction(false);
//                engine.setJobExecutorActivate(false);
//                engine.setJpaCloseEntityManager(false);
//                engine.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//            }
//        };
//    }
//
//    // A random bean
//    @Bean
//    public LoanRequestBean loanRequestBean() {
//        return new LoanRequestBean();
//    }
//}
