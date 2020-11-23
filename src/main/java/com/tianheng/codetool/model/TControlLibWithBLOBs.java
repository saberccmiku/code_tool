package com.tianheng.codetool.model;

public class TControlLibWithBLOBs extends TControlLib {
    private String img;

    private String code;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}