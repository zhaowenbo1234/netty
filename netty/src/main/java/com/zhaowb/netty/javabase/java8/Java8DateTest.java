package com.zhaowb.netty.javabase.java8;


import java.time.*;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/12 16:48
 */
public class Java8DateTest {
    public static void main(String[] args) {
        LocalDateTime time =  LocalDateTime.now();

        System.out.println(time);
        LocalDate date = time.toLocalDate();

        System.out.println(date);

        System.out.println(date.getMonth());
        System.out.println(time.getMonth());

        LocalDateTime date2 = time.withDayOfMonth(10).withYear(2012);
        System.out.println(date2);

        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println(date3);

        // 22 小时 15 分钟
        LocalTime date4 = LocalTime.of(22, 15);
        System.out.println("date4: " + date4);

        // 解析字符串
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5: " + date5);


        ZonedDateTime date1 = ZonedDateTime.parse("2018-12-03T10:15:30+05:30[Asia/Shanghai]");
        System.out.println("date1: " + date1);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("当期时区: " + currentZone);
    }
}
