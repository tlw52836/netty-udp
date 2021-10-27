package com.tlw;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;




@Slf4j
public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        //读取接受到的数据
        String s = packet.content().toString(CharsetUtil.UTF_8);
        log.debug("服务端收到数据:{}",s);
        ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("你好客户端",CharsetUtil.UTF_8),packet.sender()));
    }
}
