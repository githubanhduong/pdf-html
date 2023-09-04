package ru.kaleev.SpringCourse.service.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String date2String(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
