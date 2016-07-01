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

import net.cloudkit.enterprises.domain.model.member.LoginStatus;
import net.cloudkit.enterprises.domain.shared.AbstractEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * LoginLog.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 */
@Entity
@Table(name = "LOGIN_LOG", schema = "", catalog = "enterprises")
public class LoginLog extends AbstractEntity<LoginLog> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "IP")
    private String ip;

    @Basic
    @Column(name = "LOCAL")
    private String local;

    @Basic
    @Column(name = "LOGIN_NAME")
    private String loginName;

    @Basic
    @Column(name = "LOGIN_STATUS")
    private LoginStatus loginStatus;

    @Basic
    @Column(name = "LOGIN_TIME")
    private Timestamp loginTime;

    @Basic
    @Column(name = "PORT")
    private String port;

    @Basic
    @Column(name = "USER_AGENT")
    private String userAgent;

    public LoginLog() {

    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return the loginStatus
     */
    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    /**
     * @param loginStatus the loginStatus to set
     */
    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    /**
     * @return the ip
     */
    public Timestamp getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime the loginTime to set
     */
    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("ip:'").append(ip).append('\'');
        sb.append(", local:'").append(local).append('\'');
        sb.append(", loginName:'").append(loginName).append('\'');
        sb.append(", loginStatus:").append(loginStatus);
        sb.append(", loginTime:").append(loginTime);
        sb.append(", port:'").append(port).append('\'');
        sb.append(", userAgent:'").append(userAgent).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LoginLog loginLog = (LoginLog) o;
        return Objects.equals(ip, loginLog.ip) &&
                Objects.equals(local, loginLog.local) &&
                Objects.equals(loginName, loginLog.loginName) &&
                Objects.equals(loginStatus, loginLog.loginStatus) &&
                Objects.equals(loginTime, loginLog.loginTime) &&
                Objects.equals(port, loginLog.port) &&
                Objects.equals(userAgent, loginLog.userAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ip, local, loginName, loginStatus, loginTime, port, userAgent);
    }

    @Override
    public boolean sameIdentityAs(LoginLog other) {
        return other != null && this.getId().equals(other.getId());
    }
}
