package com.killer.ksport.common.core.constant;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 下午5:36
 * @description：异常code常量,以后会抽取到数据库,支持动态配置
 * @modified By：
 * @version: version
 */
public class ExceptionCodeConstant {

    // 账户异常(不存在的账户)
    public static final Integer EX_USER_INVALID_CODE = 40101;

    // 密码错误异常
    public static final Integer EX_PASSWORD_INCORRECT_CODE=40102;

    // 账户被禁用异常
    public static final Integer EX_USER_FORBIDDEN_CODE = 40103;

    // token异常
    public static final Integer EX_TOKEN_ERROR_CODE = 40201;
}
