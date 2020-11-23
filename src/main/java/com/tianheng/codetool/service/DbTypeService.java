package com.tianheng.codetool.service;

import com.tianheng.codetool.model.TDbType;

import java.util.List;

public interface DbTypeService {

    List<TDbType> selectList(String name);

    int insert(TDbType tDbType);

    int update(TDbType tDbType);

    TDbType getById(int id);

    int delete(int id);
}
