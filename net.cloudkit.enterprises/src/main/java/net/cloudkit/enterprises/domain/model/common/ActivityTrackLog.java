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
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Entity - 活动日志
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 */
@Entity
@Table(name = "ACTIVITY_TRACK_LOG")
public class ActivityTrackLog extends AbstractEntity<ActivityTrackLog> {

    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "ACT_REMARK")
    private String actRemark;

    @Basic
    @Column(name = "ACT_TYPE")
    private String actType;

    @Basic
    @Column(name = "ACT_URL")
    private String actUrl;

    @Basic
    @Column(name = "LINK_TYPE")
    private String linkType;

    @Basic
    @Column(name = "OPT_USER")
    private String optUser;

    @Basic
    @Column(name = "SRC_IP")
    private String srcIp;

    @Basic
    @Column(name = "TRACK_TIME")
    private Timestamp trackTime;

    public ActivityTrackLog() {

    }

    /**
     * @return the actRemark
     */
    public String getActRemark() {
        return actRemark;
    }

    /**
     * @param actRemark actRemark actRemark to set
     */
    public void setActRemark(String actRemark) {
        this.actRemark = actRemark;
    }

    /**
     * @return the actType
     */
    public String getActType() {
        return actType;
    }

    /**
     * @param actType the actType to set
     */
    public void setActType(String actType) {
        this.actType = actType;
    }

    /**
     * @return the actUrl
     */
    public String getActUrl() {
        return actUrl;
    }

    /**
     * @param actUrl the actUrl to set
     */
    public void setActUrl(String actUrl) {
        this.actUrl = actUrl;
    }

    /**
     * @return the linkType
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * @param linkType the linkType to set
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType;
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
     * @return the srcIp
     */
    public String getSrcIp() {
        return srcIp;
    }

    /**
     * @param srcIp the srcIp to set
     */
    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    /**
     * @return the trackTime
     */
    public Timestamp getTrackTime() {
        return trackTime;
    }

    /**
     * @param trackTime the trackTime to set
     */
    public void setTrackTime(Timestamp trackTime) {
        this.trackTime = trackTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActivityTrackLog that = (ActivityTrackLog) o;
        return Objects.equals(actRemark, that.actRemark) &&
                Objects.equals(actType, that.actType) &&
                Objects.equals(actUrl, that.actUrl) &&
                Objects.equals(linkType, that.linkType) &&
                Objects.equals(optUser, that.optUser) &&
                Objects.equals(srcIp, that.srcIp) &&
                Objects.equals(trackTime, that.trackTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), actRemark, actType, actUrl, linkType, optUser, srcIp, trackTime);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("actRemark:'").append(actRemark).append('\'');
        sb.append(", actType:'").append(actType).append('\'');
        sb.append(", actUrl:'").append(actUrl).append('\'');
        sb.append(", linkType:'").append(linkType).append('\'');
        sb.append(", optUser:'").append(optUser).append('\'');
        sb.append(", srcIp:'").append(srcIp).append('\'');
        sb.append(", trackTime:").append(trackTime);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean sameIdentityAs(ActivityTrackLog other) {
        return other != null && this.getId().equals(other.getId());
    }
}
