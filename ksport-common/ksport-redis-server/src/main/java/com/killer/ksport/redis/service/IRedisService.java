package com.killer.ksport.redis.service;

/**
 * @author ：Killer
 * @date ：Created in 19-7-12 上午9:59
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IRedisService {

    /**
     * set存数据
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, String value);

    /**
     * get获取数据
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 哈希set存数据
     */
    boolean hSet(String key, String hasKey, String value);

    /**
     * 哈希get获取数据
     * @param key
     * @return
     */
    String hGet(String key, String hKey);

    /**
     * 设置有效时间(秒)
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 移除数据
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * 移除哈希key对应的数据
     * @param key
     * @param hKey
     * @return
     */
    boolean hDel(String key, String hKey);
}
