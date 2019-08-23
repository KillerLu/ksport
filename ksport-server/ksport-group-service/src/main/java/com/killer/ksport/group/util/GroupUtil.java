package com.killer.ksport.group.util;

import com.killer.ksport.common.core.util.DateUtil;

/**
 * @author ：Killer
 * @date ：Created in 19-8-16 下午4:36
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class GroupUtil {

    /**
     * 根据入学年份获取年级名称
     * @param enrollYear
     * @return
     */
    public static String getGradeName(Integer enrollYear) {
        int currentYear = DateUtil.getSchoolYear();
        if (currentYear - enrollYear == 3) {
            return "大四年级";
        }
        if (currentYear - enrollYear == 2) {
            return "大三年级";
        }
        if (currentYear - enrollYear == 1) {
            return "大二年级";
        }
        if (currentYear - enrollYear == 0) {
            return "大一年级";
        }
        //针对已毕业年级或未入学年级
        return enrollYear+"年级";
    }


    public static String getClassName(Integer enrollYear, Integer index) {
        int currentYear = DateUtil.getSchoolYear();
        if (currentYear - enrollYear == 3) {
            return "大四年级("+index+")班";
        }
        if (currentYear - enrollYear == 2) {
            return "大三年级("+index+")班";
        }
        if (currentYear - enrollYear == 1) {
            return "大二年级("+index+")班";
        }
        if (currentYear - enrollYear == 0) {
            return "大一年级("+index+")班";
        }
        //针对已毕业年级或未入学年级
        return enrollYear+"年级("+index+")班";

    }

    public static void main(String[] args) {
        System.out.println(getGradeName(2019));
    }
}
