package com.example.redisdemo.service.student;

import com.example.redisdemo.po.Student;
import com.example.redisdemo.selocal.StudentVo;
import com.example.redisdemo.vo.StudentReqVo;

import java.util.List;

public interface StudentService {
    Boolean saveStudent(StudentReqVo reqVo);

    Student getStudent(String id);

    Student getStudentBySql(Long id);

    List<StudentVo> getStudentVoBySql();
}
