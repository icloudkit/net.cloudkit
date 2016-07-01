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

import net.cloudkit.enterprises.domain.shared.ValueObject;

/**
 * 数据活动类型 DataActivityType.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 */
public enum DataActivityType implements ValueObject<DataActivityType> {

    /** 可用的 */
    INSERT("新增", 1),

    /** 禁用的 */
    UPDATE("修改", 2),

    /** 已删除 */
    DELETE("删除", 3);

    // 成员变量
    private String name;
    private int value;

    // 构造方法
    private DataActivityType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static DataActivityType get(int value) {
        for (DataActivityType dataActivityType : values()) {
            if (dataActivityType.value == value) {
                return dataActivityType;
            }
        }
        return null;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    @Override
    public boolean sameValueAs(DataActivityType other) {
        return other != null && this.value == other.value;
    }

    // 覆盖方法
    @Override
    public String toString() {
        return this.value + "_" + this.name;
    }
}
