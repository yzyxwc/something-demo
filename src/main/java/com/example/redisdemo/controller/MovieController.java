package com.example.redisdemo.controller;

import com.example.redisdemo.po.Movie;
import com.example.redisdemo.service.movie.MovieService;
import com.example.redisdemo.vo.MovieVo;
import com.example.redisdemo.vo.StudentReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("local/movie")
@Api(description = "本地-movie",tags = "movie")
public class MovieController {
    @Autowired
    MovieService movieService;

    @ApiOperation(value = "保存电影类",notes = "保存",response = Boolean.class)
    @RequestMapping(value="saveMovie",method= RequestMethod.POST)
    public Boolean saveMovie(@RequestBody MovieVo reqVo){
        return movieService.saveMovie(reqVo);
    }
}
