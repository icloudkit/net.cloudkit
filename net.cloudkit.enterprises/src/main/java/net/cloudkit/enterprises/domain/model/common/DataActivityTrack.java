/*
 * Copyright (C) 2015 The Workbench Open Source Project
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

import javax.persistence.*;

/**
 * Entity - 数据变化跟踪
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月18日 上午11:38:34
 */
@Entity
@Table(name = "DATA_ACTIVITY_TRACK")
public class DataActivityTrack extends AbstractEntity<DataActivityTrack> {

    private static final long serialVersionUID = 1L;

    /** 业务类型 */
    @Basic
	@Column(name = "DATA_TYPE")
    private String dataType;

    @Basic
	@Column(name = "DATA_TYPE_DESC")
    private String dataTypeDesc;

    /** 跟踪唯一ID */
    @Basic
	@Column(name = "TRACK_ID")
    private String trackId;

    public String getDataTypeDesc() {
		return dataTypeDesc;
	}

	public void setDataTypeDesc(String dataTypeDesc) {
		this.dataTypeDesc = dataTypeDesc;
	}

	public String getDataActivityTypeDesc() {
		return dataActivityTypeDesc;
	}

	public void setDataActivityTypeDesc(String dataActivityTypeDesc) {
		this.dataActivityTypeDesc = dataActivityTypeDesc;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/** 操作人 */
    @Basic
	@Column(name = "OPT_USER")
    private String optUser;

    /** 跟踪时间 */
    @Basic
	@Column(name = "TRACK_TIME")
    private Long trackTime;

    /** 业务主键 */
    @Basic
	@Column(name = "KEYWORD")
    private String keyword;

    /** 活动操作类型 */
    @Basic
	@Column(name = "DATA_ACTIVITY_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private DataActivityType dataActivityType;

    @Basic
	@Column(name = "DATA_ACTIVITY_TYPE_DESC")
    private String dataActivityTypeDesc;

    /** 表名 **/
    @Basic
	@Column(name = "TABLE_NAME")
    private String tableName;

    /** 数据记录 */
    @Basic
	@Column(name = "DATA_RECORD")
    private String dataRecord;

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the trackId
     */
    public String getTrackId() {
        return trackId;
    }

    /**
     * @param trackId the trackId to set
     */
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    /**
     * @return the optUser
     */
    public String getOptUser() {
        return optUser;
    }

    /**
     * @param optUser the optUser to set
     */
    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    /**
     * @return the trackTime
     */
    public Long getTrackTime() {
        return trackTime;
    }

    /**
     * @param trackTime the trackTime to set
     */
    public void setTrackTime(Long trackTime) {
        this.trackTime = trackTime;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the dataActivityType
     */
    public DataActivityType getDataActivityType() {
        return dataActivityType;
    }

    /**
     * @param dataActivityType the dataActivityType to set
     */
    public void setDataActivityType(DataActivityType dataActivityType) {
        this.dataActivityType = dataActivityType;
    }

    /**
     * @return the dataRecord
     */
    public String getDataRecord() {
        return dataRecord;
    }

    /**
     * @param dataRecord the dataRecord to set
     */
    public void setDataRecord(String dataRecord) {
        this.dataRecord = dataRecord;
    }

    @Override
    public boolean sameIdentityAs(DataActivityTrack other) {
        return other != null && this.trackId.equals(other.trackId);
    }
}
