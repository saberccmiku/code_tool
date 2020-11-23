package com.tianheng.codetool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianheng.codetool.config.ResponseModel;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.generator.entity.DbProperties;
import com.tianheng.codetool.generator.entity.res.DbConfig;
import com.tianheng.codetool.model.TDbInfo;
import com.tianheng.codetool.model.TDbType;
import com.tianheng.codetool.service.DbInfoService;
import com.tianheng.codetool.service.DbTypeService;
import com.tianheng.codetool.utils.DbConnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/codetool/dbInfo")
public class DbInfoController{

    @Autowired
    private DbInfoService dbInfoService;
    @Autowired
    private DbTypeService dbTypeService;


    @PostMapping("/list")
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
        List<TDbInfo> list = dbInfoService.selectList(params.getString("name"), params.getInteger("typeId"));
        PageInfo<TDbInfo> pageInfo = new PageInfo<>(list);
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

    @PostMapping("/all")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel all(@RequestBody JSONObject params) {
        List<TDbInfo> list = dbInfoService.selectList(params.getString("name"), params.getInteger("typeId"));
        ResponseModel responseModel = new ResponseModel();
        responseModel.setList(list);
        return responseModel;
    }

    @PostMapping("/add")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel add(@RequestBody TDbInfo tDbInfo) {
        dbInfoService.insert(tDbInfo);
        return new ResponseModel();
    }

    @PostMapping("/detail")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel detail(@RequestBody TDbInfo tDbInfo) {
        tDbInfo = dbInfoService.getById(tDbInfo.getId());
        JSONObject dbInfoJson = JSONObject.parseObject(JSONObject.toJSONString(tDbInfo));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dbInfoJson.put("addTime", dbInfoJson.getDate("addTime") == null ? "" : sdf.format(dbInfoJson.getDate("addTime")));
        dbInfoJson.put("editTime", dbInfoJson.getDate("editTime") == null ? "" : sdf.format(dbInfoJson.getDate("editTime")));
        return new ResponseModel(dbInfoJson);
    }

    @PostMapping("/edit")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel edit(@RequestBody TDbInfo tDbInfo) {
        dbInfoService.update(tDbInfo);
        return new ResponseModel();
    }

    @PostMapping("/delete")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel delete(@RequestBody TDbInfo tDbInfo) {
        dbInfoService.delete(tDbInfo.getId());
        return new ResponseModel();
    }

    @PostMapping(value = "/deleteByIds")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel deleteByIds(@RequestBody JSONObject params) {
        JSONArray ids = params.getJSONArray("ids");
        for (int i = 0; i < ids.size(); i++) {
            if (ids.getInteger(i) != null) {
                dbInfoService.delete(ids.getInteger(i));
            }
        }
        return new ResponseModel();
    }

    @PostMapping(value = "/testConn")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel testConn(@RequestBody TDbInfo dbInfo) {
        TDbType dbType = dbTypeService.getById(dbInfo.getTypeId());
        dbInfo.setTypeName(dbType.getName());
        ResponseModel responseModel = new ResponseModel();
        Connection conn = null;
        try {
            DbProperties dbProperties = DbProperties.getDbProperties(dbInfo.getTypeName());
            String dbUrl = MessageFormat.format(dbProperties.getUrl(), dbInfo.getIp(), String.valueOf(dbInfo.getPort()), dbInfo.getName());
            conn = DriverManager.getConnection(dbUrl, dbInfo.getAccount(), dbInfo.getPassword());
            if (conn != null) {
                responseModel.setData(true);
            } else {
                responseModel.setCode(201);
                responseModel.setMsg("链接异常");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            responseModel.setCode(201);
            responseModel.setMsg(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    responseModel.setCode(201);
                    responseModel.setMsg("链接异常");
                }
            }
        }
        return responseModel;
    }

    @PostMapping(value = "/getTables")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel getTables(@RequestBody TDbInfo tDbInfo) {
        tDbInfo = dbInfoService.getById(tDbInfo.getId());
        TDbType dbType = dbTypeService.getById(tDbInfo.getTypeId());
        tDbInfo.setTypeName(dbType.getName());
        JSONArray tables = DbConnUtils.getTables(dbType.getDriver(), tDbInfo);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setList(tables);
        return responseModel;
    }

    @PostMapping(value = "/getFields")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel getFields(@RequestBody JSONObject params) {
        int id = params.getIntValue("id");
        String tableName = params.getString("tableName");
        TDbInfo tDbInfo = dbInfoService.getById(id);
        TDbType dbType = dbTypeService.getById(tDbInfo.getTypeId());
        tDbInfo.setTypeName(dbType.getName());
        JSONArray fields = DbConnUtils.getFieldsByTable(dbType.getDriver(), tDbInfo, tableName);
        String priKey = DbConnUtils.getPriKeyByTable(dbType.getDriver(), tDbInfo, tableName);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setData(priKey);
        responseModel.setList(fields);
        return responseModel;
    }

    @PostMapping(value = "/getPriKeyByTable")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel getPriKeyByTable(@RequestBody JSONObject params) {
        int id = params.getIntValue("id");
        String tableName = params.getString("tableName");
        TDbInfo tDbInfo = dbInfoService.getById(id);
        TDbType dbType = dbTypeService.getById(tDbInfo.getTypeId());
        String priKey = DbConnUtils.getPriKeyByTable(dbType.getDriver(), tDbInfo, tableName);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setData(priKey);
        return responseModel;
    }

    @Autowired
    private DbConfig dbConfig;

    @GetMapping("/getTest")
    @TargetDataSource(name = "codeToolDB")
    public Object getTest() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();//数据源配置
        //数据源配置
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbConfig.getDbUrl())
                .setUsername(dbConfig.getDbUser())
                .setPassword(dbConfig.getDbPwd())
                .setDriverName(dbConfig.getDriver());
        dataSourceConfig.setDbQuery(null);
        return dataSourceConfig.getDbQuery();
    }
}
