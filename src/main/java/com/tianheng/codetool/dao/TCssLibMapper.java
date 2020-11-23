package com.tianheng.codetool.dao;

import com.tianheng.codetool.model.TCssLib;

import java.util.List;

public interface TCssLibMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TCssLib record);

    int insertSelective(TCssLib record);

    TCssLib selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCssLib record);

    int updateByPrimaryKeyWithBLOBs(TCssLib record);

    int updateByPrimaryKey(TCssLib record);

    List<TCssLib> selectList(String name);
}