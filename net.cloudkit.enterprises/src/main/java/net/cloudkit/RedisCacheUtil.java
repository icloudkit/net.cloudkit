/*
 * Copyright (C) 2015. The CloudKit Open Source Project
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

package net.cloudkit;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/11/23 12:35
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheUtil<T> {
    @Autowired
    @Qualifier("jedisTemplate")
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key       缓存键值
     * // @param operation
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key/*,ValueOperations<String,T> operation*/) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.rightPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        for (int i = 0; i < size; i++) {
            dataList.add((T) listOperation.leftPop(key));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
    /*T[] t = (T[]) dataSet.toArray();
       setOperation.add(t);*/
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * // @param operation
     * @return
     */
    public Set<T> getCacheSet(String key/*,BoundSetOperations<String,T> operation*/) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
        /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  */
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * // @param hashOperation
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        /*Map<String, T> map = hashOperation.entries(key);*/
        return map;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
                /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  */
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * // @param hashOperation
     * @return
     */
    public <T> Map<Integer, T> getCacheIntegerMap(String key/*,HashOperations<String,String,T> hashOperation*/) {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
    /*Map<String, T> map = hashOperation.entries(key);*/
        return map;
    }
}


/*
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import com.test.model.City;
import com.test.model.Country;
import com.zcr.test.User;

*//*
 * 监听器，用于项目启动的时候初始化信息
 *//*
@Service
public class StartAddCacheListener implements ApplicationListener<ContextRefreshedEvent> {
    //日志
    private final Logger log = Logger.getLogger(StartAddCacheListener.class);
    @Autowired
    private RedisCacheUtil<Object> redisCache;
    @Autowired
    private BrandStoreService brandStoreService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //spring 启动的时候缓存城市和国家等信息
        if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
            System.out.println("\n\n\n_________\n\n缓存数据 \n\n ________\n\n\n\n");
            List<City> cityList = brandStoreService.selectAllCityMessage();
            List<Country> countryList = brandStoreService.selectAllCountryMessage();
            Map<Integer, City> cityMap = new HashMap<Integer, City>();
            Map<Integer, Country> countryMap = new HashMap<Integer, Country>();
            int cityListSize = cityList.size();
            int countryListSize = countryList.size();
            for (int i = 0; i < cityListSize; i++) {
                cityMap.put(cityList.get(i).getCity_id(), cityList.get(i));
            }
            for (int i = 0; i < countryListSize; i++) {
                countryMap.put(countryList.get(i).getCountry_id(), countryList.get(i));
            }
            redisCache.setCacheIntegerMap("cityMap", cityMap);
            redisCache.setCacheIntegerMap("countryMap", countryMap);
        }
    }
}


@Autowired
private RedisCacheUtil<User> redisCache;

@RequestMapping("testGetCache")
public void testGetCache() {
    *//*Map<String,Country> countryMap = redisCacheUtil1.getCacheMap("country");
    Map<String,City> cityMap = redisCacheUtil.getCacheMap("city");*//*
    Map<Integer, Country> countryMap = redisCacheUtil1.getCacheIntegerMap("countryMap");
    Map<Integer, City> cityMap = redisCacheUtil.getCacheIntegerMap("cityMap");
    for (int key : countryMap.keySet()) {
        System.out.println("key = " + key + ",value=" + countryMap.get(key));
    }
    System.out.println("------------city");
    for (int key : cityMap.keySet()) {
        System.out.println("key = " + key + ",value=" + cityMap.get(key));
    }
}
*/
