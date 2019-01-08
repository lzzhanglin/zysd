package com.zysd.test.entity;

/**
 * @author lzzhanglin
 * @date 2019/1/7 20:16
 */
public class Resp<T> {

    private String status;

    private String msg;

    private T data;

    public Resp(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Resp(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
