/*
 * Copyright (C) 2015 The CloudKit Open Source Project
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
package net.cloudkit.enterprises.infrastructure.utilities;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * SpringWiredBean.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 */
public class SpringBeanAutowiringHelper extends SpringBeanAutowiringSupport {

    /**
     * 自动装配注解会让Spring通过类型匹配为beanFactory注入示例
     */
    @Autowired
    private BeanFactory beanFactory;

    private SpringBeanAutowiringHelper() {
    }

    private static SpringBeanAutowiringHelper instance;

    static {
        // 静态块，初始化实例
        instance = new SpringBeanAutowiringHelper();
    }

    /**
     * 实例方法
     * 使用的时候先通过getInstance方法获取实例
     *
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    /**
     * 实例方法
     * 使用的时候先通过getInstance方法获取实例
     *
     * @param aClass
     * @return
     */
    public Object getBean(Class<?> aClass) {
        return beanFactory.getBean(aClass);
    }

    public static SpringBeanAutowiringHelper getInstance() {
        return instance;
    }
}
