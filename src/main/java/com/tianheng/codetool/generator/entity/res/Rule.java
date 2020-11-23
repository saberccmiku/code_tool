package com.tianheng.codetool.generator.entity.res;

import com.tianhengyun.common.tang4jbase.validate.RegexType;

/**
 * @author fjy
 * @since 2020-04-30
 * 校验规则
 */
public class Rule {

    /**
     * 类型
     */
    private String type;
    /**
     * 正则表达式字符串
     */
    private String regex;
    /**
     * 简称
     */
    private String abbreviation;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 区间限制
     */
    private Integer[] numbers;

    public Rule() {
    }

    public void regexTypeToRule(RegexType regexType) {
        this.type = regexType.getType();
        this.regex = regexType.getRegex();
        this.abbreviation = regexType.getAbbreviation();
        this.message = regexType.getMessage();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer[] getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer[] numbers) {
        this.numbers = numbers;
    }
}
