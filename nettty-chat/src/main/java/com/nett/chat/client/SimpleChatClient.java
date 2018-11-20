package com.nett.chat.client;

import com.nett.chat.handler.SimpleChatClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Janus on 2018/11/20.
 */
public class SimpleChatClient {

    public static void main(String[] args) throws Exception{
        new SimpleChatClient("localhost", 8080).run();
    }
    private final String host;
    private final int port;
    public SimpleChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            //这里使用connect instead of bind
            Channel channel = bootstrap.connect(host, port).sync().channel();
            //从控制台读取数据
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            //循环将读取的数据发送
            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
