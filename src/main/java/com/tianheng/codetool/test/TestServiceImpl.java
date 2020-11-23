package com.tianheng.codetool.test;

import com.tianheng.codetool.test.mapper.TestMapper;
import com.tianhengyun.common.tang4jbase.abstracts.AbstractServiceImpl;
import com.tianhengyun.common.tang4jbase.multiple.Multiple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl extends AbstractServiceImpl<TestMapper, ResTest> implements TestService {

    @Autowired
    private TestMapper mapper;

    @Override
    public List<ResTest> multipleSelect(Multiple... multiples) {
        return mapper.multipleSelect(multiples);
    }
}
