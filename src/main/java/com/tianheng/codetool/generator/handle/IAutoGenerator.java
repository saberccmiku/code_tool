package com.tianheng.codetool.generator.handle;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.tianheng.codetool.utils.VelocityUtil;

import java.io.IOException;

public class IAutoGenerator extends AutoGenerator {


    @Override
    public void execute() {
        // entity mapper xml service impl controller生成的地方
        super.execute();
        //其他文件生成的地方
//        try {
//            VelocityUtil.create(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
