package com.tianheng.codetool.generator.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.generator.entity.DbProperties;
import com.tianheng.codetool.generator.entity.Method;
import com.tianheng.codetool.generator.entity.res.*;
import com.tianheng.codetool.generator.service.GenerateService;
import com.tianheng.codetool.model.TDbInfo;
import com.tianheng.codetool.model.TDbType;
import com.tianheng.codetool.model.TInterRecord;
import com.tianheng.codetool.service.DbInfoService;
import com.tianheng.codetool.service.DbTypeService;
import com.tianheng.codetool.service.InterRecordService;
import com.tianheng.codetool.utils.DbConnUtils;
import com.tianheng.codetool.utils.GenerateCodeUtil;
import com.tianhengyun.common.tang4jbase.exception.ValidateException;
import com.tianhengyun.common.tang4jbase.support.Operator;
import com.tianhengyun.common.tang4jbase.support.ValidSymbol;
import com.tianhengyun.common.tang4jbase.utils.DataUtil;
import com.tianhengyun.common.tang4jbase.utils.UnderscoreUtil;
import com.tianhengyun.common.tang4jbase.validate.RegexType;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;


@Service
public class GenerateServiceImpl implements GenerateService {

    @Autowired
    private DbInfoService dbInfoService;
    @Autowired
    private DbTypeService dbTypeService;
    @Autowired
    private InterRecordService interRecordService;
    @Autowired
    private PackageConfig packageConfig;
    @Autowired
    private DbConfig dbConfig;

    private final Logger logger = LoggerFactory.getLogger(GenerateServiceImpl.class);

    /**
     * 用于指定数据库代码配置后生成
     *
     * @param generateConfig 指定数据库代码配置规则
     */
    @Override
    @TargetDataSource(name = "codeToolDB")
    public void create(GenerateConfig generateConfig) throws Exception {
        logger.info("-----解析入参数据start-----");
        //解析数据库
        generateConfig.setPackageConfig(packageConfig);
        DbConfig dbConfig = generateConfig.getDbConfig();
        TDbInfo dbInfo = dbInfoService.getById(dbConfig.getDbId());
        if (DataUtil.isEmpty(dbInfo)) {
            throw new ValidateException("dbId不存在，未找出符合要求的数据库配置id");
        }
        TDbType dbType = dbTypeService.getById(dbInfo.getTypeId());
        if (DataUtil.isEmpty(dbType)) {
            throw new ValidateException("数据库类型不存在，请在对应的数据库表中添加");
        }
        dbConfig.setDbName(dbInfo.getName()).setDbIp(dbInfo.getIp())
                .setDbPort(dbInfo.getPort()).setDbUser(dbInfo.getAccount())
                .setDbPwd(dbInfo.getPassword()).setDriver(dbType.getDriver())
                .setDbType(dbType.getName().toLowerCase());
        dbConfig.setEumDbType(DbType.getDbType(dbConfig.getDbType()));
        //拼接数据源
        DbProperties dbProperties = DbProperties.getDbProperties(dbConfig.getDbType());
        String dbUrl = MessageFormat.format(dbProperties.getUrl(), dbInfo.getIp(), String.valueOf(dbInfo.getPort()), dbInfo.getName());
        dbConfig.setDbUrl(dbUrl);
        //将表名转成数组
        generateConfig.setTableNameArr(new String[]{generateConfig.getTableName()});
        //取接口uri最后一个斜杠后面的名称首字母大写创建入参类
        List<Interfaces> interfaces = generateConfig.getInterfaces();
        interfaces.forEach(item -> {
            int lastIndex = item.getMethod().lastIndexOf("/");
            item.setName(DataUtil.upperCase(item.getMethod().substring(lastIndex + 1)));
            //检查接口方法是否符合规范 并设置请求风格restful
            item.setRestfulAnnotation(Method.getMethod(item.getMethod()).getAnnotation());

        });
        //获取每次接口入参的字段解析其对应的检验注解
        interfaces.forEach(tempInterface -> {
            List<Check> checkList = tempInterface.getCheckList();
            //过滤掉不需要入参校验的接口,不设置interfaces中checkList校验，否则，反之
            String method[] = new String[]{Method.DELETE.getLabel(),
                    Method.DETAIL.getLabel(), Method.LIST.getLabel(),
                    Method.PAGE_List.getLabel(),
                    Method.DELETE_BATCH.getLabel()};
            List<String> methodList = new ArrayList<>(Arrays.asList(method));
            if (!methodList.contains(tempInterface.getMethod()) && CollectionUtils.isEmpty(checkList)) {
                throw new ValidateException(tempInterface.getMethod() + ":" + tempInterface.getUri() + ">----" + "Checklist check field set array in interfaces cannot be empty");
            }
            if (!CollectionUtils.isEmpty(checkList)) {
                checkList.forEach(item -> {

                    //将数据库字段类型转成java类型
                    DataSourceConfig dataSourceConfig = new DataSourceConfig();
                    dataSourceConfig.setDbType(dbConfig.getEumDbType());
                    IColumnType iColumnType = dataSourceConfig.getTypeConvert().
                            processTypeConvert(new GlobalConfig(), item.getFieldType());
                    item.setFieldJavaType(iColumnType.getType());
                    //根据字段类型决定非空注解
                    item.setValidSymbol(ValidSymbol.getValidSymbol(iColumnType.getType()).getSymbol());
                    //将数据库字段类型转成首字母大写
                    item.setCapitalName(DataUtil.upperCase(item.getFieldName()));
                    //根据检验规则=正则表达式决定注解值
                    List<Rule> ruleList = item.getRule();
                    if (!CollectionUtils.isEmpty(ruleList)) {
                        //如果非自定义规则则从自定义库读取符合条件的规则
                        ruleList.forEach(rule -> {
                            if (!rule.getType().equalsIgnoreCase(RegexType.CUSTOM.getType())) {
                                if (ArrayUtils.isEmpty(rule.getNumbers())) {
                                    rule.regexTypeToRule(RegexType.getRegexType(rule.getType()));
                                } else {
                                    rule.regexTypeToRule(RegexType.getRegexType(rule.getType(), rule.getNumbers()));
                                }
                            }
                        });
                    }
                    //修正校验注解  因为数字类型的校验不能使用@Pattern
                    item.setValidAnnotations();
                    if (!DataUtil.isEmpty(item.getOperator())) {
                        //判定字段sql操作符是否符合QueryWrapper规范
                        Operator operator = Operator.getOperator(item.getOperator());
                        //当sql操作符涉及的参数值大于2个时，就将字段类型改成数组，方便模板渲染入参类型
                        if (operator.getParameter() >= 3) {//反射时该操作符对应的参数数量 比实际多1，所以这里是3
                            item.setFieldType(item.getFieldType() + "[]");
                        }
                    }
                });
            } else {
                tempInterface.setCheckList(null);
            }
        });
        logger.info(generateConfig.toString());
        logger.info("-----解析入参数据end-----");
        GenerateCodeUtil.generateByTables(generateConfig);
        //保存接口信息
        saveInter(generateConfig);
    }

    /**
     * 用于指定数据库代码直接生成无需配置
     *
     * @param config 数据
     */
    @Override
    public void lazyCreate(GenerateConfig config) {

        //切换到本项目数据源查询相关数据
        dbConfig.setDbId(config.getDbConfig().getDbId());
        TDbInfo dbInfo = dbInfoService.getById(dbConfig.getDbId());
        if (DataUtil.isEmpty(dbInfo)) {
            throw new ValidateException("dbId不存在，未找出符合要求的数据库配置id");
        }
        TDbType dbType = dbTypeService.getById(dbInfo.getTypeId());
        if (DataUtil.isEmpty(dbType)) {
            throw new ValidateException("数据库类型不存在，请在对应的数据库表中添加");
        }
        dbConfig.setDbName(dbInfo.getName()).setDbIp(dbInfo.getIp())
                .setDbPort(dbInfo.getPort()).setDbUser(dbInfo.getAccount())
                .setDbPwd(dbInfo.getPassword()).setDriver(dbType.getDriver())
                .setDbType(dbType.getName().toLowerCase());
        dbConfig.setEumDbType(DbType.getDbType(dbConfig.getDbType()));
        //拼接数据源
        String dbUrl = "jdbc:" + dbConfig.getDbType() + "://" + dbConfig.getDbIp() +
                ":" + dbConfig.getDbPort() + "/" + dbConfig.getDbName() + "?useUnicode=true&characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false";
        dbConfig.setDbUrl(dbUrl);
        //将表名转成数组
        config.setTableNameArr(new String[]{config.getTableName()});
        config.setDbConfig(dbConfig);
        config.setPackageConfig(packageConfig);
        config.setAuthor(config.getAuthor());
        GenerateCodeUtil.generateByTables(config);
        //保存接口信息
//        saveInter(config);

    }


    /**
     * 用于本项目数据库代码直接生成
     *
     * @param tableNames 表名数组
     */
    @Override
    public void localLazyCreate(String[] tableNames) {
        GenerateConfig config = new GenerateConfig();
        String dbUrl = dbConfig.getDbUrl();
        Integer startIndex = dbUrl.indexOf(":");
        Integer endIndex = dbUrl.substring(startIndex + 1).indexOf(":");
        DbType dbType = DbType.valueOf(dbUrl.substring(startIndex + 1, startIndex + endIndex + 1).toUpperCase());
        dbConfig.setEumDbType(dbType);
        config.setDbConfig(dbConfig);
        config.setTableNameArr(tableNames);
        config.setPackageConfig(packageConfig);
        GenerateCodeUtil.generateByTables(config);

    }

    private void saveInter(GenerateConfig generateConfig) throws SQLException {
        List<Interfaces> interfaces = generateConfig.getInterfaces();
        TDbInfo dbInfo = new TDbInfo();
        dbInfo.setTypeName(generateConfig.getDbConfig().getDbType());
        dbInfo.setName(generateConfig.getDbConfig().getDbName());
        dbInfo.setIp(generateConfig.getDbConfig().getDbIp());
        dbInfo.setAccount(generateConfig.getDbConfig().getDbUser());
        dbInfo.setPassword(generateConfig.getDbConfig().getDbPwd());
        dbInfo.setPort(generateConfig.getDbConfig().getDbPort());
        StringBuilder sb = new StringBuilder();
        TInterRecord interRecord;
        String[] restful = new String[]{Method.DELETE.getLabel(), Method.DETAIL.getLabel()};
        interRecord = new TInterRecord();
        interRecord.setDbId(generateConfig.getDbConfig().getDbId());
        interRecord.setTableName(generateConfig.getTableName());
        Map<String, Object> interMap;
        List<Map<String, Object>> interList = new ArrayList<>();
        for (Interfaces item : interfaces) {
            sb.delete(0, sb.length());
            sb.append("/").append(UnderscoreUtil.
                    camelCaseName(generateConfig.getTableName())).append("/").append(item.getMethod());
            if (Arrays.asList(restful).contains(item.getMethod())) {
                //获取表主键
                sb.append("/{").append(UnderscoreUtil.camelCaseName(
                        DbConnUtils.getPriKeyByTable(generateConfig.getDbConfig().getDriver(),
                                dbInfo, generateConfig.getTableName()))).append("}");

            }
            interMap = new HashMap<>();
            interMap.put("interName", item.getDesc());
            interMap.put("interUri", sb.toString());
            interList.add(interMap);
        }
        interRecord.setAddUser(generateConfig.getAuthor());
        interRecord.setAddTime(new Date());
        interRecord.setSvnSubmit("未提交");
        //文件配置json
        interRecord.setPackageConfig(JSONObject.toJSONString(generateConfig.getPackageConfig()));
        //接口配置json
        interRecord.setInterConfig(JSONObject.toJSONString(interList));
        //切换到本项目数据库
        if (!interRecordService.saveOrUpdate(interRecord)) {
            throw new SQLException("save interRecord error");
        }
    }

}
