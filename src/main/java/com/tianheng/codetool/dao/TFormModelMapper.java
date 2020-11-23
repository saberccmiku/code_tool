package com.tianheng.codetool.dao;

import com.tianheng.codetool.model.TFormModel;

public interface TFormModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TFormModel record);

    int insertSelective(TFormModel record);

    TFormModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TFormModel record);

    int updateByPrimaryKeyWithBLOBs(TFormModel record);

    int updateByPrimaryKey(TFormModel record);
}