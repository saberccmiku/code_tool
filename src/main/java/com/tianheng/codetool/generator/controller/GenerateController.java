package com.tianheng.codetool.generator.controller;

import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.generator.entity.res.GenerateConfig;
import com.tianheng.codetool.generator.service.GenerateService;
import com.tianhengyun.common.tang4jbase.support.ResponseModel;
import com.tianhengyun.common.tang4jbase.support.ResponseModelFactory;
import com.tianhengyun.common.tang4jbase.utils.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codetool/generate" )
public class GenerateController {

    private final GenerateService generateService;

    @Autowired
    public GenerateController(GenerateService generateService) {
        this.generateService = generateService;
    }

    /**
     * 用于指定数据库代码配置后生成
     *
     * @param generateConfig 指定数据库代码配置规则
     */
    @PostMapping("/create" )
    public ResponseModel create(@Validated @RequestBody GenerateConfig generateConfig, BindingResult bindingResult) {
        try {
            ValidUtil.validData(bindingResult);
            generateService.create(generateConfig);
            return ResponseModelFactory.OK();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }

    }

    /**
     * 用于指定数据库代码直接生成无需配置
     *
     * @param request dbId :数据库id tableNames :表名数组
     */
    @PostMapping("/lazyCreate" )
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel lazyCreate(@Validated @RequestBody GenerateConfig request, BindingResult bindingResult) {
        try {
            ValidUtil.validData(bindingResult);
            generateService.lazyCreate(request);
            return ResponseModelFactory.OK();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    /**
     * 用于本项目数据库代码直接生成
     *
     * @param tableNames 表名数组
     */
    @PostMapping("/localLazyCreate" )
    public ResponseModel localLazyCreate(@RequestBody String[] tableNames) {
        try {
            generateService.localLazyCreate(tableNames);
            return ResponseModelFactory.OK();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }
}
