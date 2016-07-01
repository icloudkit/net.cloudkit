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
package net.cloudkit.enterprises.domain.model.security;

import net.cloudkit.enterprises.domain.shared.AbstractEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Entity - 资源
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月18日 上午11:38:34
 */
@Entity
@Table(name = "RESOURCE", schema = "", catalog = "enterprises")
@XmlRootElement
public class Resource extends AbstractEntity<Resource> {

    private static final long serialVersionUID = 1L;

    /** 资源类型 */
    @Column(name = "TYPE")
    private Integer type;

    /** 资源名称 */
    @Column(name = "NAME")
    private String name;

    /** 资源代码 */
    @Column(name = "CODE")
    private String code;

    /** 资源描述 */
    @Column(name = "DESCRIPTION")
    private String description;

    /** 资源URL */
    @Column(name = "PATH")
    private String path;

    /** 上级资源 */
    @Column(name = "PARENT")
    private String parent;

    /** 下级资源 */
    @Column(name = "CHILDREN")
    private String children;

    /** 哪些角色可以访问 */
    // TODO RESOURCE_ROLE_RELATIONSHIPS

    /** TODO 拥有的操作 PERMISSION->RESOURCE */

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("type:").append(type);
        sb.append(", name:'").append(name).append('\'');
        sb.append(", code:'").append(code).append('\'');
        sb.append(", description:'").append(description).append('\'');
        sb.append(", path:'").append(path).append('\'');
        sb.append(", parent:'").append(parent).append('\'');
        sb.append(", children:'").append(children).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Resource resource = (Resource) o;
        return Objects.equals(type, resource.type) &&
                Objects.equals(name, resource.name) &&
                Objects.equals(code, resource.code) &&
                Objects.equals(description, resource.description) &&
                Objects.equals(path, resource.path) &&
                Objects.equals(parent, resource.parent) &&
                Objects.equals(children, resource.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, name, code, description, path, parent, children);
    }

    @Override
    public boolean sameIdentityAs(Resource other) {
        return other != null && this.code.equals(other.code);
    }
}
