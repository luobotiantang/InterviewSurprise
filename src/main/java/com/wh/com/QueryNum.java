package com.wh.com;

import java.util.LinkedList;

/**
 * @description: 从1000w中取出最小的10个数按照顺序打印出来
 * @author: WangHu
 * @create: 2020-03-10 11:17
 **/
public class QueryNum {

    public static void main(String[] args) {
        int n = 10000000;
        int m = 10;
        int[] arr =  new int[n];
        for (int i = 0; i <n ; i++) {
            arr[i] = i+1;
        }
        print(arr,n);
        for (int i = 0; i <m ; i++) {
            System.out.println(arr[i]);
        }

        LinkedList<Object> objectLinkedList = new LinkedList<>();
        for (Object o:objectLinkedList
             ) {
            objectLinkedList.add(0,o);
        }
    }

    public static void print(int[] arr,int m){
        createSmallHeap(arr,m);
        for (int i = m; i <arr.length ; i++) {
            if (arr[i]<arr[0]){
                arr[0] = arr[i];
                smallHeap(arr,m,0);
            }
        }
    }
    public static void createSmallHeap(int[] arr,int m){
        for (int i = m/2-1; i >=0 ; i--) {
            smallHeap(arr,m,i);
        }
    }

    //把最小的数放到堆顶
    public static void smallHeap(int[] arr,int m,int i){
        int temp = arr[i];
        for (int j = 2*i+1; j <m ; j=2*j+1) {
            if (j+1<m && arr[j+1]<arr[j]){
                j++;
            }
            if (temp<arr[j]){
                arr[i]=arr[j];
                i=j;
            }
        }
        arr[i]=temp;
    }
}
