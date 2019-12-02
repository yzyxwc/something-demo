package com.example.redisdemo.selocal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author wc
 */
@Data
@ToString
@AllArgsConstructor
public class Student {
    private String name;
    private Integer age;
    private int sex;
    private String promotionType;
}
