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
//package net.cloudkit.ecosystem.enterprises.infrastructure.configuration;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * SessionConfiguration.java
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2015/11/19 18:38
// */
//public class SessionConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
//
//    private ApplicationContext applicationContext;
//
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    @Bean
//    public RedisHttpSessionConfiguration redisHttpSessionConfiguration() {
//        RedisHttpSessionConfiguration redisHttpSessionConfiguration =  new RedisHttpSessionConfiguration();
//        return redisHttpSessionConfiguration;
//    }
//
//}
