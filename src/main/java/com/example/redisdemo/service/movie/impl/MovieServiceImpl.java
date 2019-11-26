package com.example.redisdemo.service.movie.impl;

import com.example.redisdemo.po.Movie;
import com.example.redisdemo.repository.CliambMovieRepository;
import com.example.redisdemo.service.movie.MovieService;
import com.example.redisdemo.vo.MovieVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    CliambMovieRepository cliambMovieRepository;

    @Override
    public Boolean saveMovie(MovieVo reqVo) {
        Movie movie = new Movie();
        BeanUtils.copyProperties(reqVo,movie);
        cliambMovieRepository.save(movie);
        return Boolean.TRUE;
    }
}
