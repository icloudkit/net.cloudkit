///*
// * Copyright (C) 2015. The CloudKit Open Source Project
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
//package net.cloudkit.flowportal;
//
//import org.activiti.engine.*;
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2015/12/24 18:15
// */
//@Configuration
//// @EnableActiviti
//public class ActivitiSimplestConfiguration {
//
//    @Autowired
//    private ProcessEngine processEngine;
//
//    @Autowired
//    private RuntimeService runtimeService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private HistoryService historyService;
//
//    @Autowired
//    private RepositoryService repositoryService;
//
//    @Autowired
//    private ManagementService managementService;
//
//    @Autowired
//    private FormService formService;
//
//
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setUsername("sa");
//        basicDataSource.setUrl("jdbc:h2:mem:anotherDatabase");
//        basicDataSource.setDefaultAutoCommit(false);
//        basicDataSource.setDriverClassName(org.h2.Driver.class.getName());
//        basicDataSource.setPassword("");
//        return basicDataSource;
//    }
//
//}
