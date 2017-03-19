/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netty_server;

import Connect.connectDatabase;
import Data.JSONData;
import Data.ListUsers;
import Data.MessageKey;
import Data.Data;
import Data.ListData;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        this.con = Netty_Server.con;
    }

    public boolean CompareUser(String username, String pass) {
        String sql = "SELECT *FROM tbl_User WHERE TrangthaiTK=1";
        boolean success=false;
        Vector user = new Vector();
        ArrayList<Data> arr=new ArrayList<Data>();
        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                arr.add(new Data(rs.getString("Username"),rs.getString("Pwd")));
            }

                System.out.println("listUser "+arr);
                for (Data item : arr) {
                    System.out.println(item.getUsername()+" "+item.getPassword());
                    if ((item.getUsername()).equals(username) && (item.getPassword()).equals(pass)) {
                        success=true;
                        break;
                        
                    }else{
                        success=false;
                    }
                }
        } catch (Exception e) {
        }
        return success;
    }

    private String Message(String json) {
        String Message = "";
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Message = object.getString("to");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Message;
    }

    private Data getUser(String mess) {
        Data data = null;
        if (mess != null && mess.length() > 0) {
           try {
                JSONArray jsonArray = new JSONArray(mess);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONObject js = object.getJSONObject("data");
                     data=new Data(js.getString("username"),js.getString("password"));
                     
                    System.out.println("data "+data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ": " + msg);
        switch (Message(msg)) {
            case MessageKey.EXIT:
                channel.writeAndFlush(MessageKey.EXIT);
                channel.disconnect();
                System.out.println(ctx.channel().remoteAddress() + ": " + MessageKey.DISCONNECTED);
                break;
            case MessageKey.TEST:
//                channel.writeAndFlush(getUser().toString());
                break;
            case MessageKey.LOGIN:
                System.out.println(MessageKey.LOGIN);
                Data data = getUser(msg);
                String username = data.getUsername();
                String pass = data.getPassword();
                System.out.println(username+" "+pass);
                if (CompareUser(username, pass)==true) {
                    channel.writeAndFlush(MessageKey.SUCCESS);
                }else{
                    channel.writeAndFlush(MessageKey.FAIL);
                }
                
                break;

            default:

                break;

        }
    }

}
