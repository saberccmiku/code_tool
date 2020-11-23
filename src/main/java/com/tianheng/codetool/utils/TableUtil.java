package com.tianheng.codetool.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.IDbQuery;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TableUtil {

    public static String getPriKeyByTable(DataSourceConfig dataSourceConfig, String tableName){
        List<TableField> fieldsByTable = TableUtil.getFieldsByTable(dataSourceConfig, dataSourceConfig.getDbQuery(), tableName);
        for (TableField tableField : fieldsByTable) {
            if (tableField.isKeyFlag()) {
                return tableField.getName();
            }
        }
        throw new RuntimeException("cont not found primary key from table of the " + tableName);
    }

    public static List<TableInfo> getTables(DataSourceConfig dataSourceConfig, IDbQuery dbQuery) {

        List<TableInfo> tableList = new ArrayList<>();
        try {
            String tablesSql = dbQuery.tablesSql();
            String schema;
            if (DbType.POSTGRE_SQL == dbQuery.dbType()) {
                schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    schema = "public";
                    dataSourceConfig.setSchemaName(schema);
                }

                tablesSql = String.format(tablesSql, schema);
            }

            if (DbType.DB2 == dbQuery.dbType()) {
                schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    schema = "current schema";
                    dataSourceConfig.setSchemaName(schema);
                }

                tablesSql = String.format(tablesSql, schema);
            } else if (DbType.ORACLE == dbQuery.dbType()) {
                schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    schema = dataSourceConfig.getUsername().toUpperCase();
                    dataSourceConfig.setSchemaName(schema);
                }

                tablesSql = String.format(tablesSql, schema);
            }

            PreparedStatement preparedStatement = dataSourceConfig.getConn().prepareStatement(tablesSql);
            Throwable var11 = null;

            try {
                ResultSet results = preparedStatement.executeQuery();
                Throwable var13 = null;

                try {
                    label578:
                    while (true) {
                        String tableName;
                        TableInfo tableInfo;
                        while (true) {
                            while (true) {
                                if (!results.next()) {
                                    break label578;
                                }
                                tableName = results.getString(dbQuery.tableName());
                                if (StringUtils.isNotEmpty(tableName)) {
                                    tableInfo = new TableInfo();
                                    tableInfo.setName(tableName);
                                    String tableComment = results.getString(dbQuery.tableComment());
                                    tableInfo.setComment(tableComment);
                                    tableList.add(tableInfo);
                                } else {
                                    throw new RuntimeException("current database is null！！！");
                                }
                            }
                        }
                    }
                } catch (Throwable var42) {
                    var13 = var42;
                    throw var42;
                } finally {
                    handleCatch(results, var13);

                }
            } catch (Throwable var44) {
                var11 = var44;
                throw var44;
            } finally {
                handleCatch(preparedStatement, var11);

            }
        } catch (SQLException var46) {
            var46.printStackTrace();
        }

        return tableList;

    }


    public static List<TableField> getFieldsByTable(DataSourceConfig dataSourceConfig, IDbQuery dbQuery, String tableName) {
        boolean haveId = false;
        List<TableField> fieldList = new ArrayList<>();
        DbType dbType = dbQuery.dbType();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            Set<String> h2PkColumns = new HashSet<>();
            PreparedStatement pkQueryStmt;
            Throwable var11;
            ResultSet results;
            Throwable var13;
            if (DbType.POSTGRE_SQL == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.DB2 == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.ORACLE == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql.replace("#schema", dataSourceConfig.getSchemaName()), tableName);
                tableFieldsSql = tableFieldsSql.replace("A.COLUMN_NAME,", "A.COLUMN_NAME,A.NULLABLE,");
            } else if (DbType.DM == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else if (DbType.H2 == dbType) {
                tableName = tableName.toUpperCase();
                pkQueryStmt = dataSourceConfig.getConn().prepareStatement(String.format("select * from INFORMATION_SCHEMA.INDEXES WHERE TABLE_NAME = '%s'", tableName));
                var11 = null;

                try {
                    results = pkQueryStmt.executeQuery();
                    var13 = null;

                    try {
                        while (results.next()) {
                            String primaryKey = results.getString(dbQuery.fieldKey());
                            if (Boolean.valueOf(primaryKey)) {
                                h2PkColumns.add(results.getString(dbQuery.fieldName()));
                            }
                        }
                    } catch (Throwable var97) {
                        var13 = var97;
                        throw var97;
                    } finally {
                        handleCatch(results, var13);

                    }
                } catch (Throwable var99) {
                    var11 = var99;
                    throw var99;
                } finally {
                    handleCatch(pkQueryStmt, var11);

                }

                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            }

            pkQueryStmt = dataSourceConfig.getConn().prepareStatement(tableFieldsSql);
            var11 = null;

            try {
                results = pkQueryStmt.executeQuery();
                var13 = null;

                try {
                    while (results.next()) {
                        TableField field = new TableField();
                        String columnName = results.getString(dbQuery.fieldName());
                        boolean isId;
                        if (DbType.H2 == dbType) {
                            isId = h2PkColumns.contains(columnName);
                        } else {
                            String key = results.getString(dbQuery.fieldKey());
                            if (DbType.DB2 != dbType && DbType.SQLITE != dbType) {
                                isId = StringUtils.isNotEmpty(key) && "PRI".equals(key.toUpperCase());
                            } else {
                                isId = StringUtils.isNotEmpty(key) && "1".equals(key);
                            }
                        }

                        if (isId && !haveId) {
                            field.setKeyFlag(true);
                            if (DbType.H2 == dbType || DbType.SQLITE == dbType || dbQuery.isKeyIdentity(results)) {
                                field.setKeyIdentityFlag(true);
                            }

                            haveId = true;
                        } else {
                            field.setKeyFlag(false);
                        }

                        String[] fcs = dbQuery.fieldCustom();
                        if (null != fcs) {
                            Map<String, Object> customMap = new HashMap<>(fcs.length);
                            for (String fc : fcs) {
                                customMap.put(fc, results.getObject(fc));
                            }

                            field.setCustomMap(customMap);
                        }

                        field.setName(columnName);
                        field.setComment(results.getString(dbQuery.tableComment()));
                        field.setType(results.getString(dbQuery.fieldType()));
                        if (DbType.MYSQL == dbType) {
                            field.setFill(results.getString("Null"));
                        }else if(DbType.ORACLE == dbType){
                            field.setFill(results.getString("NULLABLE"));
                        }
                        fieldList.add(field);
                    }
                } catch (Throwable var93) {
                    var13 = var93;
                    throw var93;
                } finally {
                    handleCatch(results, var13);
                }
            } catch (Throwable var95) {
                var11 = var95;
                throw var95;
            } finally {
                handleCatch(pkQueryStmt, var11);

            }
        } catch (SQLException var101) {
            throw new RuntimeException("SQL Exception：" + var101.getMessage());
        }
        return fieldList;
    }

    private static void handleCatch(Object object, Throwable throwable) throws SQLException {
        if (object != null) {
            if (throwable != null) {
                try {
                    handleInstance(object);
                } catch (Throwable var89) {
                    throwable.addSuppressed(var89);
                }
            } else {
                handleInstance(object);
            }
        }
    }

    private static void handleInstance(Object object) throws SQLException {
        if (object instanceof PreparedStatement) {
            PreparedStatement pkQueryStmt = (PreparedStatement) object;
            pkQueryStmt.close();
        } else if (object instanceof ResultSet) {
            ResultSet results = (ResultSet) object;
            results.close();
        }
    }

}
