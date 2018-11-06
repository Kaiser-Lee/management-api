package com.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    public void generator() throws Exception{


        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //加载配置文件  D:\workspace\jgh_mall\src\main\resources D:\maven project\tool\src\main\resources D:\maven-workspace\management-api
        File configFile = new File("D:\\maven-workspace\\management-api\\tool\\src\\main\\resources\\generatorCode.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);


    }
    public static void main(String[] args) throws Exception {
        try {
            CodeGenerator generatorSqlmap = new CodeGenerator();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
