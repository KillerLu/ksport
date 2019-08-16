package com.killer.ksport.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-12 上午10:14
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Controller
@RequestMapping("/test")
public class RedisTestController {

    private JSONObject json = new JSONObject();

    @Autowired
    private IRedisService redisService;

    /**
     * 插入字符串
     */
    @RequestMapping("/setString")
    public void setString() {
        redisService.set("redis_string_test", "springboot redis test");
    }

    /**
     * 获取字符串
     */
    @RequestMapping("/getString")
    public void getString() {
        String result = redisService.get("redis_string_test");
        System.out.println(result);
    }


    /**
     * 插入对象
     */
    @RequestMapping("/setObject")
    public void setObject() {
        Role role = new Role("Admin");
        redisService.set("redis_obj_test", json.toJSONString(role));
    }

    /**
     * 获取对象
     */
    @RequestMapping("/getObject")
    public void getObject() {
        String result = redisService.get("redis_obj_test");
        Role role = json.parseObject(result, Role.class);
        System.out.println(json.toJSONString(role));
    }

    /**
     * 插入对象List
     */
    @RequestMapping("/setList")
    public void setList() {
        String remark="ADDD";
        Role person1 = new Role("Admin1");
        person1.setRemark(remark);
        Role person2 = new Role("Admin2");
        person2.setRemark(remark);
        Role person3 = new Role("Admin3");
        person3.setRemark(remark);
        List<Role> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);
        list.add(person3);
        redisService.set("redis_list_test", json.toJSONString(list));
    }

    /**
     * 获取list
     */
    @RequestMapping("/getList")
    public void getList() {
        String result = redisService.get("redis_list_test");
        List<String> list = json.parseArray(result, String.class);
        System.out.println(list);
    }

    @RequestMapping("/remove")
    public void remove() {
        redisService.remove("aaa");
    }

//    /**
//     * 插入对象
//     */
//    @RequestMapping("/hSetObject")
//    public void hSetObject() {
//        LoginUser loginUser = new LoginUser();
//        loginUser.setUserId(1l);
//        String remark="水电费水电费";
//        Role person1 = new Role("Admin1");
//        person1.setRemark(remark);
//        Role person2 = new Role("Admin2");
//        person2.setRemark(remark);
//        Role person3 = new Role("Admin3");
//        person3.setRemark(remark);
//        List<Role> list = new ArrayList<>();
//        list.add(person1);
//        list.add(person2);
//        list.add(person3);
//        loginUser.setRoles(list);
//        redisService.hSet("KEY1", "HKEY1", json.toJSONString(loginUser));
//    }
//
//    /**
//     * 获取对象
//     */
//    @RequestMapping("/hGetObject")
//    public void hGetObject() {
//        String result = redisService.hGet(RedisKeyConstant.USER_DETAILS, "1");
//        LoginUser loginUser = json.parseObject(result, LoginUser.class);
//        System.out.println(json.toJSONString(loginUser));
//    }
}
