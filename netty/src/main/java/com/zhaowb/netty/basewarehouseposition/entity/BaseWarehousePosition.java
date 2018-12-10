package com.zhaowb.netty.basewarehouseposition.entity;

/**
 * Created with IDEA
 * position_name	x_axis	y_axis	width	height	remark
 *
 * @author zwb
 * @create 2018/12/3 18:06
 */
public class BaseWarehousePosition{

    private String positionName;

    private double xAxis;

    private double yAxis;

    private double width;

    private double height;

    private String remark;

    public BaseWarehousePosition() {
    }

    public BaseWarehousePosition(String positionName, double xAxis, double yAxis, double width, double height, String remark) {
        this.positionName = positionName;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.width = width;
        this.height = height;
        this.remark = remark;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getxAxis() {
        return xAxis;
    }

    public void setxAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BaseWarehousePosition{" +
                "positionName='" + positionName + '\'' +
                ", xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", width=" + width +
                ", height=" + height +
                ", remark='" + remark + '\'' +
                '}';
    }
}
