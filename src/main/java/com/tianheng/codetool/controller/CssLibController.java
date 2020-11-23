package com.tianheng.codetool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianheng.codetool.config.ResponseModel;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.model.TCssLib;
import com.tianheng.codetool.service.CssLibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/codetool/cssLib")
public class CssLibController{

    @Autowired
    private CssLibService cssLibService;

    @GetMapping("/test")
    public String test(){
        return "hello";
    }

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
        List<TCssLib> list = cssLibService.selectList(params.getString("name"));

        PageInfo<TCssLib> pageInfo = new PageInfo<>(list);
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
    public ResponseModel add(@RequestBody TCssLib tCssLib){
        cssLibService.insert(tCssLib);
        return new ResponseModel();
    }

    @PostMapping("/detail")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel detail(@RequestBody TCssLib tCssLib){
        tCssLib = cssLibService.getById(tCssLib.getId());
        JSONObject cssLibJson = JSONObject.parseObject(JSONObject.toJSONString(tCssLib));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cssLibJson.put("addTime", cssLibJson.getDate("addTime")==null?"":sdf.format(cssLibJson.getDate("addTime")));
        cssLibJson.put("editTime", cssLibJson.getDate("editTime")==null?"":sdf.format(cssLibJson.getDate("editTime")));
        return new ResponseModel(cssLibJson);
    }

    @PostMapping("/edit")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel edit(@RequestBody TCssLib tCssLib){
        cssLibService.update(tCssLib);
        return new ResponseModel();
    }

    @PostMapping("/delete")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel delete(@RequestBody TCssLib tCssLib){
        cssLibService.delete(tCssLib.getId());
        return new ResponseModel();
    }

    @PostMapping("/deleteByIds")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel deleteByIds(@RequestBody JSONObject params){
        JSONArray ids = params.getJSONArray("ids");
        for(int i=0;i<ids.size();i++){
            if(ids.getInteger(i)!=null) {
                cssLibService.delete(ids.getIntValue(i));
            }
        }

        return new ResponseModel();
    }
}
