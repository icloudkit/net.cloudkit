package net.cloudkit.enterprises.application.commons;

import java.util.Set;

/**
 * redis 接口
 */
public interface RedisService {

    /**
     * 通过key删除
     *
     * @param keys
     */
    Long del(String... keys);

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime 单位秒
     */
    void set(String key, String value, long liveTime);

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    Set keys(String pattern);

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    String flushDb();

    /**
     * 查看redis里有多少数据
     */
    Long dbSize();

    /**
     * 检查是否连接成功
     *
     * @return
     */
    String ping();

}
