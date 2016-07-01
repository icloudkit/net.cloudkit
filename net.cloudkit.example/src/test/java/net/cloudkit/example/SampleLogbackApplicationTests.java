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
//import org.junit.Rule;
//import org.junit.Test;
//import org.springframework.boot.test.OutputCapture;
//
//import static org.hamcrest.Matchers.containsString;
//
//public class SampleLogbackApplicationTests {
//
//    @Rule
//    public OutputCapture outputCapture = new OutputCapture();
//
//    @Test
//    public void testLoadedCustomLogbackConfig() throws Exception {
//        BootstrapApplication.main(new String[0]);
//        //this.outputCapture.expect(containsString("Sample Debug Message"));
//        //this.outputCapture.expect(not(containsString("Sample Trace Message")));
//        this.outputCapture.expect(containsString("Sample Info Message"));
//    }
//
//    @Test
//    public void testProfile() throws Exception {
//        BootstrapApplication.main(new String[]{"--spring.profiles.active=staging"});
//        //this.outputCapture.expect(containsString("Sample Debug Message"));
//        //this.outputCapture.expect(containsString("Sample Trace Message"));
//        this.outputCapture.expect(containsString("Sample Info Message"));
//    }
//
//}
