package com.zhaowb.netty.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created with IDEA
 *
 *
 * position_name	x_axis	y_axis	width	height	remark
 * A0111	0	0	9	1	A区仓位
 * A0112	0	1	9	1	A区仓位
 * A0113	15	0	6	2	A区仓位
 * A0101	0	2	3	3	A区仓位
 * A0102	3	2	3	3	A区仓位
 * A0103	6	2	3	3	A区仓位
 * A0104	15	2	3	3	A区仓位
 * A0105	18	2	3	3	A区仓位
 *
 * @author zwb
 * @create 2018/10/23 10:42
 */
public class FileUtils {

    private static String[] name = {"positionName","xAxis","yAxis","width","height","remark"};
    private final static Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static void ExcelToJSONFile(String fileName) {

        log.info("文件全称 = {}",fileName);

        JSONArray jsonArray = new JSONArray();
        String xls = "xls";
        String xlsx = "xlsx";

        try {
           File file = new File(fileName);
           if (file.exists() && file.isFile()){
               // . 为特殊字符需要转义\\.
               String[] split = file.getName().split("\\.");
               Workbook workbook;
                log.info("文件后缀名 = {}",split[1]);
               // 根据文件后缀（xls/xlsx）进行判断
               if (xls.equals(split[1])){
                   FileInputStream fis = new FileInputStream(file);
                   workbook = new HSSFWorkbook(fis);
               } else if (xlsx.equals(split[1])){
                    workbook = new XSSFWorkbook(file);
               } else {
                   log.info("文件类型错误");
                   throw  new RuntimeException("文件类型错误");
               }

               Sheet sheet = workbook.getSheetAt(0);
               //第一行是列名，不读
               int firstRowIndex = sheet.getFirstRowNum() + 1;
               int lastRowNum = sheet.getLastRowNum();
               // 行、列都从0 开始计数的，
               log.info("第一行firstRowIndex = {} ，最后一行 = {} ",firstRowIndex,lastRowNum);
                // 遍历行
               for (int rIndex = firstRowIndex;rIndex <= lastRowNum;rIndex++){
                    Row row = sheet.getRow(rIndex);
                    if (row != null){
                        int firstCellNum = row.getFirstCellNum();
                        int lastCellNum = row.getLastCellNum();
                        JSONObject object = new JSONObject();
                        for (int cIndex = firstCellNum;cIndex <= lastCellNum; cIndex++){
                            Cell cell = row.getCell(cIndex);
                            if (cell != null){
                                object.put(name[cIndex],cell.toString());
                            }
                        }
                        jsonArray.add(object);
                    }
               }
           }else {
               log.info("找不到指定文件 = {}",fileName);
           }
           log.info(jsonArray.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ExcelToJSONFile("C:/Users/zwb/Desktop/Test.xlsx");
    }
}