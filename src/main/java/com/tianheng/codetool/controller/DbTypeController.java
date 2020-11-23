package com.tianheng.codetool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianheng.codetool.config.ResponseModel;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.model.TDbType;
import com.tianheng.codetool.service.DbTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/codetool/dbType")
public class DbTypeController {

    @Autowired
    private DbTypeService dbTypeService;

    @GetMapping(value = "/index")
    public String index() {
        return "hello";
    }

    @PostMapping(value = "/list")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel list(@RequestBody JSONObject params) {

        int pageNum = 1;
        int pageSize = 20;
        if (params.containsKey("pageNum")) {
            pageNum = params.getIntValue("pageNum");
        }
        if (params.containsKey("pageSize")) {
            pageSize = params.getIntValue("pageSize");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<TDbType> list = dbTypeService.selectList(params.getString("name"));

        PageInfo<TDbType> pageInfo = new PageInfo<>(list);
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

    @PostMapping(value = "/all")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel all() {
        List<TDbType> list = dbTypeService.selectList(null);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setList(list);
        return responseModel;
    }

    @PostMapping(value = "/add")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel add(@RequestBody TDbType tDbType) {
        dbTypeService.insert(tDbType);
        return new ResponseModel();
    }

    @PostMapping(value = "/detail")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel detail(@RequestBody TDbType tDbType) {
        tDbType = dbTypeService.getById(tDbType.getId());

        JSONObject dbTypeJson = JSONObject.parseObject(JSONObject.toJSONString(tDbType));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dbTypeJson.put("addTime", dbTypeJson.getDate("addTime") == null ? "" : sdf.format(dbTypeJson.getDate("addTime")));
        dbTypeJson.put("editTime", dbTypeJson.getDate("editTime") == null ? "" : sdf.format(dbTypeJson.getDate("editTime")));
        return new ResponseModel(dbTypeJson);
    }

    @PostMapping(value = "/edit")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel edit(@RequestBody TDbType tDbType) {
        dbTypeService.update(tDbType);
        return new ResponseModel();
    }

    @PostMapping(value = "/delete")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel delete(@RequestBody TDbType tDbType) {
        dbTypeService.delete(tDbType.getId());
        return new ResponseModel();
    }

    @PostMapping(value = "/deleteByIds")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel deleteByIds(@RequestBody JSONObject params) {
        JSONArray ids = params.getJSONArray("ids");
        for (int i = 0; i < ids.size(); i++) {
            if (ids.getInteger(i) != null) {
                dbTypeService.delete(ids.getInteger(i));
            }
        }
        return new ResponseModel();
    }
}
