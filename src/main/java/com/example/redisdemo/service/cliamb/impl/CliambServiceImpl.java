package com.example.redisdemo.service.cliamb.impl;

import com.example.redisdemo.po.Movie;
import com.example.redisdemo.repository.CliambMovieRepository;
import com.example.redisdemo.service.cliamb.CliambService;
import com.example.redisdemo.util.CliambData;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CliambServiceImpl implements CliambService {
    @Autowired
    CliambMovieRepository cliambMovieRepository;
    private static String URL = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=";
    @Override
    public Boolean getData(int start,int size) {
        CliambData<Movie> cliambData = new CliambData<>();
        List<Movie> list = Lists.newArrayList();
        for (int i = start; i < size; i+=20) {
            String address = URL+i;
            list.addAll(cliambData.getData(address,Movie.class));
        }
        cliambMovieRepository.saveAll(list);
        return Boolean.TRUE;
    }
}
