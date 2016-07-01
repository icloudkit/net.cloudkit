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

package net.cloudkit.ecosystem.domain.model.user;

import static org.junit.Assert.assertTrue;

import net.cloudkit.ecosystem.application.CloudKitEcosystemApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * UserRepositoryTest.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/3/17 19:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CloudKitEcosystemApplication.class)
//@SpringApplicationConfiguration(classes = MockServletContext.class)
//@WebIntegrationTest
//@WebIntegrationTest({"server.port=0", "management.port=0"})
//@WebIntegrationTest(randomPort = true)
//@ContextConfiguration(classes = CloudKitEcosystemApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
public class UserRepositoryTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    private String profiles;

    @Resource
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        this.profiles = System.getProperty("spring.profiles.active");
    }

    @After
    public void tearDown() throws Exception {
        if (this.profiles != null) {
            System.setProperty("spring.profiles.active", this.profiles);
        } else {
            System.clearProperty("spring.profiles.active");
        }
    }

    @Test
    public void testDefaultSettings() throws Exception {
        CloudKitEcosystemApplication.main(new String[0]);
        String output = this.outputCapture.toString();
        assertTrue("Wrong output: " + output, output.contains("Hello Phil"));
    }

    @Test
    public void testCommandLineOverrides() throws Exception {
        CloudKitEcosystemApplication.main(new String[]{"--name=Gordon"});
        String output = this.outputCapture.toString();
        assertTrue("Wrong output: " + output, output.contains("Hello Gordon"));
    }

    @Test
    public void testFindrrfById() throws Exception {
        userRepository.findById(1L);
    }
}
