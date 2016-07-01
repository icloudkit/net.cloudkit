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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多数据源负载均衡. DataSourceHolder.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月22日 上午9:49:50
 */
public class DataSourceHolder {

    protected static final Logger logger = LoggerFactory.getLogger(DataSourceHolder.class);

    private static final ThreadLocal<String> dataSourceName = new ThreadLocal<String>();


    public static String getDataSourceName() {
        String dataSourceName = (String) DataSourceHolder.dataSourceName.get();
        if (dataSourceName != null) {
            logger.info("DataSource Name:[" + dataSourceName + "]");
        }
        return DataSourceHolder.dataSourceName.get();
    }


    public static void setDataSourceName(String dataSourceName) {
        DataSourceHolder.dataSourceName.set(dataSourceName);
    }

    public static void clearThreadDataSource() {
        dataSourceName.remove();
    }
}
