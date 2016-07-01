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
import java.util.Objects;

/**
 * Entity - 权限
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月30日 上午11:53:30
 */
@Entity
@Table(name = "PERMISSION", schema = "", catalog = "enterprises")
public class Permission extends AbstractEntity<Permission> {

    private static final long serialVersionUID = 1L;

    /** 权限名称 */
    @Column(name = "NAME")
    private String name;

    /** 权限代码 */
    @Column(name = "CODE")
    private String code;

    /** 权限描述 */
    @Column(name = "DESCRIPTION")
    private String description;

    /** 所属资源 */
    @Column(name = "RESOURCE")
    private String Resource;

    /** 所属角色 */
    @Column(name = "ROLE")
    private String Role;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("name:'").append(name).append('\'');
        sb.append(", code:'").append(code).append('\'');
        sb.append(", description:'").append(description).append('\'');
        sb.append(", Resource:'").append(Resource).append('\'');
        sb.append(", Role:'").append(Role).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Permission that = (Permission) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(Resource, that.Resource) &&
                Objects.equals(Role, that.Role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, code, description, Resource, Role);
    }

    @Override
    public boolean sameIdentityAs(Permission other) {
        return other != null && this.code.equals(other.code);
    }
}
