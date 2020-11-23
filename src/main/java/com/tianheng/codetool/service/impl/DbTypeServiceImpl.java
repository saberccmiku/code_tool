package com.tianheng.codetool.service.impl;

import com.tianheng.codetool.dao.TDbTypeMapper;
import com.tianheng.codetool.model.TDbType;
import com.tianheng.codetool.service.DbTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DbTypeServiceImpl implements DbTypeService {

    @Resource
    private TDbTypeMapper tDbTypeMapper;


    @Override
    public List<TDbType> selectList(String name) {
        return tDbTypeMapper.selectList(name);
    }

    @Override
    public int insert(TDbType tDbType) {
        return tDbTypeMapper.insert(tDbType);
    }

    @Override
    public int update(TDbType tDbType) {
        return tDbTypeMapper.updateByPrimaryKey(tDbType);
    }

    @Override
    public TDbType getById(int id) {
        return tDbTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(int id) {
        return tDbTypeMapper.deleteByPrimaryKey(id);
    }
}
