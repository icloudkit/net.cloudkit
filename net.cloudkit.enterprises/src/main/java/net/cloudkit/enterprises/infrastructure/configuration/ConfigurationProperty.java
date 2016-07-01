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

package net.cloudkit.enterprises.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * config.properties文件映射类
 *
 * 测试Spring注解获取properties文件的值
 * @Resource(name = "configProperty")
 * private ConfigProperty configProperty;
 *
 * System.out.println(configProperty.getAuthorName());
 * System.out.println(configProperty.getProjectInfo());
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/11/21 17:57
 */
@Component("configurationProperty")
public class ConfigurationProperty {

    /**
     * 作者名字
     */
    @Value("#{setting[author_name]}")
    private String authorName;
    /**
     * 项目信息
     */
    @Value("#{setting[project_info]}")
    private String projectInfo;

    /**
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * @return the projectInfo
     */
    public String getProjectInfo() {
        return projectInfo;
    }

    /**
     * @param projectInfo the projectInfo to set
     */
    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

}
