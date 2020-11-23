package com.tianheng.codetool.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.model.TInterRecord;
import com.tianheng.codetool.model.dto.InterRecordDto;
import com.tianhengyun.common.tang4jbase.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@TargetDataSource(name = "codeToolDB")
public interface TInterRecordMapper extends AbstractMapper<TInterRecord> {

    List<InterRecordDto> queryList(@Param("ew") Wrapper<TInterRecord> var1);
    IPage<InterRecordDto> queryList(IPage<InterRecordDto> page,@Param("ew") Wrapper<TInterRecord> var1);

    int delete(@Param("dbId") int dbId, @Param("tableName")String tableName);
}