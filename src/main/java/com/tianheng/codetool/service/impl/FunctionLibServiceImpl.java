package com.tianheng.codetool.service.impl;

import com.tianheng.codetool.dao.TFunctionLibMapper;
import com.tianheng.codetool.model.TFunctionLib;
import com.tianheng.codetool.service.FunctionLibService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunctionLibServiceImpl implements FunctionLibService {

    @Resource
    private TFunctionLibMapper tFunctionLibMapper;

    @Override
    public List<TFunctionLib> selectList(String name, String type) {
        return tFunctionLibMapper.selectList(name, type);
    }

    @Override
    public int insert(TFunctionLib tFunctionLib) {
        return tFunctionLibMapper.insert(tFunctionLib);
    }

    @Override
    public TFunctionLib getById(int id) {
        return tFunctionLibMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TFunctionLib tFunctionLib) {
        return tFunctionLibMapper.updateByPrimaryKeyWithBLOBs(tFunctionLib);
    }

    @Override
    public int delete(int id) {
        return tFunctionLibMapper.deleteByPrimaryKey(id);
    }
}
