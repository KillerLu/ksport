package com;

import com.alibaba.fastjson.JSONObject;
import com.killer.ksport.common.core.service.impl.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：Killer
 * @date ：Created in 19-7-12 上午10:06
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    private JSONObject json = new JSONObject();

    @Autowired
    private RedisService redisService;

    @Test
    public void contextLoads() throws Exception {
    }


    /**
     * 插入字符串
     */
    @Test
    public void setString() {
        redisService.set("redis_string_test", "springboot redis test");
    }

    /**
     * 获取字符串
     */
    @Test
    public void getString() {
        String result = redisService.get("redis_string_test");
        System.out.println(result);
    }


}
