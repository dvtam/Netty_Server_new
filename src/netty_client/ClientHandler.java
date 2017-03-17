/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netty_client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author TAM
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {
     private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        System.out.println("connected to server: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equalsIgnoreCase("exit")) {
            channel.disconnect();
            System.out.println("disconnected");
        }else{
            System.out.println("Server: "+msg);
        }
        
    }
}
