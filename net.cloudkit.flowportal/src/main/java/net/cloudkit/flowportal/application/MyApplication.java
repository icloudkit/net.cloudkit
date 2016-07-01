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
//package net.cloudkit.flowportal.application;
//
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2015/12/24 18:35
// */
//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
//public class MyApplication {
//
//    @Autowired
//    private MyService myService;
//
//    public static void main(String[] args) {
//        SpringApplication.run(MyApplication.class, args);
//    }
//
////    @Bean
////    public DataSource database() {
////        return DataSourceBuilder.create()
////            .url("jdbc:mysql://127.0.0.1:3306/activiti-spring-boot?characterEncoding=UTF-8")
////            .username("alfresco")
////            .password("alfresco")
////            .driverClassName("com.mysql.jdbc.Driver")
////            .build();
////    }
//
//    @Bean
//    public CommandLineRunner init(final RepositoryService repositoryService,
//                                  final RuntimeService runtimeService,
//                                  final TaskService taskService) {
//
//        return new CommandLineRunner() {
//            /*
//            @Override
//            public void run(String... strings) throws Exception {
//                System.out.println("Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
//                System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
//                runtimeService.startProcessInstanceByKey("oneTaskProcess");
//                System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
//            }
//            */
//
//            public void run (String...strings)throws Exception {
//                myService.createDemoUsers();
//            }
//        };
//
//    }
//
//    /*
//    String barFileName = "path/to/process-one.bar";
//    ZipInputStream inputStream = new ZipInputStream(new FileInputStream(barFileName));
//
//    repositoryService.createDeployment()
//        .name("process-one.bar")
//        .addZipInputStream(inputStream)
//        .deploy();
//
//
//    repositoryService.createDeployment()
//        .category("yourCategory")
//        .name("expense-process.bar")
//        .addClasspathResource("org/activiti/expenseProcess.bpmn20.xml")
//        .addClasspathResource("org/activiti/expenseProcess.png")
//        .deploy();
//
//    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//        .processDefinitionKey("expense")
//        .singleResult();
//
//    String diagramResourceName = processDefinition.getDiagramResourceName();
//    InputStream imageStream = repositoryService.getResourceAsStream(
//    processDefinition.getDeploymentId(), diagramResourceName);
//    */
//
//}
