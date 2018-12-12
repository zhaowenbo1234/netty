package com.zhaowb.netty.javabase.array;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/12 9:43
 */
public class CollectionEqual {
    public static void main(String[] args) throws Exception {
        String[] coins = {"Penny", "nickel", "dime", "Quarter", "dollar"};
        Set<String> set = new TreeSet<String>();
        for (int i = 0; i < coins.length; i++) {
            set.add(coins[i]);
        }
        System.out.println(Collections.min(set));
        String cc = Collections.min(set, String.CASE_INSENSITIVE_ORDER);
        System.out.println(cc);
        System.out.println(Collections.min(set, String.CASE_INSENSITIVE_ORDER));
        for (int i = 0; i <= 10; i++) {
            System.out.print("-");
        }
        System.out.println("");
        System.out.println(Collections.max(set));
        System.out.println(Collections.max(set, String.CASE_INSENSITIVE_ORDER));

        HashMap<String, String> map =
                new HashMap<String, String>();
        map.put("1", "1st");
        map.put("2", "2nd");
        map.put("3", "3rd");

        Collection<String> values = map.values();

        Iterator it = values.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }


//        // List集合的遍历
//        listTest();
//        // Set集合的遍历
//        setTest();

        displayHeader();


    }

    private static void setTest() {
        Set<String> set = new HashSet<String>();
        set.add("JAVA");
        set.add("C");
        set.add("C++");
        // 重复数据添加失败
        set.add("JAVA");
        set.add("JAVASCRIPT");

        // 使用iterator遍历set集合
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String value = it.next();
            System.out.println(value);
        }


        // 使用增强for循环遍历set集合
        for (String s : set) {
            System.out.println(s);
        }
    }

    // 遍历list集合
    private static void listTest() {
        List<String> list = new ArrayList<String>();
        list.add("菜");
        list.add("鸟");
        list.add("教");
        list.add("程");
        list.add("www.runoob.com");

        // 使用iterator遍历
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String value = it.next();
            System.out.println(value);
        }

        // 使用传统for循环进行遍历
        for (int i = 0, size = list.size(); i < size; i++) {
            String value = list.get(i);
            System.out.println(value);
        }

        Collections.reverse(list);

        // 使用增强for循环进行遍历
        for (String value : list) {
            System.out.println(value);
        }
    }

    public static void displayHeader() throws Exception {
        URL url = new URL("http://www.runoob.com");
        URLConnection conn = url.openConnection();

        Map headers = conn.getHeaderFields();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            String val = conn.getHeaderField(key);
            System.out.println(key + "    " + val);
        }
//        System.out.println(conn.getLastModified());

    }
}
