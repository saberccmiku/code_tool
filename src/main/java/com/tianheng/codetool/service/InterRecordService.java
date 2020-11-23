package com.tianheng.codetool.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tianheng.codetool.model.TInterRecord;
import com.tianheng.codetool.model.dto.InterRecordDto;
import com.tianhengyun.common.tang4jbase.abstracts.AbstractService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterRecordService extends AbstractService<TInterRecord> {

    int delete(int dbId, String tableName);

    List<InterRecordDto> queryList(@Param("ew") Wrapper<TInterRecord> var1);

    IPage<InterRecordDto> queryList(IPage<InterRecordDto> page, @Param("ew") Wrapper<TInterRecord> var1);
}
