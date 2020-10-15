package com.example.redisdemo.arithAndData;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 数组里面找出缺失的数
 * @author wc
 * @date 2020/10/15 10:14
 */
public class OrderArrayFindMissNum {
    //找出不重复数组中缺失的一个数
    private static List<Integer> notRepeatArr = Lists.newArrayList();
    //找出重复数组中一个个重复奇数次的数
    private static List<Integer> repeatArr = Lists.newArrayList();
    //找出重复数组中两个重复奇数次的数
    private static List<Integer> repeatArrTwice = Lists.newArrayList();

    static {
        for (int i = 1; i < 101; i++) {
            if(i == 10){
                continue;
            }
            notRepeatArr.add(i);
        }
    }
    static {
        for (int i = 1; i < 100; i++) {
            if(i ==79 ){
                repeatArr.add(i);
                continue;
            }
            repeatArr.add(i);
            repeatArr.add(i);
        }
    }
    static {
        for (int i = 1; i < 101; i++) {
            if(i ==100 || i == 4 ){
                repeatArrTwice.add(i);
                continue;
            }
            repeatArrTwice.add(i);
            repeatArrTwice.add(i);
        }
    }

    /**
     * 找出不重复数组中缺失的一个数暂定的是1-100
     * 方式 所有的数求和 - 不重复数数组的和
     * @return 缺失的数
     */
    public static Integer findNotRepeatArrMissNum(){
        //1.求所有数的和
        Integer sum = (1 + 100) * 100/2;
        //依次减去
        for (Integer num : notRepeatArr) {
            sum -= num;
        }
        return sum;
    }

    /**
     * 找出重复数组的唯一奇次重复数
     * 依次异或 因为偶次的数异或之后肯定为0
     * 例如:1,1,2,2,2,3,3,3,3,4,4
     * 1^1 = 0
     * 1^1^2^2^2^3^3^3^3^4^4 = 2
     * @return
     */
    public static Integer findOneRepeatArrNum(){
        Integer result = 0;
        for (Integer num : repeatArr) {
            result ^= num;
        }
        return result;
    }

    /**
     * 找出2个出现奇数次的数
     * 分治法
     * 遍历整个数组，依次做异或运算。由于数组存在两个出现奇数次的整数，所以最终异或的结果，等同于这两个整数的异或结果。这个结果中，至少会有一个二进制位是1（如果都是0，说明两个数相等，和题目不符）。
     *
     * 举个例子，如果最终异或的结果是5，转换成二进制是00000101。此时我们可以选择任意一个是1的二进制位来分析，比如末位。把两个奇数次出现的整数命名为A和B，如果末位是1，说明A和B转为二进制的末位不同，必定其中一个整数的末位是1，另一个整数的末位是0。
     *
     * 根据这个结论，我们可以把原数组按照二进制的末位不同，分成两部分，一部分的末位是1，一部分的末位是0。由于A和B的末位不同，所以A在其中一部分，B在其中一部分，绝不会出现A和B在同一部分，另一部分没有的情况。
     *
     * 这样一来就简单了，我们的问题又回归到了上一题的情况，按照原先的异或解法，从每一部分中找出唯一的奇数次整数即可。
     *
     * 假设数组长度是N，那么该解法的时间复杂度是O（N）。把数组分成两部分，并不需要借助额外存储空间，完全可以在按二进制位分组的同时来做异或运算，所以空间复杂度仍然是O（1）。
     * @return
     */
    public static String findTwoRepeatArrNum(){
        //先得到依次异或的结果 取任意为1的位置
        Integer tempResult = 0;
        for (Integer integer : repeatArrTwice) {
            tempResult ^= integer;
        }
        String binaryString = binNumString(tempResult);
        int index = binaryString.lastIndexOf("1");
        Integer result1 = 0;
        Integer result2 = 0;
        for (Integer num : repeatArrTwice) {
            String numString = binNumString(num);
            if(numString.charAt(index) != '1'){
                result1 ^= num;
            }else {
                result2 ^= num;
            }
        }
        return result1 + "," + result2;
    }
    public static String binNumString(Integer tempResult){
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(tempResult));
        if(binaryString.length() != 8){
            int length = 8 - binaryString.length();
            for (int i = 0; i < length; i++) {
                binaryString.insert(0, "0");
            }
        }
        return binaryString.toString();
    }

    public static void main(String[] args) {
        Integer missNum = findNotRepeatArrMissNum();
        System.out.println(missNum);
        Integer oneRepeatArrNum = findOneRepeatArrNum();
        System.out.println(oneRepeatArrNum);
        String twoRepeatArrNum = findTwoRepeatArrNum();
        System.out.println(twoRepeatArrNum);
    }
}
