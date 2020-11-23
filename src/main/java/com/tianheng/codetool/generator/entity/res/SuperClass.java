package com.tianheng.codetool.generator.entity.res;

import com.tianhengyun.common.tang4jbase.abstracts.*;

import javax.validation.constraints.NotNull;

public class SuperClass {

    /**
     * model父类
     */
    @NotNull(message = "superEntityClass不能空" )
    private Class superEntityClass = AbstractModel.class;
    /**
     * mapper父类
     */
    @NotNull(message = "superMapperClass不能空" )
    private Class superMapperClass = AbstractMapper.class;
    /**
     * service父类
     */
    @NotNull(message = "superServiceClass不能空" )
    private Class superServiceClass = AbstractService.class;
    /**
     * serviceImpl父类
     */
    @NotNull(message = "superServiceImplClass不能空" )
    private Class superServiceImplClass = AbstractServiceImpl.class;

    /**
     * controller父类
     */
    @NotNull(message = "superControllerClass不能空" )
    private Class superControllerClass = AbstractController.class;


    public Class getSuperEntityClass() {
        return superEntityClass;
    }

    public void setSuperEntityClass(Class superEntityClass) {
        this.superEntityClass = superEntityClass;
    }

    public Class getSuperMapperClass() {
        return superMapperClass;
    }

    public void setSuperMapperClass(Class superMapperClass) {
        this.superMapperClass = superMapperClass;
    }

    public Class getSuperServiceClass() {
        return superServiceClass;
    }

    public void setSuperServiceClass(Class superServiceClass) {
        this.superServiceClass = superServiceClass;
    }

    public Class getSuperServiceImplClass() {
        return superServiceImplClass;
    }

    public void setSuperServiceImplClass(Class superServiceImplClass) {
        this.superServiceImplClass = superServiceImplClass;
    }

    public Class getSuperControllerClass() {
        return superControllerClass;
    }

    public void setSuperControllerClass(Class superControllerClass) {
        this.superControllerClass = superControllerClass;
    }
}
