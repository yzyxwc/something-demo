package com.example.redisdemo;

import com.example.redisdemo.enums.PromotionType;
import com.example.redisdemo.selocal.Student;
import com.example.redisdemo.util.ListsUtil;
import com.google.common.collect.Lists;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestClass {
    static List<String> listA = Lists.newArrayList("1","2","3","4","5","6","7","8","9","10",
           "11","12","13","14","15","16","17","18","19","20");
    //目前统一校验 11位长度 且1开头就是手机号
    public final static String NEW_MOBILE_REG = "^1[0-9]{10}$";


    public static void main(String[] args) {
        lamdaGroupBy();
    }
    public static void lamdaGroupBy(){
        List<Student> list = Lists.newArrayList(
                new Student("wc",10,1,"COUPON"),
                new Student("xjh",12,1,"COUPON"),
                new Student("wc",15,1,"COUPON"),
                new Student("xjh",18,1,"SEC_KILL"),
                new Student("zq",22,0,"PRE_SALE"),
                new Student("zq",17,1,"SEC_KILL"),
                new Student("zq",10,0,"PRE_SALE")
        );
        Map<PromotionType,List<Integer>> result = list.stream().collect(Collectors.groupingBy(o-> PromotionType.valueOf(o.getPromotionType()),
                Collectors.mapping(Student::getAge, Collectors.toList())));
        System.out.println(result);
    }
    public static void placeholderOfString(){
        String message = "今天是%s,天气%s";
        String format = String.format(message, LocalDateTime.now(), "晴");

        String msgFormat = "{0}测试-->{1}功能";
        String s = MessageFormat.format(msgFormat, "wc", "String占位符");
        System.out.println(format);
        System.out.println(s);
    }

    public static void matchPattern(String str){
        String start = "该字符串匹配结果为%b";
        String result = String.format(start,str.matches(NEW_MOBILE_REG));
        System.out.println(result);
    }
    public static void lamdaOfSimpleReduce(){
        //详细参见com.example.redisdemo.selocal.stream.reduce包下实现
        List<Integer> integers = Lists.newArrayList(1,2,3,-4,-5,6);
        Integer s = integers.stream().reduce((a,b)->a+b).orElse(null);
        System.out.println(s);
    }
    public static void connectionListElements(){
        String collect = String.join("-", listA);
        System.out.println(collect);
    }
    public static void mergeCollect(){
        List<String> listB = Lists.newArrayList("AA", "BB","CC","DD");
        List<String> strings = ListsUtil.replaceListElement(listA, listB, 25);
        System.out.println(strings);
    }


}
