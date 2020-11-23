package com.tianheng.codetool.generator.entity.res;

import com.tianhengyun.common.tang4jbase.utils.DataUtil;
import com.tianhengyun.common.tang4jbase.validate.RegexType;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author fjy
 * @since 2020-04-30
 * 校验规则
 */
public class Check {
    /**
     * 字段名
     */
    @NotBlank(message = "checkList中fieldName字段名不能为空")
    private String fieldName;

    /**
     * 字段名首字母大写
     */
    private String capitalName;
    /**
     * 字段类型
     */
    @NotBlank(message = "checkList中fieldType字段类型不能为空")
    private String fieldType;

    /**
     * java字段类型
     */
    private String fieldJavaType;
    /**
     * 字段描述
     */
//    @NotNull(message = "checkList中desc接口描述不能空" )
    private String desc;
    /**
     * 校验规则
     */
    @NotEmpty(message = "checkList中rule校验规则数组不能为空")
    private List<Rule> rule;

    /**
     * 执行何种sql操作符
     */
    private String operator;

    /**
     * 注解符号
     */
    private String validSymbol;

    /**
     * 別名(入参字段用到这个)
     */
//    @NotBlank(message = "alias別名不能为空")
    private String alias;

    /**
     * 将所有的注解规则拼成一个字符串
     */
    private String validAnnotations;


    public void setValidAnnotations(String validAnnotations) {
        this.validAnnotations = validAnnotations;
    }

    public void setValidAnnotations() {
        StringBuilder sb = new StringBuilder();
        rule.forEach(item -> {
            if (!DataUtil.isEmpty(item.getType())) {
                if (item.getType().equalsIgnoreCase(RegexType.REQUIRED.getType())) {//非空注解
                    sb.append(validSymbol).append("(").append("message = ").
                            append("\"").
                            append(fieldName).append("必填").append("\"").append(")");
                } else if (DataUtil.getNumberTypeList().contains(fieldJavaType)) {//数字注解
                    //TODO
                    Integer[] numbers = item.getNumbers();
                    if (!ArrayUtils.isEmpty(numbers)) {
                        if (numbers.length == 1) {
                            sb.append("@Min").append("(").append("value = ").
                                    append(numbers[0]).append(")").
                                    append("@Max").append("(").append("value = ").
                                    append(numbers[0]).append(")");
                        } else {
                            sb.append("@Min").append("(").append("value = ").
                                    append(numbers[0]).append(")").
                                    append("@Max").append("(").append("value = ").
                                    append(numbers[1]).append(")");
                        }
                    }
                } else {//字符注解
                    sb.append("@Pattern").append("(").append("regexp = ").append("\"").
                            append(item.getRegex()).append("\"").
                            append(",").append("message = ").append("\"").
                            append(fieldName).append(item.getMessage()).append("\"").append(")");

                }
            }
        });
        setValidAnnotations(sb.toString());
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValidSymbol() {
        return validSymbol;
    }

    public void setValidSymbol(String validSymbol) {
        this.validSymbol = validSymbol;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getValidAnnotations() {
        return validAnnotations;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public String getFieldJavaType() {

        return fieldJavaType;
    }

    public void setFieldJavaType(String fieldJavaType) {
        this.fieldJavaType = fieldJavaType;
    }
}