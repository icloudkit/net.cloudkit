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
//
//package net.cloudkit.ecosystem.enterprises.infrastructure.configuration;
//
///**
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2015/11/20 14:06
// */
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jredis.JredisConnectionFactory;
//import org.springframework.data.redis.connection.jredis.JredisPool;
//import org.springframework.data.redis.connection.srp.SrpConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.script.DefaultRedisScript;
//import org.springframework.data.redis.core.script.RedisScript;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.scripting.support.ResourceScriptSource;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import redis.clients.jedis.JedisPoolConfig;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
///**
// * Sample Configuration
// * http://jimgreat.iteye.com/blog/1596058
// * http://stackoverflow.com/questions/10750626/transactions-and-watch-statement-in-redis
// */
//@Configuration
//@ComponentScan(basePackages={"net.cloudkit.ecosystem.enterprises"})
//// @PropertySource('classpath:/redis.properties')
//public class RedisConfiguration {
//
//    /** #JedisPoolConfig properties */
//    @Value("${redis.testOnBorrow}")
//    private int maxIdle;
//
//    @Value("${redis.maxIdle}")
//    private boolean testOnBorrow;
//
//    /** #RedisConnectionFactory properties */
//    private boolean usePool = true;
//
//    @Value("${redis.host}")
//    private String hostName;
//
//    @Value("${redis.port}")
//    private int port;
//
//    @Value("${redis.password}")
//    private String password;
//
//    @Value("${redis.timeout}")
//    private int timeout;
//
//    @Value("${redis.default.database}")
//    private int database;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
////    // #execute a transaction
////    List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
////        public List<Object> execute(RedisOperations operations) throws DataAccessException {
////            operations.multi();
////            operations.opsForSet().add("key", "value1");
////
////            // This will contain the results of all ops in the transaction
////            return operations.exec();
////        }
////    });
////    System.out.println("Number of items added to set: " + txResults.get(0));
//
////    /** #Usage Constrainsts **/
////
////    // #executed on thread bound connection
////    template.opsForValue().set("foo", "bar");
////
////    // #read operation executed on a free (not tx-aware)
////    connection template.keys("*");
////
////    // #returns null as values set within transaction are not visible
////    template.opsForValue().get("foo");
//
//    @Bean
//    public StringRedisTemplate redisTemplate() {
//        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
//        // template.setConnectionFactory(jedisConnectionFactory());
//        // #explicitly enable transaction support
//        template.setEnableTransactionSupport(true);
//        return template;
//    }
//
//    /**
//     * jedis, lettuce, srp,...
//     */
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//
//        // #jedis
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig());
//        // jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
//        jedisConnectionFactory.setHostName(hostName);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.setDatabase(database);
//        jedisConnectionFactory.setUsePool(usePool);
//        jedisConnectionFactory.setTimeout(timeout);
//        return jedisConnectionFactory;
//
//        /*
//        // Redis Sentinel Support
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master("mymaster").sentinel("127.0.0.1", 26379).sentinel("127.0.0.1", 26380);
//        return new JedisConnectionFactory(sentinelConfig);
//        */
//
//        // #lettuce
//        /*
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
//        lettuceConnectionFactory.setHostName(hostName);
//        lettuceConnectionFactory.setPort(port);
//        lettuceConnectionFactory.setPassword(password);
//        lettuceConnectionFactory.setDatabase(database);
//        lettuceConnectionFactory.setValidateConnection(true);
//        lettuceConnectionFactory.setTimeout(timeout);
//
//        // Redis Sentinel Support
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master("mymaster").sentinel("127.0.0.1", 26379).sentinel("127.0.0.1", 26380);
//        return new LettuceConnectionFactory(sentinelConfig);
//        */
//
//        // jredis
//        /*
//        JredisPool jredisPool = new JredisPool(hostName, port);
//        JredisConnectionFactory jredisConnectionFactory = new JredisConnectionFactory(jredisPool);
//        // jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
//        jredisConnectionFactory.setHostName(hostName);
//        jredisConnectionFactory.setPort(port);
//        jredisConnectionFactory.setPassword(password);
//        jredisConnectionFactory.setDatabase(database);
//        return jredisConnectionFactory;
//        */
//
//        // srp
//        /*
//        SrpConnectionFactory srpConnectionFactory = new SrpConnectionFactory(hostName, port);
//        // srpConnectionFactory.setHostName(hostName);
//        // srpConnectionFactory.setPort(port);
//        srpConnectionFactory.setPassword(password);
//        return srpConnectionFactory;
//        */
//    }
//
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
//        return jedisPoolConfig;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() throws SQLException {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    // @Autowired
//    // RedisScript<Boolean> script;
//    // public boolean checkAndSet(String expectedValue, String newValue) {
//    //     return redisTemplate.execute(script, Collections.singletonList("key"), expectedValue, newValue);
//    // }
//    @Bean
//    public RedisScript<Boolean> script() {
//        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
//        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("META-INF/scripts/checkandset.lua")));
//        redisScript.setResultType(Boolean.class);
//        return redisScript;
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        return new RedisCacheManager(redisTemplate());
//    }
//}