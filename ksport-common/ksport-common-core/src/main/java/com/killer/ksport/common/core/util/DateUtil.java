package com.killer.ksport.common.core.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ：Killer
 * @date ：Created in 19-7-2 下午3:03
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class DateUtil {

    private DateUtil(){

    }

    public static Date getNow(){
        return new Date();
    }

    /**
     * 获取本年
     * @return
     */
    public static int getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取本月
     * @return
     */
    public static int getCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH )+1;
        return month;
    }

    public static int getSchoolYear() {
        int year=getCurrentYear();
        //9月1日开学,9月1日前判定为上学年
        return getCurrentMonth()>=9?year:year-1;
    }


}
