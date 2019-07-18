package com.killer.ksport.common.core.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午3:37
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class BaseController {


    protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    // 自定义类型转换器
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    public Map<String, String> getErrors(BindingResult result)
    {
        Map<String, String> map = new HashMap<>();
        List<FieldError> list = result.getFieldErrors();
        if (CollectionUtils.isNotEmpty(list)) {
            for (FieldError error : list)
            {
                logger.error("error.getField():" + error.getField());
                logger.error("error.getDefaultMessage():" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
        }

        return map;
    }

    public String getErrorsString(BindingResult result)
    {
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> list = result.getFieldErrors();
        if (CollectionUtils.isNotEmpty(list))
        {
            for (FieldError error : list)
            {
                stringBuilder.append(error.getField() + ": " + error.getDefaultMessage() + ";");
            }
            return stringBuilder.toString();
        }

        return null;
    }
}
