package com.knowincloud.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LONG on 2017/2/23.
 */
public class DateUtil {
    public static final String format_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static final Date nowTime() {
        return new Date();
    }

    public static final String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
