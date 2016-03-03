package com.showcase.restful.common;

import java.text.SimpleDateFormat;

/**
 * @author: lei hang
 * @date: 2016年03月02日
 * @description:
 */

public class DateFormatConstant {

    //时间格式:年：如：2015，
    public  static final SimpleDateFormat DATA_FORMAT_YEAR = new SimpleDateFormat("yyyy");
    //时间格式:年月：如：2015-12，
    public static final SimpleDateFormat DATA_FORMAT_YEAR_MONTH = new SimpleDateFormat("yyyy-MM");
    //时间格式:年月日：如：2015-12-13，
    public  static final SimpleDateFormat DATA_FORMAT_YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
    //时间格式:年月日 时分秒：如：2015-12-13 12:12:12，
    public static final SimpleDateFormat DATA_FORMAT_YEAR_MONTH_DAY_TIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

}
