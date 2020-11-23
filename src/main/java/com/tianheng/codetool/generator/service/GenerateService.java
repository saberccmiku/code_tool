package com.tianheng.codetool.generator.service;

import com.tianheng.codetool.generator.entity.res.GenerateConfig;

public interface GenerateService {
    void create(GenerateConfig generator) throws Exception;

    void lazyCreate(GenerateConfig config) throws Exception;

    void localLazyCreate(String[] tableNames) throws Exception;
}
