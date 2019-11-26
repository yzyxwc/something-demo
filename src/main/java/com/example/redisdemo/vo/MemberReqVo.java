package com.example.redisdemo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "保存会员类请求Vo")
public class MemberReqVo {
    @ApiModelProperty(value = "会员等级",required = true,notes = "会员等级",example = "1")
    private Integer level;
    @ApiModelProperty(value = "会员名",required = true,notes = "会员名",example = "白云苍狗")
    private String name;
}
