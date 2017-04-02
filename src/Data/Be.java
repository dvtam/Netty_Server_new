/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author TAM
 */
public class Be {
    private int id;
    private String tenbe;
    private int tuoi;
    private String ngaysinh;
    private int idlop;

    public Be() {
    }

    public Be(int id, String tenbe, int tuoi, String ngaysinh, int idlop) {
        this.id = id;
        this.tenbe = tenbe;
        this.tuoi = tuoi;
        this.ngaysinh = ngaysinh;
        this.idlop = idlop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenbe() {
        return tenbe;
    }

    public void setTenbe(String tenbe) {
        this.tenbe = tenbe;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public int getIdlop() {
        return idlop;
    }

    public void setIdlop(int idlop) {
        this.idlop = idlop;
    }
    
}
