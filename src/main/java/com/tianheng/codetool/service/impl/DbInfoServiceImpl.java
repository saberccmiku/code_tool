package com.tianheng.codetool.service.impl;

import com.tianheng.codetool.dao.TDbInfoMapper;
import com.tianheng.codetool.model.TDbInfo;
import com.tianheng.codetool.service.DbInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DbInfoServiceImpl implements DbInfoService {

    @Resource
    private TDbInfoMapper tDbInfoMapper;

    @Override
    public List<TDbInfo> selectList(String name, Integer typeId) {
        return tDbInfoMapper.selectList(name, typeId);
    }

    @Override
    public int insert(TDbInfo tDbInfo) {
        return tDbInfoMapper.insert(tDbInfo);
    }

    @Override
    public TDbInfo getById(int id) {
        return tDbInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TDbInfo tDbInfo) {
        return tDbInfoMapper.updateByPrimaryKey(tDbInfo);
    }

    @Override
    public int delete(int id) {
        return tDbInfoMapper.deleteByPrimaryKey(id);
    }
}
