package com.tianheng.codetool.generator.entity;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;

public class ITemplateConfig extends TemplateConfig {

    private String requestEntity = "/templates/request.java";


    public String getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(String requestEntity) {
        this.requestEntity = requestEntity;
    }
}
