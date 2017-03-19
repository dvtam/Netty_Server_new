/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netty_server;

import Config.Config;
import Connect.connectDatabase;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author TAM
 */
public class Netty_Server {

    public static Connection con;
    private ResultSet rs;
    private PreparedStatement stmt;
    public final boolean EPOLL = Epoll.isAvailable();
    private EventLoopGroup eventLoopGroup;

    private void connect() {
        try {
            con = connectDatabase.getConnection();
        } catch (Exception e) {
            System.out.println("can't connect database");
        }
    }

    public Netty_Server(Connection con) {
        this.con = con;
    }

    /**
     * @param args the command line arguments
     */
    public Netty_Server() throws Exception {
        connect();
        eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8)).addLast(new StringEncoder(StandardCharsets.UTF_8)).addLast(new ServerHandler());
                        }
                    }).bind(Config.PORT).sync().channel().closeFuture().syncUninterruptibly();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Server running...\nPort: " + Config.PORT);
        new Netty_Server();
    }
    
}
