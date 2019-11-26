package com.example.redisdemo.repository;

import com.example.redisdemo.po.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {
    @Query(value = "select lt.name,lm.title from local_table lt left join local_movie lm on lt.id = lm.id",nativeQuery = true)
    List<Object[]> select();
}
