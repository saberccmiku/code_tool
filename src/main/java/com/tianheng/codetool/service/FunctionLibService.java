package com.tianheng.codetool.service;

import com.tianheng.codetool.model.TFunctionLib;

import java.util.List;

public interface FunctionLibService {

    List<TFunctionLib> selectList(String name, String type);

    int insert(TFunctionLib tFunctionLib);

    TFunctionLib getById(int id);

    int update(TFunctionLib tFunctionLib);

    int delete(int id);
}
