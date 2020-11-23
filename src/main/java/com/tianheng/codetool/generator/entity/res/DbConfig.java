package com.tianheng.codetool.generator.entity.res;

import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author fjy
 * @since 2020-04-30
 * 数据库配置信息
 */
@Component
public class DbConfig {

    @NotNull(message = "dbId不能为空")
    private Integer dbId;

    /**
     * 数据库类型
     */
//    @NotBlank(message = "dbType数据库类型不能为空" )
    private String dbType;

    private DbType eumDbType;
    /**
     * 驱动
     */
//    @Value("${codetool.db.driver-class-name}")
    @Value("${spring.datasource.driver-class-name}")
//    @NotBlank(message = "driver驱动不能为空" )
    private String driver;
    /**
     * 数据库名
     */
//    @NotBlank(message = "dbName数据库名不能为空" )
//    @Value("${spring.datasource.name}")
    @Value("${spring.datasource.name}")
    private String dbName;
    /**
     * 数据库ip
     */
//    @NotBlank(message = "dbIp数据库ip不能为空" )
    private String dbIp;
    /**
     * 端口
     */
//    @NotBlank(message = "dbPort端口不能为空" )
    private Integer dbPort;
    /**
     * 数据库用户
     */
//    @NotBlank(message = "dbUser数据库用户不能为空" )
//    @Value("${codetool.db.username}")
    @Value("${spring.datasource.username}")
    private String dbUser;
    /**
     * 密码
     */
//    @NotBlank(message = "dbPwd密码不能为空" )
//    @Value("${codetool.db.password}")
    @Value("${spring.datasource.password}")
    private String dbPwd;

    /**
     * 数据库连接地址
     */
//    @Value("${codetool.db.url}")
    @Value("${spring.datasource.url}")
    private String dbUrl;


    public DbConfig(){}

    public DbConfig(@Value("${codetool.db.driver-class-name}") String driver,
                    @Value("${codetool.db.url}") String dbUrl,
                    @Value("${codetool.db.username}") String dbUser,
                    @Value("${codetool.db.password}") String dbPwd) {

        this.driver = driver;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPwd = dbPwd;
    }

    public Integer getDbId() {
        return dbId;
    }

    public DbConfig setDbId(Integer dbId) {
        this.dbId = dbId;
        return this;
    }

    public String getDbType() {
        return dbType;
    }

    public DbConfig setDbType(String dbType) {
        this.dbType = dbType;
        return this;
    }

    public DbType getEumDbType() {
        return eumDbType;
    }

    public DbConfig setEumDbType(DbType eumDbType) {
        this.eumDbType = eumDbType;
        return this;
    }

    public String getDriver() {
        return driver;
    }

    public DbConfig setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public DbConfig setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public String getDbIp() {
        return dbIp;
    }

    public DbConfig setDbIp(String dbIp) {
        this.dbIp = dbIp;
        return this;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public DbConfig setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
        return this;
    }

    public String getDbUser() {
        return dbUser;
    }

    public DbConfig setDbUser(String dbUser) {
        this.dbUser = dbUser;
        return this;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    public DbConfig setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
        return this;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public DbConfig setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
        return this;
    }
}
