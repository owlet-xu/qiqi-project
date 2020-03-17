package com.qiqi.springboot.seed.bz1.contract.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author xuguoyuan
 * @description 分页查询返回数据model
 * @date 2020-03-16 15:17
 */
public class PageInfo<T> {

    private Integer page;

    private Integer size;

    private long totalCount;
    
    private Integer totalPage;

    // 高接搜索条件
    private T conditions;

    // 综合搜索文字
    private String search;

    // 分页的列表数据
    private List<T> contents;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public T getConditions() {
        return conditions;
    }

    public void setConditions(T conditions) {
        this.conditions = conditions;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
