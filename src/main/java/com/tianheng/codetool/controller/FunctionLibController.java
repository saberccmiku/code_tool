package com.tianheng.codetool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianheng.codetool.config.ResponseModel;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.model.TFunctionLib;
import com.tianheng.codetool.service.FunctionLibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/codetool/functionLib")
public class FunctionLibController{

    @Autowired
    private FunctionLibService functionLibService;

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
        List<TFunctionLib> list = functionLibService.selectList(params.getString("name"), params.getString("type"));

        PageInfo<TFunctionLib> pageInfo = new PageInfo<>(list);
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
    public ResponseModel add(@RequestBody TFunctionLib tFunctionLib){
        functionLibService.insert(tFunctionLib);
        return new ResponseModel();
    }

    @PostMapping("/detail")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel detail(@RequestBody TFunctionLib tFunctionLib){
        tFunctionLib = functionLibService.getById(tFunctionLib.getId());
        JSONObject funLibJson = JSONObject.parseObject(JSONObject.toJSONString(tFunctionLib));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        funLibJson.put("addTime", funLibJson.getDate("addTime")==null?"":sdf.format(funLibJson.getDate("addTime")));
        funLibJson.put("editTime", funLibJson.getDate("editTime")==null?"":sdf.format(funLibJson.getDate("editTime")));
        return new ResponseModel(funLibJson);
    }

    @PostMapping("/edit")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel edit(@RequestBody TFunctionLib tFunctionLib){
        functionLibService.update(tFunctionLib);
        return new ResponseModel();
    }

    @PostMapping("/delete")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel delete(@RequestBody TFunctionLib tFunctionLib){
        functionLibService.delete(tFunctionLib.getId());
        return new ResponseModel();
    }

    @PostMapping("/deleteByIds")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel deleteByIds(@RequestBody JSONObject params){
        JSONArray ids = params.getJSONArray("ids");
        for(int i=0;i<ids.size();i++){
            if(ids.getInteger(i)!=null) {
                functionLibService.delete(ids.getIntValue(i));
            }
        }

        return new ResponseModel();
    }
}
