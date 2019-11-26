package com.example.redisdemo.controller;

import com.example.redisdemo.po.Student;
import com.example.redisdemo.selocal.StudentVo;
import com.example.redisdemo.service.student.StudentService;
import com.example.redisdemo.util.RedisUtil;
import com.example.redisdemo.vo.StudentReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("local/student")
@Api(description = "本地-学生管理",tags = "学生管理")
public class StudentController {

   @Autowired
   StudentService studentService;

   @ApiOperation(value = "保存学生类",notes = "保存",response = Boolean.class)
   @RequestMapping(value="saveStudent",method= RequestMethod.POST)
   public Boolean saveStudent(@RequestBody StudentReqVo reqVo){
    return studentService.saveStudent(reqVo);
   }
    @ApiOperation(value = "查询学生类",notes = "查询",response = Student.class)
    @RequestMapping(value="getStudent",method= RequestMethod.GET)
    public Student getStudent(String id){
        return studentService.getStudent(id);
    }
    @ApiOperation(value = "数据库查询学生类",notes = "查询",response = Student.class)
    @RequestMapping(value="getStudentBySql",method= RequestMethod.GET)
    public Student getStudentBySql(Long id){
        return studentService.getStudentBySql(id);
    }

    @ApiOperation(value = "符合查询学生类",notes = "查询",response = StudentVo.class)
    @RequestMapping(value="getStudentVoBySql",method= RequestMethod.GET)
    public List<StudentVo> getStudentVoBySql(){
        return studentService.getStudentVoBySql();
    }
}
