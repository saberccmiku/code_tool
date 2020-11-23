package com.tianheng.codetool.test;

import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianhengyun.common.tang4jbase.multiple.Multiple;
import com.tianhengyun.common.tang4jbase.multiple.MultipleFactory;
import com.tianhengyun.common.tang4jbase.support.ResponseModel;
import com.tianhengyun.common.tang4jbase.support.ResponseModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel test() {
        //测试多表联合查询
//        //一层left join
//        Multiple multiple$1 = new Multiple("t_db_info", "t_db_type", "type_id", "id")
//                .addMasterTableField("id:id", "name:name").addServantTableField("name:typeName");
//        //二层left join
//        Multiple multiple$2 = new Multiple("t_db_info", "ts_test_user", "add_user", "id")
//                .addServantTableField("name:addName");
//        //三层left join 其中重复一张表测试
//        Multiple multiple$3 = new Multiple("t_db_info", "ts_test_user", "edit_user", "id")
//                .addServantTableField("name:editName");
//        List<ResTest> resTests$1 = testService.multipleSelect(multiple$1, multiple$2, multiple$3);
        //以上等价于下面的写法
        Multiple multiple = MultipleFactory
                .joinMultiple("t_db_info", "t_db_type", "type_id", "id")
                .addMasterTableField(ResTest.class).addServantTableField("name:typeName")
                .joinMultiple("t_db_info", "ts_test_user", "add_user", "id")
                .addServantTableField("name:addName")
                .joinMultiple("t_db_info", "ts_test_user", "edit_user", "id")
                .addServantTableField("name:editName");
        List<ResTest> resTests$2 = testService.multipleSelect(multiple.toArray());
        return ResponseModelFactory.OKWithData(resTests$2);
    }
}
