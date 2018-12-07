package com.zhaowb.netty.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IDEA
 * 正则表达式
 *
 * @author zwb
 * @create 2018/12/6 14:20
 */
public class RegexMatches {

    private static final String REGEX = "dog";
    private static  String INPUT = "The dog says meow. " +
            "All dogs say meow.";
    private static  String INPUT2 = "ooooofoooooooooooo";
    private static Pattern pattern;
    private static Matcher matcher;
    private static Matcher matcher2;
    private static String REPLACE = "cat";
    public static void main(String[] args) {
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(INPUT);
        matcher2 = pattern.matcher(INPUT2);

        System.out.println("REGEX = " + REGEX );
        System.out.println("INPUT = "+INPUT);
        System.out.println("INPUT2 = "+INPUT2);

//        matches 要求整个序列都匹配，而lookingAt 不要求,lookingAt 方法虽然不需要整句都匹配，但是需要从第一个字符开始匹配。
        System.out.println("matcher.lookingAt() "+matcher.lookingAt());

//        matcher.matches() 当且仅当整个区域序列时为真匹配这个匹配器的模式
        System.out.println("matcher.matches() "+matcher.matches());
        System.out.println("matcher2.lookingAt() "+ matcher2.lookingAt());

//        replaceFirst 替换首次匹配，replaceAll 替换所有匹配。
        INPUT = matcher.replaceFirst(REPLACE);
        System.out.println(INPUT);
        INPUT = matcher.replaceAll(REPLACE);
        System.out.println(INPUT);
    play();
    }

    public static void  play(){
       String REGEX = "a*b";
         String INPUT = "aabfooaabfooabfoobkkk";
        String REPLACE = "-";

        Pattern p = Pattern.compile(REGEX);
        // 获取 matcher 对象
        Matcher m = p.matcher(INPUT);
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            m.appendReplacement(sb,REPLACE);
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
    }


    public static void  add (double m ,int...ints){
        double sum = m;
        for (int n : ints){
            sum +=n;
        }

        System.out.println(sum);
    }
}
