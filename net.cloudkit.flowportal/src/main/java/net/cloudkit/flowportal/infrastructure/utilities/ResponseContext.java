/*
 * Copyright (C) 2015. The CloudKit Open Source Project
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

package net.cloudkit.flowportal.infrastructure.utilities;

import java.io.Serializable;
import java.util.List;

/**
 * ResponseContext.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月21日 下午1:00:12
 */
public class ResponseContext<T> implements Serializable {

    /**
     * 总记录数
     */
    private long totalElements;

    /**
     *当前第几页
     */
    private int currentNumber;

    /**
     *总页数
     */
    private  int totalPages;

    /**
     *当前页面的内容
     */
    private List<T> contentList;

    /**
     *当前页面的记录数
     */
    private int numberOfElements;

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContentList() {
        return contentList;
    }

    public void setContentList(List<T> contentList) {
        this.contentList = contentList;
    }
}
