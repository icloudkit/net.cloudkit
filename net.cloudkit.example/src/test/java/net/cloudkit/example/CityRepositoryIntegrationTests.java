///*
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
//package net.cloudkit.example;
//
//import net.cloudkit.example.domain.model.CityRepository;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.*;
//import org.springframework.http.HttpHeaders;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.client.RestTemplate;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.junit.Assert.assertThat;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(BootstrapApplication.class)
//@WebIntegrationTest
////@WebIntegrationTest({"server.port=0", "management.port=0"})
////@WebIntegrationTest(randomPort = true)
////@ContextConfiguration(classes = BootstrapApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
//public class CityRepositoryIntegrationTests {
//
//    @Autowired
//    CityRepository repository;
//
//    RestTemplate restTemplate = new TestRestTemplate();
//
//    // ... interact with the running server
//
//    // ...
//
//    @Rule
//    public OutputCapture capture = new OutputCapture();
//
//    @Test
//    public void testName() throws Exception {
//        // EnvironmentTestUtils.addEnvironment(env, "org=Spring", "name=Boot");
//
//        System.out.println("Hello World!");
//        assertThat(capture.toString(), containsString("World"));
//    }
//
//    RestTemplate template = new TestRestTemplate();
//
//    @Test
//    public void testRequest() throws Exception {
//        HttpHeaders headers = template.getForEntity("http://myhost.com", String.class).getHeaders();
//        assertThat(headers.getLocation().toString(), containsString("myotherhost"));
//    }
//
//}
