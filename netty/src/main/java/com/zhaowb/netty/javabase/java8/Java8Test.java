package com.zhaowb.netty.javabase.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/12 11:29
 */
public class Java8Test {

    final static String salutation = "Hello! ";

    public static void main(String[] args) {

        Java8Test test = new Java8Test();
        List<String> names1 = new ArrayList<String>();
        names1.add("Google ");
        names1.add("Runoob ");
        names1.add("Taobao ");
        names1.add("Baidu ");
        names1.add("Sina ");


//        names1.forEach(s -> {
//            System.out.println(s);
//        });
//        System.out.println("排序后==========================");
//        Collections.sort(names1, (s2, s1) -> s1.compareTo(s2));
//        names1.forEach(s -> System.out.println(s));


        MathOperation addOperation = (int a, int b) -> a + b;
        System.out.println(test.operate(10, 5, addOperation));

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;
        System.out.println(test.operate(10, 5, subtraction));

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };
        System.out.println(test.operate(10, 5, multiplication));

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;
        System.out.println(test.operate(10, 5, division));

        GreetingService service = message -> System.out.println(message);
        service.sayMessage("hello");

        GreetingService service2 = (message) -> {
            System.out.println(message);
        };
        service2.sayMessage("world");

        GreetingService greetService1 = message ->
                System.out.println(salutation + message);
        greetService1.sayMessage("Runoob");


//        lambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
        int num = 1;
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);  // 输出结果为 3


        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("输出全部数据:");
        eval(list, n -> true);
        System.out.println();

        System.out.println("输出所有偶数:");
        eval(list, n -> n % 2 == 0);
        System.out.println();

        System.out.println("输出大于 3 的所有数字:");
        eval(list, n -> n > 3);
        System.out.println();


    }

    interface MathOperation {
        /**
         * 运算 a,b
         *
         * @param a
         * @param b
         * @return
         */
        int operation(int a, int b);
    }

    interface GreetingService {
        /**
         * 输出 message
         *
         * @param message
         */
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    public interface Converter<T1, T2> {
        void convert(int i);
    }

    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
//        for (Integer n : list) {
//            if (predicate.test(n)) {
//                System.out.print(n + " ");
//            }
//        }
        list.stream().filter(predicate).forEach(System.out::println);
    }
}
