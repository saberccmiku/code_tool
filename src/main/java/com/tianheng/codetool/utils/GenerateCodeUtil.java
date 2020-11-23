package com.tianheng.codetool.utils;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.tianheng.codetool.generator.entity.IPackageConfig;
import com.tianheng.codetool.generator.entity.ITemplateConfig;
import com.tianheng.codetool.generator.entity.res.GenerateConfig;
import com.tianheng.codetool.generator.handle.IAutoGenerator;
import com.tianhengyun.common.tang4jbase.utils.ReflectUtil;
import com.tianhengyun.common.tang4jbase.utils.UnderscoreUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GenerateCodeUtil {

    /**
     * 根据数据库表名自动生成 bean mapper service impl文件的工具类
     *
     * @param generateConfig 配置参数类
     */
    public static void generateByTables(GenerateConfig generateConfig) {
        //设置配置信息
        GlobalConfig config = new GlobalConfig();//全局配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();//数据源配置
        StrategyConfig strategyConfig = new StrategyConfig();//策略配置
        IPackageConfig packageConfig = new IPackageConfig();//包配置
        ITemplateConfig templateConfig = new ITemplateConfig();//模板配置
        //数据源配置
        dataSourceConfig.setDbType(generateConfig.getDbConfig().getEumDbType())
                .setUrl(generateConfig.getDbConfig().getDbUrl())
                .setUsername(generateConfig.getDbConfig().getDbUser())
                .setPassword(generateConfig.getDbConfig().getDbPwd())
                .setDriverName(generateConfig.getDbConfig().getDriver());

        //策略配置
        //获取父类字段 父类字段在子类实体化时忽略
        @SuppressWarnings("unchecked" )
        Field[] allDeclaredFields = ReflectUtil.getAllDeclaredFields(generateConfig.getSuperClass().getSuperEntityClass());
        String[] superEntityColumns = new String[allDeclaredFields.length];
        for (int i = 0; i < allDeclaredFields.length; i++) {
            superEntityColumns[i] = UnderscoreUtil.underscoreName(allDeclaredFields[i].getName()).toLowerCase();
        }
        strategyConfig
                .setCapitalMode(true)//驼峰命名
                .setSuperEntityClass(generateConfig.getSuperClass().getSuperEntityClass().getName())
                .setSuperMapperClass(generateConfig.getSuperClass().getSuperMapperClass().getName())
                .setSuperServiceClass(generateConfig.getSuperClass().getSuperServiceClass().getName())
                .setSuperServiceImplClass(generateConfig.getSuperClass().getSuperServiceImplClass().getName())
                .setSuperControllerClass(generateConfig.getSuperClass().getSuperControllerClass().getName())
                .setEntityTableFieldAnnotationEnable(true)
                .setEntityLombokModel(false)//【实体】是否为LomBok模型（默认 false）
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setColumnNaming(NamingStrategy.underline_to_camel)//驼峰命名
                .setRestControllerStyle(true)//设置RestController注解
                //自定义实体，公共字段（设置了这个会影响xml字段生成完整，所以使用injectionConfig）
//                .setSuperEntityColumns(superEntityColumns)//
                .setInclude(generateConfig.getTableNameArr());//修改替换成你需要的表名，多个表名传数组

        //全局配置
        config.setActiveRecord(false)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setDateType(DateType.ONLY_DATE)//日期类型的字段使用哪个类型，默认是 java8的 日期类型，此处改为 java.util.date
                .setSwagger2(true)
                .setIdType(IdType.AUTO)
                .setServiceName("%sService" )
                .setAuthor(generateConfig.getAuthor())//开发者
                .setOutputDir(generateConfig.getPackageConfig().getOutputDir())
                .setFileOverride(true);//是否覆盖已有文件 默认false
        //包配置
        packageConfig
                .setRequestEntity(generateConfig.getPackageConfig().getRequestEntity())
                .setParent(generateConfig.getPackageConfig().getPackageName())
                .setMapper(generateConfig.getPackageConfig().getMapper())
                .setService(generateConfig.getPackageConfig().getService())
                .setController(generateConfig.getPackageConfig().getController())
                .setXml(generateConfig.getPackageConfig().getXml())//如果不设置setXml，xml会默认生成在mapper下的xml文件里面
                .setEntity(generateConfig.getPackageConfig().getEntity());

        //模板配置
        templateConfig.setXml("templates/mapper.xml.vm" );
        templateConfig.setEntity("templates/entity.java.vm" );
        templateConfig.setController("templates/controller.java.vm" );
        templateConfig.setRequestEntity("templates/request.java.vm" );

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                //自定义配置，在模版中cfg.superColumns 获取
                // TODO 这里解决子类会生成父类属性的问题，在模版里会用到该配置
                map.put("superColumns", superEntityColumns);
                //字段校验规则,，在模版中cfg.validRules 获取
                // TODO 这里解决字段校验规则，在模版里会用到该配置
                map.put("generateConfig", generateConfig);
                this.setMap(map);
            }
        };
        new IAutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setCfg(injectionConfig)
                .setTemplate(templateConfig).execute();
    }


}
