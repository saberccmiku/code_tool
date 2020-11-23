package com.tianheng.codetool.generator.entity;

import com.baomidou.mybatisplus.generator.config.PackageConfig;

public class IPackageConfig extends PackageConfig {

    private String requestEntity = "entity.request";


    public String getRequestEntity() {
        return requestEntity;
    }

    public IPackageConfig setRequestEntity(String requestEntity) {
        this.requestEntity = requestEntity;
        return this;
    }
}
