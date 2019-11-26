package com.example.redisdemo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "local_member")
@Entity
@Data
public class Member {
    @Id
    private Long id = System.currentTimeMillis();
    @Column(name = "member_level")
    private Integer level;
    @Column(name = "member_name")
    private String name;
}
