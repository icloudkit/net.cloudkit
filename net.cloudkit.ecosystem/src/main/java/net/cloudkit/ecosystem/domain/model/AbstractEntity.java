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
package net.cloudkit.ecosystem.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * AbstractEntity.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 *
 * @param <T>
 */
@MappedSuperclass
public abstract class AbstractEntity<T> implements Entity, Serializable {

    // private static final long serialVersionUID = 1L;

    /** "ID"属性名称 */
    public static final String ID_PROPERTY_NAME = "id";

    // MySQL/SQLServer: @GeneratedValue(strategy = GenerationType.AUTO), Oracle: @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    // Or
    // @SequenceGenerator(name = "SEQ_ACTIVITY_TRACK_LOG", sequenceName = "SEQ_ACTIVITY_TRACK_LOG", allocationSize = 1, initialValue = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACTIVITY_TRACK_LOG")

    @Id
    // @GeneratedValue(strategy = GenerationType.TABLE) | @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable=false, updatable=false, precision = 12)
    private long id;

    /**
     * 获取ID
     *
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!AbstractEntity.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
