package com.tianheng.codetool.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tianhengyun.common.tang4jbase.abstracts.AbstractModel;

import java.util.Date;

/**
 * <p>
 * 已生成的接口记录表
 * </p>
 *
 * @author saber
 * @since 2020-05-13
 */
public class TInterRecord extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据库id
     */
    @TableField("db_id" )
    private Integer dbId;

    /**
     * 表
     */
    @TableField("table_name" )
    private String tableName;

    /**
     * 添加人
     */
    @TableField("add_user" )
    private String addUser;

    /**
     * 添加时间
     */
    @TableField("add_time" )
    private Date addTime;

    /**
     * svn提交状态(已提交、未提交)
     */
    @TableField("svn_submit" )
    private String svnSubmit;

    /**
     * 文件存储路径，json数组格式
     */
    @TableField("package_config" )
    private String packageConfig;

    /**
     * 接口描述，json数组
     */
    @TableField("inter_config" )
    private String interConfig;

    /**
     * 项目名
     */
    @TableField("project_name" )
    private String projectName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getSvnSubmit() {
        return svnSubmit;
    }

    public void setSvnSubmit(String svnSubmit) {
        this.svnSubmit = svnSubmit;
    }

    public String getPackageConfig() {
        return packageConfig;
    }

    public void setPackageConfig(String packageConfig) {
        this.packageConfig = packageConfig;
    }

    public String getInterConfig() {
        return interConfig;
    }

    public void setInterConfig(String interConfig) {
        this.interConfig = interConfig;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
