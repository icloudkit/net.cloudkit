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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity - 业务状态变化跟踪
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月18日 上午11:38:34
 */
@Entity
@Table(name = "STATUS_ACTIVITY_TRACK")
public class StatusActivityTrack extends AbstractEntity<StatusActivityTrack> {

    private static final long serialVersionUID = 1L;

    /** 跟踪ID */
    @Basic
	@Column(name = "TRACK_ID")
    private String trackId;

    /** 单证KEY */
    @Basic
	@Column(name = "KEYWORD")
    private String keyword;

    /** 操作人 */
    @Basic
	@Column(name = "OPT_USER")
    private String optUser;

    /** 业务状态 */
    @Basic
	@Column(name = "TRACK_STATUS")
    private Integer trackStatus;

    /** 业务状态说明 */
    @Basic
	@Column(name = "TRACK_STATUS_DESC")
    private String trackStatusDesc;

    /** 跟踪（记录）时间 */
    @Basic
	@Column(name = "TRACK_TIME")
    private Long trackTime;

    /** 备注 */
    @Basic
	@Column(name = "TRACK_NOTE")
    private String trackNote;

    /** 业务类型 */
    @Basic
	@Column(name = "TRACK_TYPE")
    private DomainType trackType;

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
     * @return the trackStatus
     */
    public Integer getTrackStatus() {
        return trackStatus;
    }

    /**
     * @param trackStatus the trackStatus to set
     */
    public void setTrackStatus(Integer trackStatus) {
        this.trackStatus = trackStatus;
    }

    /**
     * @return the trackStatusDesc
     */
    public String getTrackStatusDesc() {
        return trackStatusDesc;
    }

    /**
     * @param trackStatusDesc the trackStatusDesc to set
     */
    public void setTrackStatusDesc(String trackStatusDesc) {
        this.trackStatusDesc = trackStatusDesc;
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
     * @return the trackNote
     */
    public String getTrackNote() {
        return trackNote;
    }

    /**
     * @param trackNote the trackNote to set
     */
    public void setTrackNote(String trackNote) {
        this.trackNote = trackNote;
    }

    /**
     * @return the trackType
     */
    public DomainType getTrackType() {
        return trackType;
    }

    /**
     * @param trackType the trackType to set
     */
    public void setTrackType(DomainType trackType) {
        this.trackType = trackType;
    }


    @Override
    public boolean sameIdentityAs(StatusActivityTrack other) {
        return other != null && this.getId().equals(other.getId());
    }
}
