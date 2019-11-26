package com.example.redisdemo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "赖主任返回Vo")
public class StudentVo {
    /**
     * 名字
     */
    @ApiModelProperty(value = "名字",notes = "名字",example = "wc")
    private String name ;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题",notes = "标题",example = "测试")
    private String title ;
}
