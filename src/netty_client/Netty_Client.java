/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netty_client;

import Config.Config;
import Data.MessageKey;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author TAM
 */
public class Netty_Client {
    
    public static Channel channel;
    public static BufferedReader reader;
    
    public static boolean EPOLL = Epoll.isAvailable();
    InputStream inputsteam;
    
    public Netty_Client() throws Exception {
        EventLoopGroup eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            channel = new Bootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8)).addLast(new StringEncoder(StandardCharsets.UTF_8)).addLast(new ClientHandler());
                        }
                    }).connect(Config.HOST, Config.PORT).sync().channel();
            channel.writeAndFlush("CLIENT");
            reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) {
                switch (line) {
                    case MessageKey.NULL:
                        continue;
                }
                channel.writeAndFlush(line);
                if (line.startsWith(MessageKey.EXIT)) {
                    System.out.println("waiting for disconnect...");
                    channel.closeFuture().syncUninterruptibly();
                    break;
                }
            }
            
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
    
    public static void sendRequest(String mss) {
        channel.writeAndFlush(mss);
        
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("netty_client.Netty_Client.main()");
        new Netty_Client();
    }
}
