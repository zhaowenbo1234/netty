package com.zhaowb.netty.basewarehouseposition;

import com.alibaba.fastjson.JSONObject;
import com.zhaowb.netty.basewarehouseposition.entity.BaseWarehousePosition;
import com.zhaowb.netty.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/4 16:21
 */
public class MainTest {

    public static List<BaseWarehousePosition> baseWarehousePositionList = null;

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();

        String position = checkPosition(18, 77);
        Long endTime = System.currentTimeMillis();
        if (StringUtils.isNotBlank(position)) {
            System.out.println("仓位 ： " + position);
        } else {
            System.out.println("仓位 为空");
        }
        System.out.println("校验耗时：" + (endTime - startTime));

    }

    public static void init() {
        String jsonFile = FileUtils.ExcelToJSONFile("C:/Users/zwb/Desktop/003202001.xlsx");
        baseWarehousePositionList = JSONObject.parseArray(jsonFile, BaseWarehousePosition.class);
    }

    public static String checkPosition(double x, double y) {
        if (baseWarehousePositionList == null || baseWarehousePositionList.size() <= 0) {
            init();
        }
        for (BaseWarehousePosition baseWarehousePosition : baseWarehousePositionList) {
            if (baseWarehousePosition.getxAxis() <= x && baseWarehousePosition.getyAxis() <= y
                    && x < (baseWarehousePosition.getxAxis() + baseWarehousePosition.getWidth())
                    && y < (baseWarehousePosition.getyAxis() + baseWarehousePosition.getHeight())) {
                return baseWarehousePosition.getPositionName();
            }
        }
        return null;
    }
}
