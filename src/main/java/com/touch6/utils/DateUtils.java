package com.touch6.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LONG on 2017/2/23.
 */
public class DateUtils {
    public static final String format1 = "yyyy-MM-dd";
    public static final String format2 = "yyyy-MM-dd HH:mm";
    public static final String format3 = "yyyy-MM-dd HH:mm:ss";
    public static final String format4 = "yyyy年MM月dd日";
    public static final String format5 = "yyyy年MM月dd日HH时mm分";
    public static final String format6 = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String format7 = "yyyy/MM/dd";
    public static final String format8 = "yyyy/MM/dd/HH/m";
    public static final String format9 = "yyyy/MM/dd/HH/mm/ss";

    public static final Date nowTime() {
        return new Date();
    }

    public static final String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
    public static Date parseDate(String format,String date){
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Long dateParse(String src, String format) {
        try {
            return new SimpleDateFormat(format).parse(src).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String dateFormat(String date, String format) {
        return new SimpleDateFormat(format).format(new Date(Long.parseLong(date)));
    }


}
