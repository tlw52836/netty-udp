package com.tlw;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;




@Slf4j
public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        //读取接受到的数据

        ByteBuf buf = packet.content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        log.debug("msg:{}",req.toString());
    }
}
