package com.tianheng.codetool.generator.entity.res;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;


@Component
public class PackageConfig {

    /**
     * 输出目录
     */
    @Value("${codetool.outputDir}")
    @NotBlank(message = "outputDir输出目录不能为空")
    private String outputDir;

    /**
     * 生成文件所在包名
     */
    @Value("${codetool.packageName}")
    @NotBlank(message = "packageName生成文件所在包名不能为空")
    private String packageName;

    /**
     * entity层文件目录
     */
    @Value("${codetool.entity}")
    private String entity;

    /**
     * requestEntity层文件目录
     */
    @Value("${codetool.requestEntity}")
    private String requestEntity;
    /**
     * mapper层文件目录
     */
    @Value("${codetool.mapper}")
    private String mapper;
    /**
     * xml层文件目录
     */
    @Value("${codetool.xml}")
    private String xml;
    /**
     * service层文件目录
     */
    @Value("${codetool.service}")
    private String service;
    /**
     * controller层文件目录
     */
    @Value("${codetool.controller}")
    private String controller;

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(String requestEntity) {
        this.requestEntity = requestEntity;
    }
}
