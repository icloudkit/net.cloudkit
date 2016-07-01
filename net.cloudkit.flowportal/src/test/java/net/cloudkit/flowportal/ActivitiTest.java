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
package net.cloudkit.flowportal;

import net.cloudkit.flowportal.application.UserBeanService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/11/20 16:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:application-context*.xml"})
// @ContextConfiguration({"classpath:application-context-activiti.xml", "classpath:application-context.xml"})
@ActiveProfiles("development")
public class ActivitiTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    @Rule
    public ActivitiRule activitiRule;

    @Test
    // @Deployment
    public void simpleProcessTest() {
        try {

            runtimeService.startProcessInstanceByKey("helloProcess");
            runtimeService.startProcessInstanceByKey("taskProcess");

            // 对应Activiti ProcessDefinition对象的key属性。 通过id来启动流程定义的流程实例
            // ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("taskProcess");

            // 任务列表
            List<Task> tasks = taskService.createTaskQuery().list();
            // List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("kermit").list();
            // List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();

            // 领取任务
            // 认领这个任务,任务会进入认领任务人的个人任务列表中
            // taskService.claim(task.getId(), "fozzie");
            // 任务会进入认领任务人的个人任务列表中
            // List<Task> tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();

            // 完成任务
            // taskService.complete(task.getId());

            // 使用historyService判断流程已经结束了
            // HistoryService historyService = processEngine.getHistoryService();
            // HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
            // System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());


            // Task task = taskService.createTaskQuery().singleResult();
            for (Task task : tasks) {
                // assertEquals("My Task", task.getName());
                // 完成任务
                taskService.complete(task.getId());
                // assertEquals(0, runtimeService.createProcessInstanceQuery().count());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getHello() throws Exception {
        // RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");

        String deploymentId = repositoryService.createDeployment()
            .addClasspathResource("hello.bpmn20.xml")
            // 提供流程图片
            //.addClasspathResource("org/activiti/expenseProcess.png")
            .name("hello")
            // 类别
            .category("test")
            .tenantId("10000")
            .deploy()
            .getId();
        System.out.println("deploymentId:" + deploymentId);

        /*
        // 可以通过API来获取流程定义图片资源
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey("expense")
            .singleResult();

        String diagramResourceName = processDefinition.getDiagramResourceName();
        InputStream imageStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);
        */


        /*
        // 通过一个压缩文件（支持Zip和Bar）部署业务归档
        String barFileName = "path/to/process-one.bar";
        ZipInputStream inputStream = new ZipInputStream(new FileInputStream(barFileName));

        repositoryService.createDeployment()
            .name("process-one.bar")
            .addZipInputStream(inputStream)
            .deploy();
        */

        // UserBeanService userBean = (UserBeanService) applicationContext.getBean("userBeanService");
        // userBean.hello();

        /*
        // Create Activiti process engine
        ProcessEngine processEngine = ProcessEngineConfiguration
            .createStandaloneProcessEngineConfiguration()
            .buildProcessEngine();

        // Get Activiti services
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // Deploy the process definition
        repositoryService.createDeployment()
            .addClasspathResource("FinancialReportProcess.bpmn20.xml")
            .deploy();

        // Start a process instance
        runtimeService.startProcessInstanceByKey("financialReport");
        */


    }


    /*
    // 代码总结
    public static void main(String[] args) {

        // Create Activiti process engine
        ProcessEngine processEngine = ProcessEngineConfiguration
            .createStandaloneProcessEngineConfiguration()
            .buildProcessEngine();

        // Get Activiti services
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // Deploy the process definition
        repositoryService.createDeployment()
            .addClasspathResource("FinancialReportProcess.bpmn20.xml")
            .deploy();

        // Start a process instance
        String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();

        // Get the first task
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for accountancy group: " + task.getName());

            // claim it
            taskService.claim(task.getId(), "fozzie");
        }

        // Verify Fozzie can now retrieve the task
        tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
        for (Task task : tasks) {
            System.out.println("Task for fozzie: " + task.getName());

            // Complete the task
            taskService.complete(task.getId());
        }

        System.out.println("Number of tasks for fozzie: "
            + taskService.createTaskQuery().taskAssignee("fozzie").count());

        // Retrieve and claim the second task
        tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for accountancy group: " + task.getName());
            taskService.claim(task.getId(), "kermit");
        }

        // Completing the second task ends the process
        for (Task task : tasks) {
            taskService.complete(task.getId());
        }

        // verify that the process is actually finished
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance =
            historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
        System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
    }
    */
}
