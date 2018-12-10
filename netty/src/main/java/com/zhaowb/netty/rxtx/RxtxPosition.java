package com.zhaowb.netty.rxtx;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/10/26 16:15
 */
public class RxtxPosition {

    private static CommPortIdentifier portIdentifier;
    private static SerialPort serialPort;

    private static InputStream inputStream;
    public static void main(String[] args) {

    }

    public static void  init(){
        try {
            /**
             * 串口号
             */
            portIdentifier = CommPortIdentifier.getPortIdentifier("COM4");
            //使用者  和 最大响应时长(ms)
            serialPort = (SerialPort) portIdentifier.open("test",5000);

            //设置串口参数
            serialPort.setSerialPortParams(115200,  //波特率
                    SerialPort.DATABITS_8,          //数据位
                    SerialPort.STOPBITS_1,          //停止位
                    SerialPort.PARITY_NONE);        //校验位
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            inputStream=serialPort.getInputStream();

            byte[] b = new byte[1024];
            while (true){
                if (inputStream.available() > 0){
                    int num = inputStream.read(b);
                }
            }
//            String s = new String(b);
//            System.out.println(b);

        } catch (Exception e){

        }
    }
}
