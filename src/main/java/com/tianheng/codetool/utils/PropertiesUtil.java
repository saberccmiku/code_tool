package com.tianheng.codetool.utils;

import org.springframework.core.env.Environment;

/**
 * 在普通的util类中需要读取application.properties中自定义的一些属性，
 * 在spring boot的bean类中我们知道可以用@Value注解来获取，
 * 但这些普通类怎么获取呢？在网上查了一番资料发现，
 * 通过spring boot的Environment接口可以获取这些属性，
 */
public class PropertiesUtil {

    private static Environment env = null;

    public static void setEnvironment(Environment env) {
        PropertiesUtil.env = env;
    }

    public static String getProperty(String key) {
        return PropertiesUtil.env.getProperty(key);
    }


}
