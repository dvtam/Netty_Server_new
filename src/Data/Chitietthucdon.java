/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;

/**
 *
 * @author TAM
 */
public class Chitietthucdon {
    private int id;
    private String thu;
    private String tenbua;
    private String luatuoi;
    private ArrayList<String> monnan;
    private int idthucdon;

    public Chitietthucdon() {
    }

    public Chitietthucdon(int id, String thu, String tenbua, String luatuoi, int idthucdon) {
        this.id = id;
        this.thu = thu;
        this.tenbua = tenbua;
        this.luatuoi = luatuoi;
        this.idthucdon = idthucdon;
    }

    public Chitietthucdon(int id, String thu, String tenbua, String luatuoi, ArrayList<String> monnan, int idthucdon) {
        this.id = id;
        this.thu = thu;
        this.tenbua = tenbua;
        this.luatuoi = luatuoi;
        this.monnan = monnan;
        this.idthucdon = idthucdon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getTenbua() {
        return tenbua;
    }

    public void setTenbua(String tenbua) {
        this.tenbua = tenbua;
    }

    public String getLuatuoi() {
        return luatuoi;
    }

    public void setLuatuoi(String luatuoi) {
        this.luatuoi = luatuoi;
    }

    public ArrayList<String> getMonnan() {
        return monnan;
    }

    public void setMonnan(ArrayList<String> monnan) {
        this.monnan = monnan;
    }

    public int getIdthucdon() {
        return idthucdon;
    }

    public void setIdthucdon(int idthucdon) {
        this.idthucdon = idthucdon;
    }
    
}
