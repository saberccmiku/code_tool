package com.tianheng.codetool.dao;

import com.tianheng.codetool.model.TDbInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDbInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TDbInfo record);

    int insertSelective(TDbInfo record);

    TDbInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TDbInfo record);

    int updateByPrimaryKey(TDbInfo record);

    List<TDbInfo> selectList(@Param("name") String name, @Param("typeId")Integer typeId);
}