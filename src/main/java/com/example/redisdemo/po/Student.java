package com.example.redisdemo.po;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "local_table")
@Entity
@Data
@ToString
public class Student {
    @Id
    private Long id = System.currentTimeMillis();
    @Column(length = 64)
    private String name;
    @Column(length = 4)
    private Integer age;
    @Column(length = 2)
    private String sex;
    @Column(length = 64)
    private String address;
}
