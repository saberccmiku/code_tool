package com.tianheng.codetool.config;

import java.io.Serializable;

public class ResponseModel implements Serializable {


    protected int code = 200;
    protected String msg = "操作成功";
    protected Object data;
    protected Object page;
    protected Object list;


    public ResponseModel() {
        super();
    }

    public ResponseModel(Object data) {
        super();
        this.data = data;
    }

    public ResponseModel(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }
}
