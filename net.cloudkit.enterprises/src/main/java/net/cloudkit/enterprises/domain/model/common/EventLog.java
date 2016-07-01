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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * EventLog.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月18日 上午11:38:34
 */
@Entity
@Table(name = "EVENT_LOG")
public class EventLog extends AbstractEntity<EventLog> {

    private static final long serialVersionUID = 1L;

    /** 获取目标类名 */
    @Column(name = "CLASS_NAME", nullable = false)
    private String className;

    /** 获取目标方法签名 */
    @Column(name = "SIGNATURE", nullable = false)
    private String signature;

    /** 目标方法体参数 */
    // @Basic 默认为即时加载 EAGER
    @Column(name = "PARAMES", nullable = false)
    private String parames;

    public EventLog() {

    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return the parames
     */
    public String getParames() {
        return parames;
    }

    /**
     * @param parames the parames to set
     */
    public void setParames(String parames) {
        this.parames = parames;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("className:'").append(className).append('\'');
        sb.append(", signature:'").append(signature).append('\'');
        sb.append(", parames:'").append(parames).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EventLog eventLog = (EventLog) o;
        return Objects.equals(className, eventLog.className) &&
                Objects.equals(signature, eventLog.signature) &&
                Objects.equals(parames, eventLog.parames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), className, signature, parames);
    }

    @Override
    public boolean sameIdentityAs(EventLog other) {
        return other != null && this.getId().equals(other.getId());
    }
}
