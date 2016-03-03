package com.showcase.restful.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 */

public class ParamChecker {

    public static final String PARAM_NOT_NULL_TIPS = "param [%s] is null";
    public static final String PARAM_NOT_NUMBER = "param [%s] must be number";
    public static final String PARAM_OVER_MAXLENGTH = "param [%s] maxlength is ";
    public static final String PARAM_NOT_DATE = "param [%s] must be [%s]";

    private boolean isValid = true;
    private String checkResult;

    public static ParamChecker newInstance() {
        return new ParamChecker();
    }

    public boolean isValid() {
        return this.isValid;
    }

    public boolean isNotValid() {
        return !isValid();
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    //非空检查
    public ParamChecker notBlankCheck(String paramName, String paramVal) {
        if (!isValid())
            return this;
        if (StringUtils.isBlank(paramVal)) {
            this.checkResult = String.format(PARAM_NOT_NULL_TIPS, paramName);
            this.setValid(false);
        }
        return this;
    }

    //数字类型检查
    public ParamChecker notNumberCheck(String paramName, String paramVal) {
        if (!isValid())
            return this;
        if (!NumberUtils.isNumber(paramVal)) {
            this.checkResult = String.format(PARAM_NOT_NUMBER, paramName, paramVal);
            this.setValid(false);
        }
        return this;
    }

    //非空时数字类型检查
    public ParamChecker notNullNumberCheck(String paramName, String paramVal) {
        if (!isValid())
            return this;
        if (StringUtils.isNotEmpty(paramVal) && !NumberUtils.isNumber(paramVal)) {
            this.checkResult = String.format(PARAM_NOT_NUMBER, paramName, paramVal);
            this.setValid(false);
        }
        return this;
    }

    //长度检查
    public ParamChecker paramLengthCheck(String paramName, String paramVal, int length) {
        if (!isValid())
            return this;
        if (StringUtils.isNotEmpty(paramVal) && length < paramVal.length()) {
            this.checkResult = String.format(PARAM_OVER_MAXLENGTH, paramName) + length;
            this.setValid(false);
        }
        return this;
    }

    //日期字符串
    public ParamChecker paramDateCheck(String paramName, String paramVal, String formatStr) {
        if (!isValid())
            return this;
        if (StringUtils.isNotBlank(paramVal)) {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            try {
                format.parse(paramVal);//NOSONAR
            } catch (ParseException e) {
                this.checkResult = String.format(PARAM_NOT_DATE, paramName, formatStr);
                this.setValid(false);
            }

        }
        return this;
    }

    public String getCheckResult() {
        return this.checkResult;
    }

}
