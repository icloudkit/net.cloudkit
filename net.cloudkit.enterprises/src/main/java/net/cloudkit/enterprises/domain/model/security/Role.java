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
 * Entity - 角色
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013-5-22 下午2:42:27
 */
@Entity
@Table(name = "ROLE", schema = "", catalog = "enterprises")
public class Role extends AbstractEntity<Role> {

    private static final long serialVersionUID = 1L;

    /** 角色名 */
    @Column(name = "NAME")
    private String name;

    /** 角色名 */
    @Column(name = "CODE")
    private String code;

    /** 角色描述 */
    @Column(name = "DESCRIPTION")
    private String description;

    // @ElementCollection
    // @CollectionTable(name="AUTHORITY")
    // private List<String> authorities;

    /** 可以访问的资源 */
    // TODO RESOURCE_ROLE_RELATIONSHIPS

    /** TODO 可以对访问的资源进行的权限 PERMISSION->ROLE */

    /** TODO 被分配给的用户 */
    // USER_ROLE_RELATIONSHIPS

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
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name) &&
                Objects.equals(code, role.code) &&
                Objects.equals(description, role.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, code, description);
    }

    @Override
    public boolean sameIdentityAs(Role other) {
        return other != null && this.code.equals(other.code);
    }
}
