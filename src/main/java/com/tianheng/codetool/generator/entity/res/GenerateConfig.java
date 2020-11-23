package com.tianheng.codetool.generator.entity.res;

import org.apache.commons.lang3.ArrayUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fjy
 * @since 2020-04-30
 * 代码生成器各项配置前端入参数据
 */
public class GenerateConfig {

    @NotNull(message = "dbConfig数据库相关配置不能为空")
    @Valid
    private DbConfig dbConfig;

    /**
     * 表名
     */
    @NotBlank(message = "tableName表名不能为空")
    private String tableName;

    /**
     * 表名集合
     */
    private String[] tableNameArr;
    /**
     * 作者
     */
    @NotBlank(message = "author作者不能为空")
    private String author = "saber";

    private PackageConfig packageConfig;

    /**
     * 接口
     */
//    @NotEmpty(message = "interfaces接口配置集合类不能为空" )
    private List<@Valid Interfaces> interfaces;


    //@NotNull(message = "superClass不能为空")
    //@Valid
    private SuperClass superClass;

    public GenerateConfig() {
    }

    public String[] getTableNameArr() {
        if (ArrayUtils.isEmpty(this.tableNameArr)) {
            return this.tableNameArr = new String[]{this.tableName};
        }
        return this.tableNameArr;
    }

    public SuperClass getSuperClass() {
        if (this.superClass == null) {
            this.superClass = new SuperClass();
        }
        return superClass;
    }


    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableNameArr(String[] tableNameArr) {
        this.tableNameArr = tableNameArr;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PackageConfig getPackageConfig() {
        return packageConfig;
    }

    public void setPackageConfig(PackageConfig packageConfig) {
        this.packageConfig = packageConfig;
    }

    public List<Interfaces> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Interfaces> interfaces) {
        this.interfaces = interfaces;
    }

    public void setSuperClass(SuperClass superClass) {
        this.superClass = superClass;
    }
}