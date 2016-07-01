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
package net.cloudkit.enterprises.domain.shared;

/**
 * 数据状态 DataStatus.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 */
public enum DataStatus implements ValueObject<DataStatus> {

    /** 可用的 */
    ENABLED("可用的", 1),

    /** 禁用的 */
    DISABLED("禁用的", 0),

    /** 已删除 */
    DELETED("已删除", -1);

    // 成员变量
    private String name;
    private int value;

    // 构造方法
    private DataStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static DataStatus get(int value) {
        for (DataStatus dataStatus : values()) {
            if (dataStatus.value == value) {
                return dataStatus;
            }
        }
        return null;
    }

    @Override
    public boolean sameValueAs(DataStatus other) {
        return other != null && this.value == other.value;
    }

    // 覆盖方法
    @Override
    public String toString() {
        return this.value + "_" + this.name;
    }
}
