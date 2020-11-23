package com.tianheng.codetool.dao;

import com.tianheng.codetool.model.TFunctionLib;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFunctionLibMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TFunctionLib record);

    int insertSelective(TFunctionLib record);

    TFunctionLib selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TFunctionLib record);

    int updateByPrimaryKeyWithBLOBs(TFunctionLib record);

    int updateByPrimaryKey(TFunctionLib record);

    List<TFunctionLib> selectList(@Param("name") String name, @Param("type")String type);
}