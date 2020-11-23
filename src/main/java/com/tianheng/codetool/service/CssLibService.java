package com.tianheng.codetool.service;

import com.tianheng.codetool.model.TCssLib;

import java.util.List;

public interface CssLibService {

    List<TCssLib> selectList(String name);

    int insert(TCssLib tCssLib);

    TCssLib getById(int id);

    int update(TCssLib tCssLib);

    int delete(int id);
}
