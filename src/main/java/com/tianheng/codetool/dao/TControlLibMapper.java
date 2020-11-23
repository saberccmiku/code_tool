package com.tianheng.codetool.dao;

import com.tianheng.codetool.model.TControlLib;
import com.tianheng.codetool.model.TControlLibWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TControlLibMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TControlLibWithBLOBs record);

    int insertSelective(TControlLibWithBLOBs record);

    TControlLibWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TControlLibWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TControlLibWithBLOBs record);

    int updateByPrimaryKey(TControlLib record);

    List<TControlLib> selectList(@Param("name") String name, @Param("type")String type);


}