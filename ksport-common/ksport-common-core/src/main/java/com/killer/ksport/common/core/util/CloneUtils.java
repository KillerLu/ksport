package com.killer.ksport.common.core.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 上午9:33
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class CloneUtils {

    /**
     * 拷贝对象
     * @param source  被拷贝的对象
     * @param classType  拷贝后对象的类型
     * @return
     */
    public static <T, E> E clone(T source, Class<E> classType) {

        if (source == null) {
            return null;
        }
        E targetInstance = null;
        try {
            targetInstance = classType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, targetInstance);
        return targetInstance;
    }

    /**
     * 拷贝集合对象
     * @param sourceList
     * @param classType
     * @return
     */
    public static <T, E> List<E> batchClone(List<T> sourceList, Class<E> classType) {
        if (sourceList == null) {
            return null;
        }
        List<E> result = new ArrayList<E>();
        int size = sourceList.size();
        for (int i = 0; i < size; i++) {
            result.add(clone(sourceList.get(i), classType));
        }
        return result;
    }

}
