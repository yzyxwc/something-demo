package com.example.redisdemo;

import com.example.redisdemo.enums.PromotionType;
import com.example.redisdemo.selocal.Student;
import com.example.redisdemo.selocal.atomic.MyThread;
import com.example.redisdemo.util.ListsUtil;
import com.google.common.collect.Lists;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestClass {
    static List<String> listA = Lists.newArrayList("1","2","3","4","5","6","7","8","9","10",
           "11","12","13","14","15","16","17","18","19","20");
    //目前统一校验 11位长度 且1开头就是手机号
    public final static String NEW_MOBILE_REG = "^1[0-9]{10}$";

    private final static Student student = new Student("wc",10,1,"COUPON");

    public static void main(String[] args) throws InterruptedException {
        //AtomicIntegerDemo();
        int hash = hash("wc".hashCode());
        System.out.println(hash);
    }

    public static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    /**
     * 测试java并发编程的原子性
     * 这个实现类似 int j = 10
     * for(int i = 0; i < 10; i++){
     *     i++;
     * }
     */
    public static void AtomicIntegerDemo() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }
        for (int j=0;j<10;j++){
            threads[j].join();
        }
        System.out.println(MyThread.atomicInteger);
    }


    /**
     * 测试final关键字
     * description:final的变量成员变量必须赋初始值
     * 且若变量为引用变量 变量的引用不可变 但变量的值可以改变
     */
    public final static void changeFinalParam(){
        student.setAge(20);
        System.out.println(student);
    }

    /**
     * lamda测试分组
     */
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

    /**
     * 测试String的占位符
     */
    public static void placeholderOfString(){
        String message = "今天是%s,天气%s";
        String format = String.format(message, LocalDateTime.now(), "晴");

        String msgFormat = "{0}测试-->{1}功能";
        String s = MessageFormat.format(msgFormat, "wc", "String占位符");
        System.out.println(format);
        System.out.println(s);
    }

    /**
     * 测试正则表达式
     * @param str 输入param 与regex做匹配
     */
    public static void matchPattern(String str){
        String start = "该字符串匹配结果为%b";
        String result = String.format(start,str.matches(NEW_MOBILE_REG));
        System.out.println(result);
    }

    /**
     * lamda 的reduce方法
     */
    public static void lamdaOfSimpleReduce(){
        //详细参见com.example.redisdemo.selocal.stream.reduce包下实现
        List<Integer> integers = Lists.newArrayList(1,2,3,-4,-5,6);
        Integer s = integers.stream().reduce((a,b)->a+b).orElse(null);
        System.out.println(s);
    }

    /**
     * String 的join拼接方法
     */
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
