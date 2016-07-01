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
//package net.cloudkit.example.domain.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import java.io.Serializable;
//
///**
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2016/1/4 19:44
// */
//@Entity
//public class City implements Serializable {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String country;
//
//    // ... additional members, often include @OneToMany mappings
//
//    protected City() {
//        // no-args constructor required by JPA spec
//        // this one is protected since it shouldn't be used directly
//    }
//
//    public City(String name, String country) {
//        this.name = name;
//        this.country = country;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public String getCountry() {
//        return this.country;
//    }
//
//    // ... etc
//
//}
