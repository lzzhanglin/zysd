package com.zysd.test.entity;


import java.io.Serializable;

public class ReturnPage<T> implements Serializable {


    private Integer page;

    private Integer total;

    private T rows;

    public ReturnPage() {
    }

    public ReturnPage(Integer page, Integer total, T rows) {
        this.page = page;
        this.total = total;
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }
}
