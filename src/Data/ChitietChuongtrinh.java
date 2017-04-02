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
public class ChitietChuongtrinh {
    private int id;
    private String thoigian;
    private String thu;
    private String noidung;
    private String tieude;
    private String chude;
    private int idchuongtrinh;

    public ChitietChuongtrinh() {
    }

    public ChitietChuongtrinh(int id, String thoigian, String thu, String noidung, String tieude, String chude, int idchuongtrinh) {
        this.id = id;
        this.thoigian = thoigian;
        this.thu = thu;
        this.noidung = noidung;
        this.tieude = tieude;
        this.chude = chude;
        this.idchuongtrinh = idchuongtrinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getChude() {
        return chude;
    }

    public void setChude(String chude) {
        this.chude = chude;
    }

    public int getIdchuongtrinh() {
        return idchuongtrinh;
    }

    public void setIdchuongtrinh(int idchuongtrinh) {
        this.idchuongtrinh = idchuongtrinh;
    }

    
}
