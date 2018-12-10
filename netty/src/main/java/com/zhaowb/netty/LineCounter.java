package com.zhaowb.netty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 *
 * @ClassName: LineCounter
 * @Description: 统计项目下所有去除空行后的行号, 在main函数中传入项目的绝对路径（不能传入一个文件，要传入文件夹）。
 * @author: zwb
 * @create: 2018/9/10 10:01
 */

public class LineCounter {
    List<File> list = new ArrayList<File>();
    int linenumber = 0;

    FileReader fr = null;
    BufferedReader br = null;

    public void counter(String path) {
        // String path = System.getProperty("user.dir");
        System.out.println("项目路径" + path);
        File file = new File(path);
        File files[] = null;
        files = file.listFiles();
        addFile(files);
        isDirectory(files);
        readLinePerFile();
        System.out.println("Totle:" + linenumber + "行");
    }

    /**
     * 判断是否是目录
     *
     * @param files
     */
    public void isDirectory(File[] files) {
        for (File s : files) {
            if (s.isDirectory()) {
                File file[] = s.listFiles();
                addFile(file);
                isDirectory(file);
                continue;
            }
        }
    }

    /**
     * 将src下所有文件组织成list
     *
     * @param file
     */
    public void addFile(File file[]) {
        for (int index = 0; index < file.length; index++) {
            list.add(file[index]);
            // System.out.println(list.size());
        }
    }

    /**
     * 读取非空白行
     */
    public void readLinePerFile() {
        try {
            for (File s : list) {
                int yuan = linenumber;
                if (s.isDirectory()) {
                    continue;
                }
                if (s.getName().lastIndexOf(".java") > 0 || s.getName().lastIndexOf(".jsp") > 0 || s.getName().lastIndexOf(".js") > 0 || s.getName().lastIndexOf(".html") > 0 || s.getName().lastIndexOf(".css") > 0 || s.getName().lastIndexOf(".xml") > 0) {

                } else {
                    continue;
                }
                fr = new FileReader(s);
                br = new BufferedReader(fr);
                String i = "";
                while ((i = br.readLine()) != null) {
                    if (isBlankLine(i)) {
                        linenumber++;
                    }
                }
                System.out.print(s.getName());
                System.out.println("\t\t有" + (linenumber - yuan) + "行");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 是否是空行
     */
    public boolean isBlankLine(String i) {
        if (i.trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String args[]) {
        LineCounter lc = new LineCounter();
        //$NON-NLS-1$
        lc.counter("D:\\iotshudt\\trunk\\iot-env");

    }
}