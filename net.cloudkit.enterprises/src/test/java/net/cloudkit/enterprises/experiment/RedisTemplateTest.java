//package net.cloudkit.ecological.enterprises.experiment;
//
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.config.PropertiesFactoryBean;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//// ApplicationContext will be loaded from "classpath:/app-config.xml"
//@ContextConfiguration(locations = {"classpath*:application-context*.xml"})
//@ActiveProfiles("development")
////@SpringApplicationConfiguration(classes = Application.class)
////@WebAppConfiguration
////@IntegrationTest("server.port=0")
////@DirtiesContext
//public class RedisTemplateTest {
//
//    private static final Logger logger = Logger.getLogger(RedisTemplateTest.class);
//
//    @Resource
//    protected RedisTemplate<String, String> redisTemplate;
//
//    @SuppressWarnings(value = "unchecked")
//    @Test
//    public void test() {
//        // production development
//        System.setProperty("spring.profiles.active", "development");
//
//        /*
//        // TODO
//        // 新增
//        Boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
//
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = getRedisSerializer();
//                byte[] key  = serializer.serialize("test");
//                byte[] name = serializer.serialize("Coffee");
//                return connection.setNX(key, name);
//            }
//        });
//        */
//
//        // 批量新增
//        /*
//        Assert.notEmpty(list);
//        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
//            public Boolean doInRedis(RedisConnection connection)
//                    throws DataAccessException {
//                RedisSerializer<String> serializer = getRedisSerializer();
//                for (User user : list) {
//                    byte[] key  = serializer.serialize(user.getId());
//                    byte[] name = serializer.serialize(user.getName());
//                    connection.setNX(key, name);
//                }
//                return true;
//            }
//        }, false, true);
//        */
//
//        // 删除多个
//        // redisTemplate.delete(keys);
//
//        // 修改
//        /*
//        String key = user.getId();
//        if (get(key) == null) {
//            throw new NullPointerException("数据行不存在, key = " + key);
//        }
//        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
//            public Boolean doInRedis(RedisConnection connection)
//                    throws DataAccessException {
//                RedisSerializer<String> serializer = getRedisSerializer();
//                byte[] key  = serializer.serialize(user.getId());
//                byte[] name = serializer.serialize(user.getName());
//                connection.set(key, name);
//                return true;
//            }
//        });
//        */
//
//        // 通过key获取
//        String result2 = (String) redisTemplate.execute(new RedisCallback<String>() {
//            public String doInRedis(RedisConnection connection)
//                    throws DataAccessException {
//                RedisSerializer<String> serializer = getRedisSerializer();
//                byte[] key = serializer.serialize("test");
//                byte[] value = connection.get(key);
//                if (value == null) {
//                    return null;
//                }
//                String name = serializer.deserialize(value);
//                return name;
//            }
//        });
//        System.out.println(result2);
//    }
//
//    /**
//     * 设置redisTemplate
//     * @param redisTemplate the redisTemplate to set
//     */
//    @SuppressWarnings(value = "unchecked")
//    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    /**
//     * 获取 RedisSerializer
//     */
//    protected RedisSerializer<String> getRedisSerializer() {
//        return redisTemplate.getStringSerializer();
//    }
//}
