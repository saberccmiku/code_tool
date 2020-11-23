package com.tianheng.codetool.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.tianheng.codetool.generator.entity.DbProperties;
import com.tianheng.codetool.model.TDbInfo;
import com.tianhengyun.common.tang4jbase.utils.DataUtil;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;

public class DbConnUtils {

    public static JSONArray getTables(String driver, @Valid TDbInfo dbInfo) {

        JSONArray arr = new JSONArray();
        DataSourceConfig dataSourceConfig = initDataSourceConfig(driver, dbInfo);
        List<TableInfo> tableInfoList = TableUtil.getTables(dataSourceConfig, dataSourceConfig.getDbQuery());
        JSONObject table;
        for (TableInfo tableInfo : tableInfoList) {
            table = new JSONObject();
            table.put("tableName", tableInfo.getName());
            table.put("tableComment", tableInfo.getComment());
            arr.add(table);
        }
        return arr;
    }

    public static JSONArray getFieldsByTable(String driver, @Valid TDbInfo dbInfo, String tableName) {

        JSONArray arr = new JSONArray();
        DataSourceConfig dataSourceConfig = initDataSourceConfig(driver, dbInfo);
        List<TableField> fieldsByTable = TableUtil.getFieldsByTable(dataSourceConfig, dataSourceConfig.getDbQuery(), tableName);
        fieldsByTable.forEach(tableField -> {

            String fieldType = tableField.getType();
            int columnLength, decimalPlaces;
            Object[] typeProperties = getTypeProperties(fieldType);
            columnLength = (int) typeProperties[1];
            decimalPlaces = (int) typeProperties[2];
            JSONObject field = new JSONObject();
            field.put("columnName", tableField.getName());
            field.put("dataType", typeProperties[0]);
            field.put("columnLength", columnLength);
            field.put("decimalPlaces", decimalPlaces);
            field.put("columnComment", tableField.getComment());
            field.put("isNullAble", tableField.getFill());
            arr.add(field);
        });
        return arr;
    }

    private static Object[] getTypeProperties(String fieldType) {
        Object[] typeProperties = new Object[3];
        int start = fieldType.indexOf("(");
        int end = fieldType.indexOf(")");
        String size = null;
        if (start != -1 && end != -1) {
            size = fieldType.substring(start + 1, end);
            typeProperties[0] = fieldType.substring(0, start);
        } else {
            typeProperties[0] = fieldType;
        }
        if (!DataUtil.isEmpty(size) && size.contains(",")) {
            String[] _arr = size.split(",");
            typeProperties[1] = Integer.parseInt(_arr[0]);
            typeProperties[2] = Integer.parseInt(_arr[1]);
        } else if (DataUtil.isEmpty(size)) {
            typeProperties[1] = 0;
            typeProperties[2] = 0;
        } else {
            typeProperties[1] = Integer.parseInt(size);
            typeProperties[2] = 0;
        }

        return typeProperties;
    }
    
    public static String getPriKeyByTable(String driver, @Valid TDbInfo dbInfo, String tableName) {
        return TableUtil.getPriKeyByTable(initDataSourceConfig(driver, dbInfo), tableName);
    }

    public static DataSourceConfig initDataSourceConfig(String driver, @Valid TDbInfo dbInfo) {
        DbProperties dbProperties = DbProperties.getDbProperties(dbInfo.getTypeName());
        String dbUrl = MessageFormat.format(dbProperties.getUrl(), dbInfo.getIp(), String.valueOf(dbInfo.getPort()), dbInfo.getName());
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbQuery(dbProperties.getDbQuery()).setDbType(dbProperties.getDbType())
                .setDriverName(driver).setPassword(dbInfo.getPassword()).setUsername(dbInfo.getAccount())
                .setUrl(dbUrl).setSchemaName(dbInfo.getAccount());

        return dataSourceConfig;
    }

}
