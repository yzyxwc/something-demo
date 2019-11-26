package com.example.redisdemo.selocal;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> list1=new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List<Integer> list2=new ArrayList<>();
        list2.add(3);
        list2.add(4);
        list2.add(5);
        List<Integer> list=list1.stream().filter(t-> !list2.contains(t)).collect(Collectors.toList());
        System.out.println(list);
    }

    //准备筛选的集合大小小于 准备抽取的数量 就是个死循环 即prizeNum>user_size
    public static List winnerPrizeUser222(int prizeNum,List userSizeList){
        int user_size = userSizeList.size();
        Random random = new Random();

        boolean[]  bool = new boolean[user_size];
        int randInt = 0;
        List winner_users = new ArrayList();
        for(int i=0;i<prizeNum;i++){
            do {
                randInt  = random.nextInt(user_size);
            }while(bool[randInt]);
            bool[randInt] = true;
            winner_users.add(userSizeList.get(randInt));
        }
        return winner_users;
    }


    public static List winnerPrizeUser(int prizeNum,List userSizeList){
        int size = userSizeList.size();
        Random random = new Random();
        int a = random.nextInt(size);
	/*	System.out.println(a);
		System.out.println(userSizeList.get(a));
		System.out.println("======================================");*/

        boolean[]  bool = new boolean[size];
        int randInt = 0;
        List list2 = new ArrayList();
        for(int i=0;i<prizeNum;i++){
            do {
                randInt  = random.nextInt(size);
            }while(bool[randInt]);
            bool[randInt] = true;
//           System.out.println(userSizeList.get(randInt));
            list2.add(userSizeList.get(randInt));
        }
        Collections.sort(list2);
		/*System.out.println("=====================================");
		System.out.println(list2);*/
        return list2;
    }


    public static List getArraylist(){
        List list = new ArrayList();//下标以0开始
        list.add("AA");
        list.add("BB");
        list.add("DD");
        list.add("CC");
        list.add("GG");
        list.add("FF");
        list.add("QQ");
        list.add("WW");
        list.add("EE");
        list.add("RR");
        list.add("TT");
        list.add("YY");
        list.add("UU");
        list.add("II");
        list.add("OO");
        list.add("PP");
        list.add("SS");
        list.add("HH");
        list.add("JJ");
        list.add("KK");
        list.add("LL");
        list.add("ZZ");
        list.add("XX");
        list.add("VV");
        list.add("NN");
        list.add("MM");
        return list;
    }
}
