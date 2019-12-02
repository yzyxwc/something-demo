package com.example.redisdemo.selocal.hashmap;

import com.example.redisdemo.util.JsonUtils;

/**
 * @author wc
 */
public class CustomHashMapTest {
    public static void main(String[] args) {
        CustomHashMap hashMap = new CustomHashMap();
        hashMap.put("name","wc");
        hashMap.put("age",18);
        hashMap.put("id",123456789L);
        System.out.println(hashMap.get("id"));
    }
}
