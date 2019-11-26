package com.example.redisdemo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "local_movie")
@Entity
@Data
public class Movie {
    /**
     * 电影的id
     */
    @Id
    private String id;

    private Long time;
    /**
     * 导演
     */
    private String  directors;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面
     */
    private String cover;
    /**
     * 评分
     */
    private String rate;
    /**
     * 演员
     */
    private String casts;
    private Timestamp createDate;
}
