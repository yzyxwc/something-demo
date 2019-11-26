package com.example.redisdemo.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CliambData<T> {
    public  List<T>  getData(String url,Class<T> t){

        //记录数
        int total = 0;
        List<T> list = Lists.newArrayList();
        try {
            JSONObject dayLine = new GetJson().getHttpJson(url, 1);

            JSONArray json = dayLine.getJSONArray("data");
            list = JSON.parseArray(json.toString(), t);

            System.out.println(JSON.toJSONString(list));
            total += list.size();
            System.out.println("正在爬取中---共抓取:" + total + "条数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return list;
    }
}
