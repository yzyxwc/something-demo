package com.example.redisdemo.controller;

import com.example.redisdemo.service.cliamb.CliambService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("cliamb")
@RestController
@Api(description = "本地-爬取豆瓣数据",tags = "爬虫")
public class CliambController {
    @Autowired
    CliambService cliambService;
    @ApiOperation(value = "爬取豆瓣数据",notes = "爬虫",response = Boolean.class)
    @RequestMapping(value="data",method= RequestMethod.GET)
    public Boolean data(int start,int size){
        return cliambService.getData(start,size);
    }
}
