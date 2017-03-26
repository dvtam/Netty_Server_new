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
public class Chuongtrinh {
    private int id;
    private String chudiem;
    private String chude;
    private String ngaybatdau;
    private String ngsyketthuc;
    private int idnguoidung;
    private ArrayList<ChitietChuongtrinh> chitietchuongtrinh;
    private ArrayList<NoidungChuongtrinh> noidungChuongtrinhs;

    public Chuongtrinh() {
        
    }

    public Chuongtrinh(int id, String chudiem, String chude, String ngaybatdau, String ngsyketthuc, int idnguoidung, ArrayList<ChitietChuongtrinh> chitietchuongtrinh, ArrayList<NoidungChuongtrinh> noidungChuongtrinhs) {
        this.id = id;
        this.chudiem = chudiem;
        this.chude = chude;
        this.ngaybatdau = ngaybatdau;
        this.ngsyketthuc = ngsyketthuc;
        this.idnguoidung = idnguoidung;
        this.chitietchuongtrinh = chitietchuongtrinh;
        this.noidungChuongtrinhs = noidungChuongtrinhs;
    }

    public Chuongtrinh(int id, String chudiem, String chude, String ngaybatdau, String ngsyketthuc, int idnguoidung) {
        this.id = id;
        this.chudiem = chudiem;
        this.chude = chude;
        this.ngaybatdau = ngaybatdau;
        this.ngsyketthuc = ngsyketthuc;
        this.idnguoidung = idnguoidung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChudiem() {
        return chudiem;
    }

    public void setChudiem(String chudiem) {
        this.chudiem = chudiem;
    }

    public String getChude() {
        return chude;
    }

    public void setChude(String chude) {
        this.chude = chude;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public String getNgsyketthuc() {
        return ngsyketthuc;
    }

    public void setNgsyketthuc(String ngsyketthuc) {
        this.ngsyketthuc = ngsyketthuc;
    }

    public int getIdnguoidung() {
        return idnguoidung;
    }

    public void setIdnguoidung(int idnguoidung) {
        this.idnguoidung = idnguoidung;
    }

    public ArrayList<ChitietChuongtrinh> getChitietchuongtrinh() {
        return chitietchuongtrinh;
    }

    public void setChitietchuongtrinh(ArrayList<ChitietChuongtrinh> chitietchuongtrinh) {
        this.chitietchuongtrinh = chitietchuongtrinh;
    }

    public ArrayList<NoidungChuongtrinh> getNoidungChuongtrinhs() {
        return noidungChuongtrinhs;
    }

    public void setNoidungChuongtrinhs(ArrayList<NoidungChuongtrinh> noidungChuongtrinhs) {
        this.noidungChuongtrinhs = noidungChuongtrinhs;
    }
    
}
