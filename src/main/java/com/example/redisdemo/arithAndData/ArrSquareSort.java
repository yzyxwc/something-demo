package com.example.redisdemo.arithAndData;

/**
 * 有序数组平方排序
 * @author wc
 * @date 2020/10/16 14:32
 */
public class ArrSquareSort {
    /**
     * 两边从中间走 创建了2个指针
     * @param arr
     * @return
     */
    public static int[] sortSquare(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        int i = end;
        int[] result = new int[arr.length];
        while (i >= 0){
            result[i--] = arr[start] * arr[start] >= arr[end] * arr[end] ? arr[start] * arr[start++] : arr[end] * arr[end--];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {-3,1,1,4};
        int[] ints = sortSquare(arr);
        for (int i : ints) {
            System.out.println(i);
        }

    }
}
