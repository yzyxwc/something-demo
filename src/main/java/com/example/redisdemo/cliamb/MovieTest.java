package com.example.redisdemo.cliamb;

import com.alibaba.fastjson.JSON;
import com.example.redisdemo.po.Movie;
import com.example.redisdemo.util.CliambData;
import com.example.redisdemo.util.GetJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MovieTest {
    public static  void  main(String [] args) {
        CliambData<Movie> cliambData = new CliambData<>();
        String address = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=40";
        cliambData.getData(address,Movie.class);
       /* //每页多少条
        int start;
        //记录数
        int total = 0;
        int end = 40;
        for (start  = 0; start <= end; start += 20)  {
            try {

                String address = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=" + start;

                JSONObject dayLine = new GetJson().getHttpJson(address, 1);

                System.out.println("start:" + start);
                JSONArray json = dayLine.getJSONArray("data");
                List<Movie> list = JSON.parseArray(json.toString(), Movie.class);

                if (start <= end){
                    System.out.println("已经爬取到底了");
                }
                System.out.println(JSON.toJSONString(list));
                total += list.size();
                System.out.println("正在爬取中---共抓取:" + total + "条数据");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }*/
    }

}

