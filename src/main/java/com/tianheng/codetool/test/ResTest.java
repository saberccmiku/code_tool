package com.tianheng.codetool.test;

import com.baomidou.mybatisplus.annotation.TableField;
import com.tianhengyun.common.tang4jbase.abstracts.AbstractModel;

public class ResTest extends AbstractModel {

    private String id;
    private String name;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String addName;
    @TableField(exist = false)
    private String editName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }
}
