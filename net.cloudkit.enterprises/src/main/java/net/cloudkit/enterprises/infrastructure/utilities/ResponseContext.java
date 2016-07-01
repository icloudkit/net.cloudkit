package net.cloudkit.enterprises.infrastructure.utilities;

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
