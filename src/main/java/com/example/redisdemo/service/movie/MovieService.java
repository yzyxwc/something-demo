package com.example.redisdemo.service.movie;

import com.example.redisdemo.vo.MovieVo;

public interface MovieService {
    Boolean saveMovie(MovieVo reqVo);
}
