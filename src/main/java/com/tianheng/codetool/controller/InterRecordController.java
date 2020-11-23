package com.tianheng.codetool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianheng.codetool.config.ResponseModel;
import com.tianheng.codetool.datasource.TargetDataSource;
import com.tianheng.codetool.model.TInterRecord;
import com.tianheng.codetool.model.dto.InterRecordDto;
import com.tianheng.codetool.service.InterRecordService;
import com.tianheng.codetool.utils.PropertiesUtil;
import com.tianheng.codetool.utils.SvnKitUtil;
import com.tianhengyun.common.tang4jbase.support.RequestPage;
import com.tianhengyun.common.tang4jbase.utils.QueryWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tmatesoft.svn.core.SVNException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel;

@RestController
@RequestMapping("/codetool/interRecord")
public class InterRecordController {

    @Autowired
    private InterRecordService interRecordService;

    @PostMapping("/list")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel list(@RequestBody RequestPage<InterRecordDto> request) {
        ResponseModel responseModel = new ResponseModel();

        IPage<InterRecordDto> page = new Page<>(request.getCurrent(), request.getSize());
        IPage<InterRecordDto> pageList = interRecordService.queryList(page, QueryWrapperUtil.initFuzzyQuerySql(request.getData()));
        //获取生成代码文件
        for (int i = 0; i < pageList.getRecords().size(); i++) {
            InterRecordDto dto = pageList.getRecords().get(i);
            String tableName = dto.getTableName();
            String tableStr = underlineToCamel(tableName);
            String filePrefix = tableStr.substring(0, 1).toUpperCase() + tableStr.substring(1, tableStr.length());
            JSONObject json = JSONObject.parseObject(dto.getPackageConfig());
            JSONArray dirTree = new JSONArray();
            JSONObject rootDir = new JSONObject();
            rootDir.put("name", json.getString("packageName"));
            JSONArray subArr = new JSONArray();
            Set<String> keySet = json.keySet();
            for (String key : keySet) {
                if (key.equals("controller")
                        || key.equals("service")
                        || key.equals("entity")
                        || key.equals("mapper")
                        || key.equals("xml")) {
                    JSONObject tmpJson = new JSONObject();
                    tmpJson.put("name", json.getString(key));
                    JSONArray chiledArr = new JSONArray();
                    JSONObject fileJson = new JSONObject();
                    String fileName = "";
                    if (key.equals("controller")) {
                        fileName = filePrefix + "Controller.java";
                        fileJson.put("name", fileName);
                        chiledArr.add(fileJson);
                    } else if (key.equals("service")) {
                        fileName = filePrefix + "Service.java";
                        fileJson.put("name", fileName);
                        chiledArr.add(fileJson);

                        JSONObject implNameJson = new JSONObject();
                        implNameJson.put("name", "impl");
                        JSONArray implArr = new JSONArray();
                        JSONObject implJson = new JSONObject();
                        implJson.put("name", filePrefix + "ServiceImpl.java");
                        implArr.add(implJson);
                        implNameJson.put("children", implArr);
                        chiledArr.add(implNameJson);
                    } else if (key.equals("entity")) {
                        fileName = filePrefix + ".java";
                        fileJson.put("name", fileName);
                        chiledArr.add(fileJson);
                    } else if (key.equals("mapper")) {
                        fileName = filePrefix + "Mapper.java";
                        fileJson.put("name", fileName);
                        chiledArr.add(fileJson);
                    } else if (key.equals("xml")) {
                        fileName = filePrefix + "Mapper.xml";
                        fileJson.put("name", fileName);
                        chiledArr.add(fileJson);
                    }
                    tmpJson.put("children", chiledArr);
                    subArr.add(tmpJson);
                }
            }
            rootDir.put("children", subArr);
            dirTree.add(rootDir);
            dto.setDirTree(dirTree);
            dto.setInterArr(JSONArray.parseArray(dto.getInterConfig()));
        }
        responseModel.setData(pageList);
        return responseModel;
    }

    @TargetDataSource(name = "codeToolDB")
    @PostMapping("/delete")
    public ResponseModel delete(@RequestBody TInterRecord interRecord) {
        interRecordService.delete(interRecord.getDbId(), interRecord.getTableName());
        return new ResponseModel();
    }

    @Autowired
    private SvnKitUtil svnKitUtil;
    @TargetDataSource(name = "codeToolDB")
    @PostMapping("/getSubmitFiles")
    public ResponseModel getSubmitFiles() {
        List<File> files = new ArrayList<>();
        File dirFile = new File(PropertiesUtil.getProperty("codetool.outputDir"));
        try {
            svnKitUtil.findCommitFiles(dirFile, files);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        List<String> pathList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            pathList.add(files.get(i).getPath());
        }
        ResponseModel responseModel = new ResponseModel();
        responseModel.setList(pathList);
        return responseModel;
    }

    @PostMapping("/submitToSvn")
    @TargetDataSource(name = "codeToolDB")
    public ResponseModel submitToSvn(@RequestBody JSONArray files) {
        try {
            svnKitUtil.commit(files);
        } catch (Exception e) {
            //e.printStackTrace();
            new ResponseModel(201, e.getMessage());
        }
        return new ResponseModel();
    }
}
