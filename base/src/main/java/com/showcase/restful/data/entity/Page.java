package com.showcase.restful.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description: 分页查询公共对象
 */

public class Page<T> implements Serializable {

    //------------------公共变量----------------//
    public static final String ASC = "asc";

    public static final String DESC = "desc";


    //------------------查询输入参数----------------//
    //排序字段
    private String orderBy;

    //排序方向
    private String order;

    //----- 分页参数 ----//
    //页码
    private int pageNo = 1;
    //每页记录数  默认为-1(不分页)
    private int pageSize = -1;


    //-----------以下是查询后需要封装的返回参数-----------//

    //总记录数
    private long totalCount;

    //起始页
    private long startIndex;

    //结束页
    private long lastIndex;

    //返回当前页结果
    private List<T> currentPageResult = new ArrayList<T>();


    //默认构造函数
    public Page() {
    }

    //分页参数访问函数
    public Page(int pageNo, int pageSize) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public Page(List<T> currentPageResult, long totalCount) {
        this.currentPageResult = currentPageResult;
        this.totalCount = totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public long getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(long lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<T> getCurrentPageResult() {
        return currentPageResult;
    }

    public void setCurrentPageResult(List<T> currentPageResult) {
        this.currentPageResult = currentPageResult;
    }
}