package com.tianheng.codetool.service;

import com.tianheng.codetool.model.TDbInfo;

import java.util.List;

public interface DbInfoService {

    List<TDbInfo> selectList(String name, Integer typeId);
    int insert(TDbInfo tDbInfo);
    TDbInfo getById(int id);
    int update(TDbInfo tDbInfo);
    int delete(int id);
}
