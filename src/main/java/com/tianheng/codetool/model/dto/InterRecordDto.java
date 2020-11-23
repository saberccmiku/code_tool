package com.tianheng.codetool.model.dto;

import com.alibaba.fastjson.JSONArray;
import com.tianheng.codetool.model.TInterRecord;

public class InterRecordDto extends TInterRecord {

    private String dbName;
    private String ip;
    private JSONArray interArr;
    private JSONArray dirTree;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public JSONArray getInterArr() {
        return interArr;
    }

    public void setInterArr(JSONArray interArr) {
        this.interArr = interArr;
    }

    public JSONArray getDirTree() {
        return dirTree;
    }

    public void setDirTree(JSONArray dirTree) {
        this.dirTree = dirTree;
    }
}
