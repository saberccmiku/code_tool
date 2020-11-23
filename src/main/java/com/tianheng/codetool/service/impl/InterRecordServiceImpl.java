package com.tianheng.codetool.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tianheng.codetool.dao.TInterRecordMapper;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.model.TInterRecord;
import com.tianheng.codetool.model.dto.InterRecordDto;
import com.tianheng.codetool.service.InterRecordService;
import com.tianhengyun.common.tang4jbase.abstracts.AbstractServiceImpl;
import com.tianhengyun.common.tang4jbase.utils.DataUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InterRecordServiceImpl extends AbstractServiceImpl<TInterRecordMapper, TInterRecord> implements InterRecordService {

    @Resource
    private TInterRecordMapper interRecordMapper;

    @Override
    public int delete(int dbId, String tableName) {
        return interRecordMapper.delete(dbId, tableName);
    }

    @Override
    public List<InterRecordDto> queryList(Wrapper<TInterRecord> var1) {
        return interRecordMapper.queryList(var1);
    }

    @Override
    public IPage<InterRecordDto> queryList(IPage<InterRecordDto> page, Wrapper<TInterRecord> var1) {
        return interRecordMapper.queryList(page, var1);
    }


    @Override
    public boolean saveOrUpdate(TInterRecord record) {
        String interConfig = record.getInterConfig();
        JSONArray objects = JSON.parseArray(interConfig);
        QueryWrapper<TInterRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("db_id", record.getDbId());
        queryWrapper.eq("table_name", record.getTableName());
        TInterRecord interRecord = this.interRecordMapper.selectOne(queryWrapper);
        if (!DataUtil.isEmpty(interRecord)) {
            record.setId(interRecord.getId());
            JSONArray tempObjects = JSON.parseArray(interRecord.getInterConfig());
            for (Object object : objects) {
                if (!tempObjects.contains(object)) {
                    tempObjects.add(object);
                }
            }
            record.setInterConfig(tempObjects.toJSONString());
        }
        return super.saveOrUpdate(record);
    }
}
