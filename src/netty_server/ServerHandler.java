/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netty_server;

import Connect.connectDatabase;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author TAM
 */
class ServerHandler extends SimpleChannelInboundHandler<String> {

    private Connection con;
    private ResultSet rs;
    private PreparedStatement stmt;
    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        System.out.println("client connected: " + ctx.channel().remoteAddress());
        try {
            con = connectDatabase.getConnection();
        } catch (Exception e) {
            System.out.println("can't connect database");
        }
    }

    public Vector getUser() {
        String sql = "SELECT *FROM tbl_User WHERE TrangthaiTK=1";
        Vector user = new Vector();
        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Vector data = new Vector();
                data.addElement(rs.getString("Username"));
                data.addElement(rs.getString("Pwd"));
                user.add(data);
            }
        } catch (Exception e) {
        }
        return user;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equalsIgnoreCase("exit")) {
            channel.writeAndFlush("exit");
            channel.disconnect();
            System.out.println(ctx.channel().remoteAddress() + " disconnected");
        } else if (msg.equalsIgnoreCase("test")) {
            channel.writeAndFlush(getUser().toString());
        } else {
            System.out.println(ctx.channel().remoteAddress() + ": " + msg);
        }

    }

}
