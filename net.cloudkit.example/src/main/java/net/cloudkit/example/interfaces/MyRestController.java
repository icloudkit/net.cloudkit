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
//package net.cloudkit.example.interfaces;
//
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2016/1/4 19:29
// */
//@RestController
//@RequestMapping(value="/users")
//public class MyRestController {
//
//    @RequestMapping(value="/{user}", method=RequestMethod.GET)
//    public User getUser(@PathVariable Long user) {
//        // ...
//    }
//
//    @RequestMapping(value="/{user}/customers", method=RequestMethod.GET)
//    List<Customer> getUserCustomers(@PathVariable Long user) {
//        // ...
//    }
//
//    @RequestMapping(value="/{user}", method= RequestMethod.DELETE)
//    public User deleteUser(@PathVariable Long user) {
//        // ...
//    }
//
//}
