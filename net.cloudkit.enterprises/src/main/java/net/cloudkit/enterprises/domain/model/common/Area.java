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
package net.cloudkit.enterprises.domain.model.common;

import net.cloudkit.enterprises.domain.shared.AbstractEntity;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 行政区划 Area
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年12月30日 下午10:52:05
 */
@Entity
@Table(name = "AREA")
public class Area extends AbstractEntity<Area> implements Comparable<Area> {

    private static final long serialVersionUID = 1L;

    /** 地区代码 * */
    @Basic
	@Column(name = "CODE")
    private Integer code;

    /** 地区名称 * */
    @Basic
	@Column(name = "NAME")
    private String name;

    /** 获取地址拼接信息 * */
    @Basic
	@Column(name = "REGION")
    private String region;

    @Basic
	@Column(name = "ORDER_NO")
    private Integer orderNo;

    /** 上线地区 * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Area parent;

    /** 下级地区 * */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private Set<Area> children = new HashSet<>();

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
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
     * @return the region
     */
    public String getRegion() {
        return this.region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the parent
     */
    public Area getParent() {
        return this.parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Area parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    @OrderBy("order asc")
    public Set<Area> getChildren() {
        return this.children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(Set<Area> children) {
        this.children = children;
    }

    /**
     * @return the orderNo
     */
    public Integer getOrderNo() {
        return this.orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public boolean sameIdentityAs(Area other) {
        return other != null && this.getId().equals(other.getId());
    }

    public int compareTo(Area area) {
        return new CompareToBuilder().append(getOrderNo(), area.getOrderNo()).append(getId(), area.getId()).toComparison();
    }


    @PrePersist
    public void prePersist() {
        Area area = getParent();
        if (area != null) {
            setRegion(area.getRegion() + getName());
            // TODO TreePath
            // setTreePath(area.getTreePath() + area.getId() + ",");
        } else {
            setRegion(getName());
            // TODO TreePath
            // setTreePath(",");
        }
    }

    @PreUpdate
    public void preUpdate() {
        Area area = getParent();
        if (area != null) {
            setRegion(area.getRegion() + getName());
        } else {
            setRegion(getName());
        }
    }

    @PreRemove
    public void preRemove() {

    }

    @Override
    public String toString() {
        return getRegion();
    }
}
