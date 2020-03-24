package com.example.redisdemo.controller;

import com.example.redisdemo.annocation.InterfaceLimit;
import com.example.redisdemo.po.Student;
import com.example.redisdemo.selocal.StudentVo;
import com.example.redisdemo.service.student.StudentService;
import com.example.redisdemo.util.JsonResult;
import com.example.redisdemo.vo.StudentReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("local/student")
@Api(description = "本地-学生管理",tags = "学生管理")
public class StudentController {

   @Autowired
   StudentService studentService;


   @RequestMapping(value="saveStudent",method= RequestMethod.POST)
   public Boolean saveStudent(@RequestBody StudentReqVo reqVo){
    return studentService.saveStudent(reqVo);
   }
    /**
     * @return
     */
    @ApiOperation(value = "测试防刷",notes = "测试防刷",response = JsonResult.class)
    @GetMapping(value = "/test")
    @InterfaceLimit(message = "10秒内不允许重复调多次", value = "10")//value 表示10表示10秒
    public JsonResult testPreventIncludeMessage(String name) {
        return new JsonResult<String>("调用成功");
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
    @ApiOperation(value = "符合查询学生类",notes = "查询",response = Void.class)
    @RequestMapping(value="testFile",method= RequestMethod.GET)
    public  void putOrgConfig(String content) {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String basePath = getResourceBasePath();
        String studentResourcePath = new File(basePath, "change/org/student.txt").getAbsolutePath();
        // 保证目录一定存在
        ensureDirectory(studentResourcePath);
        System.out.println("studentResourcePath = " + studentResourcePath);
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(studentResourcePath)));
            writer.write(content);

            writer.flush();
            writer.close();
        }catch (Exception e){
            log.debug(e.getMessage());
        }

    }
    @ApiOperation(value = "符合查询学生类",notes = "查询",response = Void.class)
    @RequestMapping(value="testFileRead",method= RequestMethod.GET)
    public  void putOrgConfig() {
        String basePath = getResourceBasePath();
        String studentResourcePath = new File(basePath, "change/org/student.txt").getAbsolutePath();
        // 保证目录一定存在
        ensureDirectory(studentResourcePath);
        System.out.println("studentResourcePath = " + studentResourcePath);
        StringBuilder result =new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(studentResourcePath));
            if(!reader.ready())
            {
                System.out.println("文件流暂时无法读取");
                return;
            }
            String s = null;
            while ((s = reader.readLine()) != null) {
                result.append(s);
            }
            reader.close();
        }catch (Exception e){
            log.debug(e.getMessage());
        }
        System.out.println(result.toString());

    }
    private void ensureDirectory(String filePath) {
        if (org.apache.commons.lang3.StringUtils.isBlank(filePath)) {
            return;
        }
        filePath = replaceSeparator(filePath);
        if (filePath.indexOf("/") != -1) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    private String replaceSeparator(String filePath) {
        return filePath.replace("\\", "/").replace("\\\\", "/");
    }

    private static String getResourceBasePath() {
        // 获取跟目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }

        String pathStr = path.getAbsolutePath();
        // 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
        pathStr = pathStr.replace("\\target\\classes", "");

        return pathStr;
    }
}
