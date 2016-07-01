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
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.TestRestTemplate;
//import org.springframework.boot.test.WebIntegrationTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
///**
// * Basic integration tests for demo application.
// *
// * @author Dave Syer
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(BootstrapApplication.class)
//@WebIntegrationTest(randomPort = true)
//@DirtiesContext
//public class SampleWebStaticApplicationTests {
//
//    @Value("${local.server.port}")
//    private int port = 0;
//
//    @Test
//    public void testHome() throws Exception {
//        ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port, String.class);
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//        assertTrue("Wrong body (title doesn't match):\n" + entity.getBody(), entity.getBody().contains("<title>Static"));
//    }
//
//    @Test
//    public void testCss() throws Exception {
//        ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port + "/webjars/bootstrap/3.0.3/css/bootstrap.min.css", String.class);
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//        assertTrue("Wrong body:\n" + entity.getBody(), entity.getBody().contains("body"));
//        assertEquals("Wrong content type:\n" + entity.getHeaders().getContentType(), MediaType.valueOf("text/css;charset=UTF-8"), entity.getHeaders().getContentType());
//    }
//
//}
