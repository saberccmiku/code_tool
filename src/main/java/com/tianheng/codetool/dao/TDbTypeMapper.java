package com.tianheng.codetool.dao;

import com.tianheng.codetool.model.TDbType;

import java.util.List;

public interface TDbTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TDbType record);

    int insertSelective(TDbType record);

    TDbType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TDbType record);

    int updateByPrimaryKey(TDbType record);

    List<TDbType> selectList(String name);
}