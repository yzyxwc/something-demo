package com.example.redisdemo.selocal.stream.sort;

import com.example.redisdemo.selocal.Student;
import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wc
 */
public class ComparingSort {
    public static void main(String[] args) {
        List<Student> list = Lists.newArrayList(
                new Student("wc",12,1,"da"),
                new Student("ac",14,1,"qa"),
                new Student("wb",17,0,"wa"),
                new Student("hd",18,1,"ea"),
                new Student("tb",14,0,"ra"),
                new Student("qw",16,1,"ta"),
                new Student("bv",17,0,"yya"),
                new Student("df",11,1,"dua")
        );
        List<Student> configPoList = Lists.newArrayList();
        List<Student> delete = list.stream()
                .filter(item -> !configPoList.contains(item))
                .collect(Collectors.toList());
        System.out.println(delete);
        //按照性别自然排序 再按照年龄倒叙
        /*list.stream()
                .sorted(Comparator.comparing(Student::getSex)
                        .thenComparing(Student::getAge,Comparator.reverseOrder()))
                .forEach(System.out::println);*/

    }
}
