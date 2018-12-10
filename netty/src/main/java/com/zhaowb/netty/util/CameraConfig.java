/**
 * <p>Title: CameraConfig.java</p>
 * <p>Copyright: Copyright (c) 2017 - </p>
 */
package com.zhaowb.netty.util;

import java.io.Serializable;
import java.util.List;

/**
 * @author MaYongLong
 * @date 2018-06-04 13:27
 * -------------------------------------------------------------------------
 * Modified Date		Modified By			Why & What's modified
 * -------------------------------------------------------------------------
 */
public class CameraConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1415088208456408324L;

	private String appKey;

    private String appSecret;
    //获取accessToken
    private String accessTokenUrl;
    //获取设备列表
    private String deviceListUrl;
    //添加设备
    private String deviceAddUrl;
    //设备抓拍图片
    private String deviceCaptureUrl;
    //设备布撤防
    private String defenceSetUrl;
    //按照设备获取报警消息
    private String alarmDeviceListUrl;
    //取所有设备的报警消息
    private String alarmListUrl;
    //创建一个消费者
    private String consumerCreate;
    //消费者消费消息
    private String consumerMessages;
    //消费者提交偏移量
    private String consumerOffsets;
    //图片下载GET
    private String picDownload;
    //NVR IP
    private String nvrIp;

    public String getNvrIp() {
        return nvrIp;
    }

    public void setNvrIp(String nvrIp) {
        this.nvrIp = nvrIp;
    }

    private List<CameraInfoConfig> cameraInfoList;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getDeviceListUrl() {
        return deviceListUrl;
    }

    public void setDeviceListUrl(String deviceListUrl) {
        this.deviceListUrl = deviceListUrl;
    }

    public String getDeviceAddUrl() {
        return deviceAddUrl;
    }

    public void setDeviceAddUrl(String deviceAddUrl) {
        this.deviceAddUrl = deviceAddUrl;
    }

    public String getDeviceCaptureUrl() {
        return deviceCaptureUrl;
    }

    public void setDeviceCaptureUrl(String deviceCaptureUrl) {
        this.deviceCaptureUrl = deviceCaptureUrl;
    }

    public String getDefenceSetUrl() {
        return defenceSetUrl;
    }

    public void setDefenceSetUrl(String defenceSetUrl) {
        this.defenceSetUrl = defenceSetUrl;
    }

    public String getAlarmDeviceListUrl() {
        return alarmDeviceListUrl;
    }

    public void setAlarmDeviceListUrl(String alarmDeviceListUrl) {
        this.alarmDeviceListUrl = alarmDeviceListUrl;
    }
    
    public String getAlarmListUrl() {
		return alarmListUrl;
	}

	public void setAlarmListUrl(String alarmListUrl) {
		this.alarmListUrl = alarmListUrl;
	}

    public String getConsumerCreate() {
        return consumerCreate;
    }

    public void setConsumerCreate(String consumerCreate) {
        this.consumerCreate = consumerCreate;
    }

    public String getConsumerMessages() {
        return consumerMessages;
    }

    public void setConsumerMessages(String consumerMessages) {
        this.consumerMessages = consumerMessages;
    }

    public String getConsumerOffsets() {
        return consumerOffsets;
    }

    public void setConsumerOffsets(String consumerOffsets) {
        this.consumerOffsets = consumerOffsets;
    }

    public String getPicDownload() {
        return picDownload;
    }

    public void setPicDownload(String picDownload) {
        this.picDownload = picDownload;
    }

    public List<CameraInfoConfig> getCameraInfoList() {
        return cameraInfoList;
    }

    public void setCameraInfoList(List<CameraInfoConfig> cameraInfoList) {
        this.cameraInfoList = cameraInfoList;
    }

	@Override
	public String toString() {
		return "CameraConfig [appKey=" + appKey + ", appSecret=" + appSecret + ", accessTokenUrl=" + accessTokenUrl
				+ ", deviceListUrl=" + deviceListUrl + ", deviceAddUrl=" + deviceAddUrl + ", deviceCaptureUrl="
				+ deviceCaptureUrl + ", defenceSetUrl=" + defenceSetUrl + ", alarmDeviceListUrl=" + alarmDeviceListUrl
				+ ", alarmListUrl=" + alarmListUrl + ", consumerCreate=" + consumerCreate + ", consumerMessages="
				+ consumerMessages + ", consumerOffsets=" + consumerOffsets + ", picDownload=" + picDownload
				+ ", nvrIp=" + nvrIp + ", cameraInfoList=" + cameraInfoList + ", getNvrIp()=" + getNvrIp()
				+ ", getAppKey()=" + getAppKey() + ", getAppSecret()=" + getAppSecret() + ", getAccessTokenUrl()="
				+ getAccessTokenUrl() + ", getDeviceListUrl()=" + getDeviceListUrl() + ", getDeviceAddUrl()="
				+ getDeviceAddUrl() + ", getDeviceCaptureUrl()=" + getDeviceCaptureUrl() + ", getDefenceSetUrl()="
				+ getDefenceSetUrl() + ", getAlarmDeviceListUrl()=" + getAlarmDeviceListUrl() + ", getAlarmListUrl()="
				+ getAlarmListUrl() + ", getConsumerCreate()=" + getConsumerCreate() + ", getConsumerMessages()="
				+ getConsumerMessages() + ", getConsumerOffsets()=" + getConsumerOffsets() + ", getPicDownload()="
				+ getPicDownload() + ", getCameraInfoList()=" + getCameraInfoList() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
}
