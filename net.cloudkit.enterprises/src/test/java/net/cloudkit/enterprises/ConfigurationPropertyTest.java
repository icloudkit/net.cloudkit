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

package net.cloudkit.enterprises;

import javax.annotation.Resource;

import net.cloudkit.enterprises.infrastructure.configuration.ConfigurationProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JUnit4 + Spring 注解进行单元测试，测试通过Spring注解获得Properties文件的值
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/11/21 17:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class ConfigurationPropertyTest {

    @Resource(name = "configurationProperty")
    private ConfigurationProperty configurationProperty;

    /**
     * 测试Spring注解获取properties文件的值
     */
    @Test
    public void test() {
        System.out.println(configurationProperty.getAuthorName());
        System.out.println(configurationProperty.getProjectInfo());
    }

}
