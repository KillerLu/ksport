package com.killer.ksport.common.core.handler;

import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 上午11:07
 * @description：全局捕获异常类，只要作用在@RequestMapping上，所有的异常都会被捕获
 * @modified By：
 * @version: version
 */
@ControllerAdvice(basePackages="com.killer.ksport")
//注意这里不要写成 com.killer.ksport.*
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(CommonException.class)
    public Object baseExceptionHandler(HttpServletResponse response, CommonException ex) {
        logger.error(ex.getMessage(),ex);
        return new ResponseBuilder().code(ex.getCode()).message(ex.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    public Object otherExceptionHandler(HttpServletResponse response, Exception ex) {
        logger.error(ex.getMessage(),ex);
        return new ResponseBuilder().error().message(ex.getMessage()).build();
    }
}
