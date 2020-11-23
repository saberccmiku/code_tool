package com.tianheng.codetool.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianheng.codetool.config.ResponseModel;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.model.TControlLib;
import com.tianheng.codetool.model.TControlLibWithBLOBs;
import com.tianheng.codetool.service.ControlLibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/codetool/controlLib")
public class ControlLibController{

    @Autowired
    private ControlLibService controlLibService;

    @PostMapping("/list")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel list(@RequestBody JSONObject params){
        int pageNum = 1;
        int pageSize = 20;
        if(params.containsKey("pageNum")){
            pageNum = params.getIntValue("pageNum");
        }
        if(params.containsKey("pageSize")){
            pageSize = params.getIntValue("pageSize");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TControlLib> list = controlLibService.selectList(params.getString("name"), params.getString("type"));

        PageInfo<TControlLib> pageInfo = new PageInfo<>(list);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setList(pageInfo.getList());
        JSONObject page = new JSONObject();
        page.put("pageNum", pageInfo.getPageNum());
        page.put("pageSize", pageInfo.getPageSize());
        page.put("total", pageInfo.getTotal());
        page.put("pageCount", pageInfo.getPages());
        responseModel.setPage(page);
        return responseModel;
    }

    @PostMapping("/add")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel add(@RequestBody TControlLibWithBLOBs tControlLib){
        controlLibService.insert(tControlLib);
        return new ResponseModel();
    }

    @PostMapping("/detail")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel detail(@RequestBody TControlLibWithBLOBs tControlLib){
        tControlLib = controlLibService.getById(tControlLib.getId());
        return new ResponseModel(tControlLib);
    }

    @PostMapping("/edit")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel edit(@RequestBody TControlLibWithBLOBs tControlLib){
        controlLibService.update(tControlLib);
        return new ResponseModel();
    }

    @PostMapping("/delete")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel delete(@RequestBody TControlLib tControlLib){
        controlLibService.delete(tControlLib.getId());
        return new ResponseModel();
    }
}
