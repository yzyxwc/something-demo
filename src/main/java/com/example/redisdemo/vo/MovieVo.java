package com.example.redisdemo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@ApiModel(description = "保存电影类请求Vo")
public class MovieVo {
    /**
     * 电影的id
     */
    @ApiModelProperty(value = "电影的id",required = true,notes = "电影的id",example = "1")
    private String id ;

    /**
     * 电影的id
     */
    @ApiModelProperty(value = "电影的id",required = true,notes = "电影的id",example = "1")
    private Long time ;
    /**
     * 导演
     */
    @ApiModelProperty(value = "电影的导演",required = true,notes = "电影的导演",example = "wc")
    private String  directors;
    /**
     * 标题
     */
    @ApiModelProperty(value = "电影的导演",required = true,notes = "电影的导演",example = "wc")
    private String title;
    /**
     * 封面
     */
    @ApiModelProperty(value = "电影的导演",required = true,notes = "电影的导演",example = "wc")
    private String cover;
    /**
     * 评分
     */
    @ApiModelProperty(value = "电影的评分",required = true,notes = "电影的评分",example = "8.0")
    private String rate;
    /**
     * 演员
     */
    @ApiModelProperty(value = "电影的演员",required = true,notes = "电影的演员",example = "wc")
    private String casts;
    @ApiModelProperty(value = "电影的导演",required = true,notes = "电影的导演",example = "2016-11-01 22:37:23")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createDate;
}
