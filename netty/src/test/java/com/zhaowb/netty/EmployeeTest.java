package com.zhaowb.netty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 定义一个Employee类,属性:name:String,age:int,salary:double
 * 把若干Employee对象放在List中,排序并遍历输出,排序规则:salary高的在前面,salary相同时age大的在前面,age也相同时按照name升序排列
 * 把若干Employee对象放在Set中并遍历,要求没有重复元素
 */
public class EmployeeTest {


    public static void main(String[] args) {

        Employee e1 = new Employee("Alis",20,3000);
        Employee e2 = new Employee("Tom",22,3000);
        Employee e3 = new Employee("Jack",22,3000);
        Employee e4 = new Employee("Lily",21,3500);
        Employee e5 = new Employee("Mike",20,2900);
        Employee e6 = new Employee("Bobo",23,4000);

        List<Employee> list = new ArrayList<Employee>();
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);
        list.add(e6);
//        for (Employee e : list){
//            System.out.println(e);
//        }
        list.forEach((employee -> System.out.println(employee)));

        Set<Employee> set = new HashSet();

        System.out.println("排序后。。。。。。。。。");
        for (int i = 1; i < list.size();i ++){
            for (int j = 0  ;j < list.size() - i;j++){
                Employee em1 = list.get(j);
                Employee em2 = list.get(j + 1);

                if (em2.getSalary() > em1.getSalary()){
                    list.set(j,em2);
                    list.set(j + 1,em1);
                } else if (em2.getAge() > em1.getAge()){
                    list.set(j,em2);
                    list.set(j + 1,em1);
                } else if(em2.getName().compareTo(em1.getName()) > 0 ){
                    list.set(j,em2);
                    list.set(j + 1,em1);
                }
            }
        }

        list.forEach(employee -> System.out.println(employee));
    }
}