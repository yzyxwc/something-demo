package com.example.redisdemo.service.student.impl;

import com.example.redisdemo.po.Student;
import com.example.redisdemo.repository.StudentRepository;
import com.example.redisdemo.selocal.StudentVo;
import com.example.redisdemo.service.student.StudentService;
import com.example.redisdemo.util.ClassCastUtil;
import com.example.redisdemo.util.RedisUtil;
import com.example.redisdemo.vo.StudentReqVo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    StudentRepository studentRepository;

    @Override
    public Boolean saveStudent(StudentReqVo reqVo) {
        Student student = new Student();
        BeanUtils.copyProperties(reqVo,student);
        Student save = studentRepository.save(student);
        if(null != save){
          return  redisUtil.set("student:"+save.getId().toString(),save);
        }
        return Boolean.FALSE;
    }

    @Override
    public Student getStudent(String id) {
        return (Student) redisUtil.get("student:"+id);
    }

    @Override
    public Student getStudentBySql(Long id) {
        return studentRepository.findById(id).orElse(new Student());
    }

    @Override
    public List<StudentVo> getStudentVoBySql() {
        List<Object[]> select = studentRepository.select();
        List<StudentVo> list = Lists.newArrayList();
        try {
            list = ClassCastUtil.castEntity(select, StudentVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
