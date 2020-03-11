package com.wh.com;

import java.util.List;

/**
 * @description:
 * @author: WangHu
 * @create: 2020-03-10 23:06
 **/

class ParentClass{
    static {
        System.out.println("Parent Static Block");
    }
    ParentClass(){
        System.out.println("In Parent Constructor");
    }

    ParentClass(int variable){
        System.out.println("In Parent Constructor："+variable);
    }
}
public class DemoClass extends ParentClass{
    public DemoClass(){
        super(34);
        System.out.println("In DemoClass Constructor");

    }

    public void printStatement(){
        System.out.println("Just One Line");
    }

    public static void main(String[] args) {
        new DemoClass();
    }
}
