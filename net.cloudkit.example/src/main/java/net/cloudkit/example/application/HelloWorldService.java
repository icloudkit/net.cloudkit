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
package net.cloudkit.example.application;

import com.mongodb.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * HelloWorldService.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/4 9:49
 */
@Component
public class HelloWorldService {

    @Value("${name:World}")
    private String name;

    public String getHelloMessage() {
        return "Hello " + this.name;
    }

    /*
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public HelloWorldService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    */

    /*
    // RedisConnectionFactory, StringRedisTemplate or vanilla RedisTemplate localhost:6379
    private StringRedisTemplate template;

    @Autowired
    public HelloWorldService(StringRedisTemplate template) {
        this.template = template;
    }
    */

    /*
    // org.springframework.data.mongodb.MongoDbFactory mongodb://localhost/test
    private MongoDbFactory mongo;

    @Autowired
    public HelloWorldService(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    public void example() {
        DB db = mongo.getDb();
        // ...
    }
    */

    /*
    private MongoTemplate mongoTemplate;

    @Autowired
    public HelloWorldService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    */

    /*
    private SolrServer solr;

    @Autowired
    public MyBean(SolrServer solr) {
        this.solr = solr;
    }
    */

    /*
    private ElasticsearchTemplate template;

    @Autowired
    public MyBean(ElasticsearchTemplate template) {
        this.template = template;
    }
    */

    /*
    private CassandraTemplate template;

    @Autowired
    public MyBean(CassandraTemplate template) {
        this.template = template;
    }
    */

    /*
    @CacheResult
    public int computePiDecimal(int i) {
        // ...
    }
    */

    /*
    private final JmsTemplate jmsTemplate;

    @Autowired
    public MyBean(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "someQueue")
    public void processMessage(String content) {
        // ...
    }
    */

    /*
     private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;

    @Autowired
    public MyBean(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }

    @RabbitListener(queues = "someQueue")
    public void processMessage(String content) {
        // ...
    }
    */


    /*
    // Inject the primary (XA aware) ConnectionFactory
    @Autowired
    private ConnectionFactory defaultConnectionFactory;

    // Inject the XA aware ConnectionFactory (uses the alias and injects the same as above)
    @Autowired
    @Qualifier("xaJmsConnectionFactory")
    private ConnectionFactory xaConnectionFactory;

    // Inject the non-XA aware ConnectionFactory
    @Autowired
    @Qualifier("nonXaJmsConnectionFactory")
    private ConnectionFactory nonXaConnectionFactory;
    */

    /*
    Remote shell commands
    classpath*:/commands/**
    classpath*:/crash/commands/**

    import org.crsh.cli.Command
    import org.crsh.cli.Usage
    import org.crsh.command.InvocationContext

    @Usage("Say Hello")
    @Command
    public String command(InvocationContext context) {
        return "Hello";
    }
    */
}
