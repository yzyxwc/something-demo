package com.example.redisdemo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "保存学生类请求Vo")
public class StudentReqVo {
    @ApiModelProperty(value = "姓名",required = true,notes = "姓名",example = "张辉")
    @NotBlank(message = "姓名不能为空")
    private String name;
    @ApiModelProperty(value = "年龄",required = true,notes = "年龄",example = "18")
    private Integer age;
    @ApiModelProperty(value = "性别",required = true,notes = "性别",example = "男")
    private String sex;
    @ApiModelProperty(value = "家庭住址",notes = "家庭住址",example = "成都")
    private String address;
}
