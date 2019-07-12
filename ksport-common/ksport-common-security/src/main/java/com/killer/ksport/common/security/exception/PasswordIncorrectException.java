package com.killer.ksport.common.security.exception;

import com.killer.ksport.common.core.constant.ExceptionCodeConstant;
import com.killer.ksport.common.core.exception.CommonException;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 下午5:43
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class PasswordIncorrectException extends CommonException {

    public PasswordIncorrectException(String message) {
        super(message, ExceptionCodeConstant.EX_PASSWORD_INCORRECT_CODE);
    }

    public PasswordIncorrectException() {
        this("密码错误");
    }
}
