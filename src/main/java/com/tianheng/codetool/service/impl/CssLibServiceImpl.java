package com.tianheng.codetool.service.impl;

import com.tianheng.codetool.dao.TCssLibMapper;
import com.tianheng.codetool.model.TCssLib;
import com.tianheng.codetool.service.CssLibService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CssLibServiceImpl implements CssLibService {

    @Resource
    private TCssLibMapper tCssLibMapper;


    @Override
    public List<TCssLib> selectList(String name) {
        return tCssLibMapper.selectList(name);
    }

    @Override
    public int insert(TCssLib tCssLib) {
        return tCssLibMapper.insert(tCssLib);
    }

    @Override
    public TCssLib getById(int id) {
        return tCssLibMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TCssLib tCssLib) {
        return tCssLibMapper.updateByPrimaryKeyWithBLOBs(tCssLib);
    }

    @Override
    public int delete(int id) {
        return tCssLibMapper.deleteByPrimaryKey(id);
    }
}
