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
package net.cloudkit.enterprises.infrastructure.shiro;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * ChainDefinitionSectionMetaSource.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月30日 上午11:55:34
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Section> {

    private static Logger logger = LoggerFactory.getLogger(ChainDefinitionSectionMetaSource.class);

    private String filterChainDefinitions;

    /** 默认premission字符串 */
    public static final String PERMISSION_STRING = "perms[\"{0}\"]";

    @Override
    public Section getObject() throws Exception {

        // TODO 获取系统所有资源列表 Resource

        Ini ini = new Ini();
        // 加载默认的url
        ini.load(filterChainDefinitions);
        logger.info("设置系统配置的默认权限规则:" + filterChainDefinitions);
        Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

        // // 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,里面的键就是链接URL,值就是存在什么条件才能访问该链接
        // for (Iterator it = list.iterator(); it.hasNext();) {
        //     Resource resource = it.next();
        //     // 如果不为空值添加到section中
        //     if (StringUtils.isNotEmpty(resource.getValue()) && StringUtils.isNotEmpty(resource.getPermission())) {
        //         section.put(resource.getValue(), MessageFormat.format(PERMISSION_STRING, resource.getPermission()));
        //     }
        //
        // }

        // 循环Permission的url,逐个添加到section中。section就是filterChainDefinitionMap,
        // 里面的键就是链接URL,值就是存在什么条件才能访问该链接
        logger.info("设置权限数据库中当前系统的所有权限规则.");
        // TODO 设置权限数据库中当前系统的所有权限规则
        /*
        for (Iterator<ResourceDTO> it = resourceList.iterator(); it.hasNext(); ) {
            ResourceDTO resource = it.next();
            // 如果不为空值添加到section中
            if (StringUtils.isNotEmpty(resource.getPath()) && StringUtils.isNotEmpty(resource.getName())) {
                section.put(resource.getPath(), MessageFormat.format(PERMISSION_STRING, resource.getCode()));
                logger.info(resource.getPath() + ":" + MessageFormat.format(PERMISSION_STRING, resource.getCode()));
            }
        }
        */
        return section;
    }

    /**
     * 通过filterChainDefinitions对默认的url过滤定义
     *
     * @param filterChainDefinitions 默认的url过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
