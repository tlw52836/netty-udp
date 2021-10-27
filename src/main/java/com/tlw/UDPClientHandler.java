package com.tlw;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;


@Slf4j
public class UDPClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //ByteBuf buf = Unpooled.copiedBuffer("我是客户端", CharsetUtil.UTF_8);
//        ByteBuf b1 = buf.slice(0,6);
//        ByteBuf b2 = buf.slice(6, 9);
//        log.debug("b1:{}",b1.toString(CharsetUtil.UTF_8));
//        log.debug("b2:{}",b2.toString(CharsetUtil.UTF_8));
          ByteBuf buf = ctx.alloc().DEFAULT.buffer();
          buf.writeInt(15);
          buf.writeBytes("我是".getBytes());


        ctx.writeAndFlush(new DatagramPacket(buf,new InetSocketAddress("192.168.192.1",8089)));



    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        //读取接受到的数据

        String s = packet.content().toString(CharsetUtil.UTF_8);

        log.debug("客户端收到数据：{}",s);
        //ctx.channel().close();
    }
}
