/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netty_server;

import Connect.connectDatabase;
import Data.ChitietChuongtrinh;
import Data.Chuongtrinh;
import Data.JSONData;
import Data.ListUsers;
import Data.MessageKey;
import Data.Data;
import Data.ListData;
import Data.NoidungChuongtrinh;
import Data.UserInfos;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
        String sql = "SELECT * FROM tbl_Nguoidung";
        boolean success = false;
        Vector user = new Vector();
        ArrayList<Data> arr = new ArrayList<Data>();
        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                arr.add(new Data(rs.getString("tentaikhoan"), rs.getString("matkhau")));
            }

            System.out.println("listUser " + arr);
            for (Data item : arr) {
                System.out.println(item.getUsername() + " " + item.getPassword());
                if ((item.getUsername()).equals(username) && (item.getPassword()).equals(pass)) {
                    success = true;
                    break;

                } else {
                    success = false;
                }
            }
        } catch (Exception e) {
        }
        return success;
    }

    private JSONArray getUserInfos(String tentaikhoan) {
        boolean active = false;
        JSONArray userinfos = new JSONArray();
        String sql = "SELECT * FROM tbl_Nguoidung WHERE tentaikhoan=?";
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        UserInfos userinfo = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tentaikhoan);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("hoten") != "") {
                    userinfo = new UserInfos(rs.getInt("ID"), rs.getString("hoten"), rs.getString("ngaysinh"), rs.getString("gioitinh"), rs.getString("diachi"), rs.getString("dienthoai"), rs.getString("email"), rs.getString("tentaikhoan"), rs.getString("matkhau"), rs.getInt("idLoai"));
                    active = true;
                } else {
                    active = false;
                }

            }
            if (active = true) {
                data.put("id", userinfo.getId());
                data.put("hoten", userinfo.getHoten());
                data.put("ngaysinh", userinfo.getNgaysinh());
                data.put("gioitinh", userinfo.getGioitinh());
                data.put("diachi", userinfo.getDiachi());
                data.put("dienthoai", userinfo.getDienthoai());
                data.put("email", userinfo.getEmail());
                data.put("tentaikhoan", userinfo.getTentaikhoan());
                data.put("matkhau", userinfo.getMatkhau());
                data.put("idloai", userinfo.getIdloai());
                jsonObject.put("to", MessageKey.SUCCESS);
                jsonObject.put("data", data);
                System.out.println(jsonObject.toString());
                userinfos.put(jsonObject);
            } else {
                data.put("id", userinfo.getId());
                data.put("email", userinfo.getEmail());
                data.put("tentaikhoan", userinfo.getTentaikhoan());
                data.put("matkhau", userinfo.getMatkhau());
                data.put("idloai", userinfo.getIdloai());
                jsonObject.put("to", MessageKey.SUCCESS);
                jsonObject.put("data", data);
                System.out.println(jsonObject.toString());
                userinfos.put(jsonObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userinfos;
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
                    data = new Data(js.getString("username"), js.getString("password"));

                    System.out.println("data " + data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private JSONArray getTyeUser() {
        String sql = "SELECT * FROM tbl_Loainguoidung";
        JSONArray arr = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonarr = new JSONArray();
        JSONObject data = null;

        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> tenloai = new ArrayList<>();
            jsonObject.put("to", MessageKey.LOADTYPEUSER);
            while (rs.next()) {
                data = new JSONObject();
                data.put("id", rs.getString("ID"));
                data.put("tenloai", rs.getString("tenloai"));
                jsonarr.put(data);
            }
            jsonObject.put("data", jsonarr);
            arr.put(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    private boolean signUp(String json) throws SQLException {
        boolean success = false;
        String username = "";
        String password = "";
        String email = "";
        int idloaind = 0;
        String sql = "INSERT INTO tbl_Nguoidung(email,tentaikhoan,matkhau,idLoai) VALUES(?,?,?,?)";
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONObject js = object.getJSONObject("data");
                    username = js.getString("username");
                    password = js.getString("password");
                    email = js.getString("email");
                    idloaind = js.getInt("idloainguoidung");
                }
                stmt = con.prepareStatement(sql);
                // set gia tri bien
                stmt.setString(1, email);
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.setInt(4, idloaind);
                stmt.executeUpdate();
                stmt.close();
                success = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    private JSONArray toJSON(String mess) {
        JSONArray gettype = new JSONArray();
        JSONObject oject = new JSONObject();
        try {
            oject.put("to", mess);
            gettype.put(oject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gettype;
    }

    private boolean updateUserpass(String json) {
        boolean success = false;
        String sql = "UPDATE tbl_Nguoidung set matkhau=? where ID=?";
        UserInfos userInfos = null;
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONObject rs = object.getJSONObject("data");
                    try {
                        userInfos = new UserInfos(rs.getInt("id"),
                                rs.getString("hoten"),
                                rs.getString("ngaysinh"),
                                rs.getString("gioitinh"),
                                rs.getString("diachi"),
                                rs.getString("dienthoai"),
                                rs.getString("email"),
                                rs.getString("tentaikhoan"),
                                rs.getString("matkhau"),
                                rs.getInt("idloai"));

                    } catch (JSONException e) {
                    }
                }
                stmt = con.prepareStatement(sql);
                // set gia tri bien
                stmt.setString(1, userInfos.getMatkhau());
                stmt.setInt(2, userInfos.getId());
                stmt.executeUpdate();
                stmt.close();
                success = true;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

    private boolean updateUserInfor(String json) {
        boolean success = false;
        String sql = "UPDATE tbl_Nguoidung set hoten=?, gioitinh=?, diachi=?, email=?, dienthoai=?, ngaysinh=?, IDLoai=? where ID=?";
        UserInfos userInfos = null;
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONObject rs = object.getJSONObject("data");
                    try {
                        userInfos = new UserInfos(rs.getInt("id"),
                                rs.getString("hoten"),
                                rs.getString("ngaysinh"),
                                rs.getString("gioitinh"),
                                rs.getString("diachi"),
                                rs.getString("dienthoai"),
                                rs.getString("email"),
                                rs.getString("tentaikhoan"),
                                rs.getString("matkhau"),
                                rs.getInt("idloai"));

                    } catch (JSONException e) {
                    }
                }
                stmt = con.prepareStatement(sql);
                // set gia tri bien
                stmt.setString(1, userInfos.getHoten());
                stmt.setString(2, userInfos.getGioitinh());
                stmt.setString(3, userInfos.getDiachi());
                stmt.setString(4, userInfos.getEmail());
                stmt.setString(5, userInfos.getDienthoai());
                stmt.setString(6, userInfos.getNgaysinh());
                stmt.setInt(7, userInfos.getIdloai());
                stmt.setInt(8, userInfos.getId());
                stmt.executeUpdate();
                stmt.close();
                success = true;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                success = false;
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

    private int getIDfromdate(String date) {
        int idchuongtrinh = 0;
        String sql = "select ID from tbl_Chuongtrinh where ngaybatdau=?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, date);
            rs = stmt.executeQuery();
            while (rs.next()) {
                idchuongtrinh = rs.getInt("ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idchuongtrinh;
    }

    private JSONArray getChuongtrinh(int id) {
        String getchuongtrinh = "SELECT * FROM tbl_Chuongtrinh where ID=?";
        String getnoidungct = "select tbl_Chuongtrinh.ID as idct,tbl_Noidungchuongtrinh.ID as id,tbl_Noidungchuongtrinh.tieudeamnhac as tieudeamnhac,tbl_Noidungchuongtrinh.noidungamnhac as noidungamnhac,tbl_Noidungchuongtrinh.tieudevanhoc as tieudevanhoc,tbl_Noidungchuongtrinh.noidungvanhoc as noidungvanhoc from tbl_Chuongtrinh inner join tbl_Noidungchuongtrinh on tbl_Chuongtrinh.ID=tbl_Noidungchuongtrinh.IDChuongtrinh where tbl_Chuongtrinh.ID=?";
        String getctct = "select tbl_Chuongtrinh.ID as idct,tbl_Chitietchuongtrinh.ID as id,tbl_Chitietchuongtrinh.thoigian as thoigian,tbl_Chitietchuongtrinh.noidung as noidung,tbl_Chitietchuongtrinh.thu as thu ,tbl_Chitietchuongtrinh.IDChuongtrinh as idctr from tbl_Chuongtrinh inner join tbl_Chitietchuongtrinh on tbl_Chuongtrinh.ID=tbl_Chitietchuongtrinh.IDChuongtrinh where tbl_Chitietchuongtrinh.IDChuongtrinh=?";
        Chuongtrinh chuongtrinh = null;
        ChitietChuongtrinh chitietChuongtrinh = null;
        NoidungChuongtrinh noidungChuongtrinh = null;
        JSONArray listchuongtrinh = new JSONArray();
        JSONArray arrlistctct = new JSONArray();
        JSONArray arrlistndct = new JSONArray();
        JSONObject ct = new JSONObject();
        ArrayList<ChitietChuongtrinh> listctct = new ArrayList<ChitietChuongtrinh>();
        ArrayList<NoidungChuongtrinh> listndct = new ArrayList<NoidungChuongtrinh>();
        int idchuongtrinh = 1;
        try {
            stmt = con.prepareStatement(getctct);
            if (id != 0) {
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    idchuongtrinh = rs.getInt("idct");
                    chitietChuongtrinh = new ChitietChuongtrinh(rs.getInt("id"), rs.getString("thoigian"), rs.getString("thu"), rs.getString("noidung"), rs.getInt("idctr"));
                    listctct.add(chitietChuongtrinh);
                }
                arrlistctct.put(listctct);
//            System.out.println(arrlistctct.toString());
                stmt = con.prepareStatement(getnoidungct);
                stmt.setInt(1, idchuongtrinh);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    noidungChuongtrinh = new NoidungChuongtrinh(rs.getInt("ID"), rs.getString("tieudeamnhac"), rs.getString("noidungamnhac"), rs.getString("tieudevanhoc"), rs.getString("noidungvanhoc"), rs.getInt("idct"));
                    listndct.add(noidungChuongtrinh);
                }
                arrlistndct.put(listndct);
//            System.out.println(arrlistndct.toString());
                stmt = con.prepareStatement(getchuongtrinh);
                stmt.setInt(1, idchuongtrinh);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    chuongtrinh = new Chuongtrinh(rs.getInt("ID"), rs.getString("chudiem"), rs.getString("chude"), rs.getString("ngaybatdau"), rs.getString("ngayketthuc"), rs.getInt("IDNguoidung"), listctct, listndct);

                }

                ct.put("id", chuongtrinh.getId());
                ct.put("chudiem", chuongtrinh.getChudiem());
                ct.put("chude", chuongtrinh.getChude());
                ct.put("ngaybatdau", chuongtrinh.getNgaybatdau());
                ct.put("ngayketthuc", chuongtrinh.getNgsyketthuc());
                ct.put("idnguoidung", chuongtrinh.getIdnguoidung());
                ct.put("listchitietchuongtrinh", arrlistctct);
                ct.put("listnoidungchuongtrinh", arrlistndct);
                JSONObject data = new JSONObject();
                data.put("to", MessageKey.GETCHUONGTRINH);
                data.put("data", ct);
                listchuongtrinh.put(data);
                
            } else {
                listchuongtrinh.put(new JSONObject().put("to", MessageKey.GETCHUONGTRINH_FAIL));
                stmt.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(listchuongtrinh.toString());
        return listchuongtrinh;
    }

    private String getdate(String json) {
        String date="";
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    date = object.getString("date");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ": " + msg);
        switch (Message(msg)) {
            case MessageKey.EXIT://Client gửi tín hiệu thoát
                channel.writeAndFlush(toJSON(MessageKey.EXIT).toString());
                channel.disconnect();
                System.out.println(ctx.channel().remoteAddress() + ": " + MessageKey.DISCONNECTED);
                break;
            case MessageKey.TEST:
//                channel.writeAndFlush(getUser().toString());
                break;
            case MessageKey.LOGIN://Client gửi yêu cầu đăng nhập
                System.out.println(MessageKey.LOGIN);
                Data data = getUser(msg);
                String username = data.getUsername();
                String pass = data.getPassword();
                System.out.println(username + " " + pass);
                if (CompareUser(username, pass) == true) {
                    channel.writeAndFlush(getUserInfos(username).toString());
                } else {
                    channel.writeAndFlush(toJSON(MessageKey.FAIL).toString());
                }

                break;
            case MessageKey.SIGNUP://Client gửi yêu cầu đăng ký tài khoản
                if (signUp(msg)) {
                    channel.writeAndFlush(toJSON(MessageKey.SINGUP_SUCCESS).toString());
                } else {
                    channel.writeAndFlush(toJSON(MessageKey.SINGUP_FAIL).toString());
                }
                break;
            case MessageKey.LOADTYPEUSER://client yêu cầu thông tin loại người dùng
                JSONArray typeuser = getTyeUser();
                System.out.println(typeuser);
                channel.writeAndFlush(typeuser.toString());
                break;
            case MessageKey.GETSPLAN://gửi chương trình học
                break;
            case MessageKey.GETMENU://gửi thực đơn
                break;
            case MessageKey.UPDATEUSER:
                if (updateUserInfor(msg) == true) {
                    channel.writeAndFlush(toJSON(MessageKey.UPDATEUSER).toString());
                } else {
                    channel.writeAndFlush(toJSON(MessageKey.UPDATEFAIL).toString());
                }

                break;
            case MessageKey.UPDATEPASS:
                if (updateUserpass(msg) == true) {
                    channel.writeAndFlush(toJSON(MessageKey.UPDATEPASS).toString());
                } else {
                    channel.writeAndFlush(toJSON(MessageKey.UPDATEPASS_FAIL).toString());
                }
                break;
            case MessageKey.GETCHUONGTRINH:
                    JSONArray chuongtrinh=getChuongtrinh(getIDfromdate(getdate(msg)));
                    channel.writeAndFlush(chuongtrinh.toString());
                break;
            default:
                break;

        }
    }

}
