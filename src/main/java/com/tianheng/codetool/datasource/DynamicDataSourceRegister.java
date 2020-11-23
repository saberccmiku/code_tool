package com.tianheng.codetool.datasource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源注册<br/>
 * 启动动态数据源请在启动类中 添加 @Import(DynamicDataSourceRegister.class)
 */
public class DynamicDataSourceRegister
        implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    // 如配置文件中未指定数据源类型，使用该默认值
    private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    // 数据源
    private DataSource defaultDataSource;
    private Map<String, DataSource> customDataSources = new HashMap<>();

    /**
     * 加载多数据源配置
     */
    @Override
    public void setEnvironment(Environment env) {
        initDefaultDataSource(env);
        initCustomDataSources(env);
    }

    /**
     * 1.5.8 初始化主数据源
     */
//    private void initDefaultDataSource(Environment env) {
//        // 读取主数据源
//        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, DB_DEFAULT_VALUE+".");
//        Map<String, Object> dsMap = new HashMap<>();
//        dsMap.put("type", propertyResolver.getProperty("type"));
//        dsMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
//        dsMap.put("url", propertyResolver.getProperty("url"));
//        dsMap.put("username", propertyResolver.getProperty("username"));
//        dsMap.put("password", propertyResolver.getProperty("password"));
//
//        defaultDataSource = buildDataSource(dsMap);
//        customDataSources.put(defaultDbname,defaultDataSource);//默认数据源放到动态数据源里
//        dataBinder(defaultDataSource, env);
//    }

    /**
     * 2.0.4 初始化主数据源
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("type", env.getProperty("spring.datasource.type"));
        dsMap.put("driver-class-name", env.getProperty("spring.datasource.driver-class-name"));
        dsMap.put("url", env.getProperty("spring.datasource.url"));
        dsMap.put("username", env.getProperty("spring.datasource.username"));
        dsMap.put("password", env.getProperty("spring.datasource.password"));
        defaultDataSource = buildDataSource(dsMap);
//        customDataSources.put(defaultDbname,defaultDataSource);//默认数据源放到动态数据源里
//        dataBinder(defaultDataSource, env);
    }


    /**
     * 为DataSource绑定更多数据
     */
//    private void dataBinder(DataSource dataSource, Environment env) {
//        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
//        //dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
//        dataBinder.setConversionService(conversionService);
//        dataBinder.setIgnoreNestedProperties(false);//false
//        dataBinder.setIgnoreInvalidFields(false);//false
//        dataBinder.setIgnoreUnknownFields(true);//true
//        if (dataSourcePropertyValues == null) {
//            Map<String, Object> rpr = new RelaxedPropertyResolver(env, DB_DEFAULT_VALUE).getSubProperties(".");
//            Map<String, Object> values = new HashMap<String, Object>(rpr);
//            // 排除已经设置的属性
//            values.remove("type");
//            values.remove("driver-class-name");
//            values.remove("url");
//            values.remove("username");
//            values.remove("password");
//            dataSourcePropertyValues = new MutablePropertyValues(values);
//        }
//        dataBinder.bind(dataSourcePropertyValues);
//    }

    // 初始化更多数据源
    private void initCustomDataSources(Environment env) {
        //读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        //RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env,DB_DEFAULT_VALUE+".");
        // 多个数据源
        Map<String, Object> dsMap = new HashMap<>();

        //代码生成器的数据源
        dsMap.put("type", env.getProperty("codetool.db.type"));
        dsMap.put("driver-class-name", env.getProperty("codetool.db.driver-class-name"));
        dsMap.put("url", env.getProperty("codetool.db..url"));
        dsMap.put("username", env.getProperty("codetool.db.username"));
        dsMap.put("password", env.getProperty("codetool.db..password"));

        DataSource ds = buildDataSource(dsMap);
        customDataSources.put("codeToolDB", ds);
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("dataSource", defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);
        DynamicDataSourceContextHolder.dataSourceIds.addAll(customDataSources.keySet());
        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);

        logger.info("Dynamic DataSource Registry");
    }

    // 创建DataSource
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null)
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource

            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}