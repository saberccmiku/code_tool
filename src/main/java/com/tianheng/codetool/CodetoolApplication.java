package com.tianheng.codetool;

import com.tianheng.codetool.datasource.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
// 注册动态多数据源
@Import({DynamicDataSourceRegister.class})
@SpringBootApplication
public class CodetoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodetoolApplication.class, args);
    }

}
