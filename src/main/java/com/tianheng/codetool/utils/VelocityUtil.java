package com.tianheng.codetool.utils;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.tianheng.codetool.generator.entity.IPackageConfig;
import com.tianheng.codetool.generator.entity.ITemplateConfig;
import com.tianheng.codetool.generator.entity.res.Check;
import com.tianheng.codetool.generator.entity.res.GenerateConfig;
import com.tianheng.codetool.generator.entity.res.Interfaces;
import com.tianheng.codetool.generator.handle.IAutoGenerator;
import com.tianhengyun.common.tang4jbase.utils.DataUtil;
import com.tianhengyun.common.tang4jbase.utils.UnderscoreUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VelocityUtil {

    private static final Logger logger = LoggerFactory.getLogger(VelocityUtil.class);

    public static void create(IAutoGenerator generator) throws IOException {
        InjectionConfig cfg = generator.getCfg();
        GlobalConfig globalConfig = generator.getGlobalConfig();
        ITemplateConfig templateConfig = (ITemplateConfig) generator.getTemplate();
        GenerateConfig generateConfig = (GenerateConfig) cfg.getMap().get("generateConfig");
        IPackageConfig packageConfig = (IPackageConfig) generator.getPackageInfo();
        String packageName = packageConfig.getParent();//基础类包
        String beanPackageName = packageConfig.getRequestEntity();//当前类包
        String completePackageName = packageName + "." + beanPackageName;//包
        String tableName = generateConfig.getTableName();

        ClassPathResource classPathResource = new ClassPathResource("");
        String sourcePath = classPathResource.getFile().getAbsolutePath();
        String outputDirPath = globalConfig.getOutputDir();
        if (!outputDirPath.endsWith("\\")) {
            outputDirPath += "\\";
        }
        String targetPath = outputDirPath
                + (packageName + "." + beanPackageName).replace(".", "\\");

        List<Interfaces> interfaces = generateConfig.getInterfaces();
        for (Interfaces anInterface : interfaces) {
            List<Check> checkList = anInterface.getCheckList();
            List<String> importPackages = new ArrayList<>();
            if (!CollectionUtils.isEmpty(checkList)) {

                for (Check check : checkList) {//用来确定实体类成员变量字段名称及其java类型所需的导包路径
                    IColumnType iColumnType = generator.getDataSource().getTypeConvert().processTypeConvert(globalConfig, check.getFieldType());
                    check.setFieldJavaType(iColumnType.getType());
                    if (null != iColumnType.getPkg() && !importPackages.contains(iColumnType.getPkg())) {
                        importPackages.add(iColumnType.getPkg());
                    }
                }
                anInterface.setImportPackages(importPackages);
                //类名
                String className = "Request" + DataUtil.upperCase(UnderscoreUtil.camelCaseName(tableName.toLowerCase())) + DataUtil.upperCase(anInterface.getMethod());
                //类文件名
                String targetFile = className + ".java";


                Properties pro = new Properties();
                pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, sourcePath);
                VelocityEngine ve = new VelocityEngine(pro);

                VelocityContext context = new VelocityContext();
                context.put("className", className);
                context.put("packageName",completePackageName );
                context.put("interface", anInterface);
                Template t = ve.getTemplate(templateConfig.getRequestEntity(), "UTF-8");
                File file = new File(targetPath, targetFile);
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                if (!file.exists())
                    file.createNewFile();

                FileOutputStream outStream = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(outStream,
                        "UTF-8");
                BufferedWriter sw = new BufferedWriter(writer);
                t.merge(context, sw);
                sw.flush();
                sw.close();
                outStream.close();
                logger.info("成功生成Java文件:"
                        + (targetPath + targetFile).replaceAll("/", "\\\\"));
            }
        }

    }
}
