package com.tlw;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UDPServer {
    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioDatagramChannel.class);
            //支持广播
            b.option(ChannelOption.SO_BROADCAST,true);
            b.handler(new ChannelInitializer<NioDatagramChannel>() {
                @Override
                protected void initChannel(NioDatagramChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new UDPServerHandler());
                }
            });
            ChannelFuture channelFuture = b.bind("192.168.192.1",8089).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        }finally {
            group.shutdownGracefully();
        }
    }
}
