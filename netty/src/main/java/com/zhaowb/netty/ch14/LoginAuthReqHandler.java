package com.zhaowb.netty.ch14;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 握手认证客户端，用于在通道激活时发起握手请求
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("LoginAuthReqHandler channelActive");

        //当客户端跟服务端TCP三次握手成功之后，由客户端构造握手请求消息发送给服务端
        ctx.writeAndFlush(buildLoginReq());

        LOGGER.info("LoginAuthReqHandler 发送登陆请求" + buildLoginReq().toString());
    }

    // 握手请求发送之后，按照协议规范，服务端需要返回握手应答消息。
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 如果是握手应答消息，需要判断是否认证成功
        //对握手应答消息进行处理，首先判断消息是否是握手应答消息，
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                // 如果是握手应答消息，则对应答结果进行判断，如果非0，说明认证失败，关闭链路，重新发起连接。
                // 握手失败，关闭链路
                ctx.close();
            } else {
                LOGGER.info("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            // 如果不是，直接透传给后面的ChannelHandler进行处理；
            //调用下一个channel链..
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 构建登录请求
     */
    private NettyMessage buildLoginReq() {
        // 由于采用IP白名单认证机制，因此，不需要携带消息体，消息体为空，消息类型为3：握手请求消息。
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }
}
