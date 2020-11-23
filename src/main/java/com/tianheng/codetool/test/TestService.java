package com.tianheng.codetool.test;

import com.tianhengyun.common.tang4jbase.abstracts.AbstractService;
import com.tianhengyun.common.tang4jbase.multiple.Multiple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestService extends AbstractService<ResTest> {
    List<ResTest> multipleSelect(@Param("multiples") Multiple... multiples);
}
