package com.gmail.pavelchupin.net_storage.server.netty;

import com.gmail.pavelchupin.net_storage.server.netty.handler.NetStorageReadMainHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class NetStorageServer {
    private static final String SERVER_PORT = "server.port";

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new FileInputStream("D:/GitRepository/LearnGeekBrains/Курс четверть 2 - Разработка сетевого хранилища на Java/HomeWork/net_storage_server/src/main/resources/conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectDecoder(1024 * 1024 * 100, ClassResolvers.cacheDisabled(null)),
                            new ObjectEncoder(),
                            new NetStorageReadMainHandler()/*, new NetStorageReadHandler()*/);
                }
            });
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(Integer.parseInt(prop.getProperty(SERVER_PORT))).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
