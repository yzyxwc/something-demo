package com.example.redisdemo.arithAndData;

import java.util.Stack;

/**
 * 最小栈实现
 * @author wc
 * @date 2020/10/14 13:42
 */
public class MinStack {
    /**
     * 存储数据用的栈
     */
    private  Stack<Integer> cacheStack = new Stack<>();
    /**
     * 记录历史最小站的
     */
    private Stack<Integer> recordMinStack = new Stack<>();

    /**
     * 入栈
     * @param num 数
     * @return 加入是否成功
     */
    public  boolean push(Integer num){
        if(recordMinStack.empty() || recordMinStack.peek() >= num){
            recordMinStack.push(num);
        }
        cacheStack.push(num);
        return Boolean.TRUE;
    }
    public Integer pop(){
        if(cacheStack.empty()){
            return null;
        }
        Integer pop = cacheStack.pop();
        if(recordMinStack.peek().equals(pop)){
            recordMinStack.pop();
        }
        return pop;
    }
    public Integer getMin(){
        if(recordMinStack.empty()){
            return null;
        }
        return recordMinStack.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
//        minStack.push(7);
//        minStack.push(5);
//        minStack.push(4);
//        minStack.push(9);
//        minStack.push(7);
//        minStack.push(4);
//        Integer stackMin = minStack.getMin();
//        minStack.pop();
        Integer min = minStack.getMin();
    }
}
