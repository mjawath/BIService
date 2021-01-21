package com.techstart.common.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String get_yyyyMMddHHmmss(Date date){
        return dateFormat.format(date);
    }

}
