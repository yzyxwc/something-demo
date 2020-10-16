package com.example.redisdemo.arithAndData;

/**
 * 求两个数的最大公约数
 * @author wc
 * @date 2020/10/15 14:05
 */
public class MaxCommonDivisor {


    /**
     * 辗转相除法求最大公约数
     * @param lawNumber 小得数
     * @param upperNumber 大一点的数
     * @return 最大公约数
     */
    public static Integer mod(int lawNumber,int upperNumber){
        if(upperNumber % lawNumber == 0 ){
            return lawNumber;
        }
        return mod(upperNumber%lawNumber , lawNumber);
    }

    /**
     * 更相减损数算最大公约数
     * @param numberA 数字一
     * @param numberB 数字二
     * @return 最大公约数
     */
    public static Integer count(int numberA,int numberB){
        if(numberA == numberB){
            return numberA;
        }
        if(numberA > numberB){
            return count(numberA - numberB,numberB);
        }else {
                return count(numberB - numberA,numberA);
        }
    }

    /**
     * 中和更相减损数和辗转相除法的两个优势
     * @param numberA 数一
     * @param numberB 数二
     * @return 最大公约数
     */
    public static Integer prefect(int numberA,int numberB){
        if(numberA == numberB){
            return numberA;
        }
        if(numberA % 2 == 0 &&  numberB % 2 == 0){
            return 2 * prefect(numberA >> 1,numberB >> 1);
        }
        if(numberA % 2 == 0){
            numberA = numberA >> 1;
        }
        if(numberB % 2 == 0){
            numberB = numberB >> 1;
        }
        if(numberA == numberB){
            return numberA;
        }
        if(numberA > numberB){
            return prefect(numberA - numberB,numberB);
        }else {
            return prefect(numberB - numberA,numberA);
        }
    }

    public static void main(String[] args) {
        Integer mod = prefect(8, 10);
        System.out.println(mod);
    }
}
