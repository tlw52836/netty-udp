package com.tlw;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UDPClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioDatagramChannel.class);
        b.option(ChannelOption.SO_BROADCAST,true);
        b.bind("192.168.192.1",8080);
    }
}
