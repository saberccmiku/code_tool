package com.tianheng.codetool.generator.entity.res;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author fjy
 * @since 2020-04-30
 * 代码生成器接口配置类
 */

public class Interfaces {
    /**
     * 接口配置项名称
     */
//    @NotNull(message = "interfaces中name配置项名称不能空")
    private String name;

    /**
     * 接口uri
     */
//    @NotEmpty(message = "interfaces中uri接口uri不能为空")
    private String uri;
    /**
     * 接口注释
     */
    @NotBlank(message = "interfaces中desc接口注释不能为空")
    private String desc;
    /**
     * service层方法
     * {@link com.tianheng.codetool.generator.entity.Method}
     */
    @NotBlank(message = "interfaces中method不能为空")
    private String method;

    /**
     * 请求方式 由method字段类型决定
     */
    private String restfulAnnotation;

    /**
     * 该接口生成实体类所需要的导包
     */
    private List<String> importPackages;
    /**
     * 校验字段
     */
//    @NotEmpty(message = "interfaces中checkList校验字段集数组不能空")
    private List<Check> checkList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRestfulAnnotation() {
        return restfulAnnotation;
    }

    public void setRestfulAnnotation(String restfulAnnotation) {
        this.restfulAnnotation = restfulAnnotation;
    }

    public List<Check> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<Check> checkList) {
        this.checkList = checkList;
    }

    public List<String> getImportPackages() {
        return importPackages;
    }

    public void setImportPackages(List<String> importPackages) {
        this.importPackages = importPackages;
    }
}
