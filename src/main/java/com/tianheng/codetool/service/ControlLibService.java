package com.tianheng.codetool.service;

import com.tianheng.codetool.model.TControlLib;
import com.tianheng.codetool.model.TControlLibWithBLOBs;

import java.util.List;

public interface ControlLibService {

    List<TControlLib> selectList(String name, String type);

    int insert(TControlLibWithBLOBs record);

    TControlLibWithBLOBs getById(int id);

    int update(TControlLibWithBLOBs record);

    int delete(int id);
}
