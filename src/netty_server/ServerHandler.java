/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netty_server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author TAM
 */
class ServerHandler extends SimpleChannelInboundHandler<String> {

    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        System.out.println("client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equalsIgnoreCase("exit")) {
            channel.writeAndFlush("exit");
            channel.disconnect();
            System.out.println(ctx.channel().remoteAddress()+" disconnected");
        } else if (msg.equalsIgnoreCase("test")) {
            channel.writeAndFlush("echo");
        }else{
            System.out.println(ctx.channel().remoteAddress()+": "+msg);
        }
        
    }

}
