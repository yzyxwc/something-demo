package com.example.redisdemo.repository;

import com.example.redisdemo.po.Movie;
import com.example.redisdemo.po.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CliambMovieRepository extends CrudRepository<Movie,String> {
}
