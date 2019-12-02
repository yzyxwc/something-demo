package com.example.redisdemo.selocal.hashmap;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wc
 */
@SuppressWarnings("unchecked")
public class CustomHashMap {
    /**
     * 定义数组+链表的结构
     */
    private LinkedList[] array = new LinkedList[999];

    /**
     * put方法
     * @param key 键
     * @param value 值
     */
    public void put(Object key,Object value){
        CustomEntity entity = new CustomEntity(key,value);
        int position = key.hashCode() % array.length;
        if (null == array[position]) {
            LinkedList<CustomEntity> list = Lists.newLinkedList();
            list.add(entity);
            array[position] = list;
        }else {
            LinkedList list = array[position];
            for (Object element : list) {
                CustomEntity e = (CustomEntity) element;
                if (e.key.equals(key)) {
                    e.value = value;
                    return;
                }
            }
            array[position].add(entity);
        }
    }
    /**
     * get方法
     * @param key 键
     * @return 值
     */
    public Object get(Object key){
        int position = key.hashCode() % array.length;
        if(null != array[position]){
            LinkedList list = array[position];
            for (Object e : list) {
                CustomEntity entity = (CustomEntity) e;
                if (entity.key.equals(key)) {
                    return entity.value;
                }
            }
        }
        return null;
    }
}
@Data
@AllArgsConstructor
@ToString
class CustomEntity{
    Object key;
    Object value;
}