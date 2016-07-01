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
//package net.cloudkit.example.infrastructure.configuration;
//
///**
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2016/1/4 9:17
// */
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//
///**
// * RedisHttpSessionConfig.java
// * <p/>
// * req.getSession().setAttribute(attributeName, attributeValue);
// * req.getSession().setAttribute("user", new User(uid, "张三", "这里是redis的"));
// */
//@EnableRedisHttpSession
//public class RedisHttpSessionConfig {
//
//    @Bean
//    public JedisConnectionFactory connectionFactory() {
//        JedisConnectionFactory connection = new JedisConnectionFactory();
//        connection.setHostName("192.168.1.197");
//        connection.setPort(6379);
//        connection.setPassword("root");
//        // connection.setUsePool(true);
//        // connection.setPoolConfig(new JedisPoolConfig());
//        return connection;
//    }
//}
//
