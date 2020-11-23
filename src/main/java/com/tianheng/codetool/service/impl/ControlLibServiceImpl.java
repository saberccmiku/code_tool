package com.tianheng.codetool.service.impl;

import com.tianheng.codetool.dao.TControlLibMapper;
import com.tianheng.codetool.model.TControlLib;
import com.tianheng.codetool.model.TControlLibWithBLOBs;
import com.tianheng.codetool.service.ControlLibService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ControlLibServiceImpl implements ControlLibService {

    @Resource
    private TControlLibMapper tControlLibMapper;

    @Override
    public List<TControlLib> selectList(String name, String type) {
        return null;
    }

    @Override
    public int insert(TControlLibWithBLOBs record) {
        return tControlLibMapper.insert(record);
    }

    @Override
    public TControlLibWithBLOBs getById(int id) {
        return tControlLibMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TControlLibWithBLOBs record) {
        return tControlLibMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int delete(int id) {
        return tControlLibMapper.deleteByPrimaryKey(id);
    }
}
