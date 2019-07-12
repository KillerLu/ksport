package com.killer.ksport.common.security.exception;

import com.killer.ksport.common.core.constant.ExceptionCodeConstant;
import com.killer.ksport.common.core.exception.CommonException;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 下午5:40
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class UserInvalidException extends CommonException{
    public UserInvalidException(String message) {
        super(message, ExceptionCodeConstant.EX_USER_INVALID_CODE);
    }

    public UserInvalidException() {
        this("用户名或密码错误");
    }
}
