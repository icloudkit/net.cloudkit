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
package net.cloudkit.flowportal.infrastructure.persistence;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 多数据源负载均衡. PrototypeLoadBalanceDataSource.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月22日 上午9:49:50
 */
public class BalanceRoutingDataSource extends AbstractRoutingDataSource {

    // private Lock lock = new ReentrantLock();
    // private int counter = 0;
    // private int dataSourceNumber = 3;

    @Override
    protected Object determineCurrentLookupKey() {
        /*
        lock.lock();
        try{
            counter++;
            int lookupKey = counter % getDataSourceNumber();
            return new Integer(lookupKey);
        }finally{
            lock.unlock();
        }
        */

        return DataSourceHolder.getDataSourceName();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        afterPropertiesSet();
    }

    /**
     * @return the dataSourceNumber
     */
    /*
    public int getDataSourceNumber() {
        return dataSourceNumber;
    }
    */
    /**
     * @param dataSourceNumber the dataSourceNumber to set
     */
    /*
    public void setDataSourceNumber(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }
    */
}
