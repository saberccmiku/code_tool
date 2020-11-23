package com.tianheng.codetool.generator.entity;

import com.tianhengyun.common.tang4jbase.exception.ValidateException;

/**
 * 指定接口{@link com.tianheng.codetool.generator.entity.res.Interfaces}中的method
 */
public enum Method {

    INSERT("insert", "post", "@PostMapping", "新增" ),
    INSERT_BATCH("insertBatch", "post", "@PostMapping", "批量新增" ),
    UPDATE("update", "put", "@PutMapping", "修改" ),
    UPDATE_BATCH("updateBatch", "put", "@PutMapping", "批量修改" ),
    DELETE("delete", "delete", "@DeleteMapping", "删除" ),
    DELETE_BATCH("deleteBatch", "delete", "@DeleteMapping", "批量删除" ),
    DETAIL("detail", "get", "@GetMapping", "详情" ),
    LIST("list", "get", "@GetMapping", "查询" ),
    PAGE_List("pageList", "get", "@GetMapping", "分页查询" ),
    EDIT_BY_CRIT("editByCrit", "get", "@PutMapping", "条件修改" ),
    DEL_BY_CRIT("delByCrit", "delete", "@DeleteMapping", "条件删除" );

    //方法编码
    private final String label;
    //方法名称
    private final String val;
    //请求方式
    private final String restful;
    //请求方式spring注解
    private final String annotation;

    public static Method getMethod(String type) {
        Method[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Method method = var1[var3];
            if (method.label.equalsIgnoreCase(type)) {
                return method;
            }
        }

        throw new ValidateException(Method.class.getName() + ":No eligible methods found > " + type);
    }


    Method(String label, String restful, String annotation, String val) {
        this.label = label;
        this.val = val;
        this.restful = restful;
        this.annotation = annotation;
    }

    public String getLabel() {
        return label;
    }

    public String getVal() {
        return val;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getRestful() {
        return restful;
    }
}
