package com.tianheng.codetool.generator.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.IDbQuery;
import com.baomidou.mybatisplus.generator.config.querys.*;

/**
 * 数据库类型及其jdbc
 * ip:{0}  port:{1}  name:{2}
 */
public enum DbProperties {
    MYSQL(DbType.MYSQL, "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding=utf8&serverTimezone=Hongkong&useSSL=true", new MySqlQuery()),
    MARIADB(DbType.MARIADB, "jdbc:mariadb://{0}:{1}/{2}", new MariadbQuery()),
    ORACLE(DbType.ORACLE, "jdbc:oracle:thin:@{0}:{1}:orcl", new OracleQuery()),
    DB2(DbType.DB2, "jdbc:db2://{0}:{1}/{2}:sslConnection=true;", new DB2Query()),
    H2(DbType.H2, "", new H2Query()),
    HSQL(DbType.HSQL, "", null),
    SQLITE(DbType.SQLITE, "", new SqlServerQuery()),
    POSTGRE_SQL(DbType.POSTGRE_SQL, "jdbc:postgresql://{0}:{1}/{2}", new PostgreSqlQuery()),
    SQL_SERVER2005(DbType.SQL_SERVER2005, "", new SqlServerQuery()),
    SQL_SERVER(DbType.SQL_SERVER, "jdbc:sqlserver://{0}:{1};DatabaseName={2}", new SqlServerQuery()),
    DM(DbType.DM, "", new DMQuery()),
    OTHER(DbType.OTHER, "", null);

    private DbType dbType;
    private String url;
    private IDbQuery dbQuery;

    public static DbProperties getDbProperties(String dbType) {
        DbProperties[] dts = values();
        DbProperties[] var2 = dts;
        int var3 = dts.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            DbProperties dt = var2[var4];
            if (dt.getDbType().getDb().equalsIgnoreCase(dbType)) {
                return dt;
            }
        }

        return OTHER;
    }

    public DbType getDbType() {
        return this.dbType;
    }

    public String getUrl() {
        return this.url;
    }

    public IDbQuery getDbQuery() {
        return this.dbQuery;
    }


    private DbProperties(DbType dbType, String url, IDbQuery dbQuery) {
        this.dbType = dbType;
        this.url = url;
        this.dbQuery = dbQuery;
    }
}
