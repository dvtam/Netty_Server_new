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
public class UserInfos {
    private int id;
    private String hoten;
    private String ngaysinh;
    private String gioitinh;
    private String diachi;
    private String dienthoai;
    private String email;
    private String tentaikhoan;
    private String matkhau;
    private int idloai;

    public UserInfos() {
    }

    public UserInfos(int id, String hoten, String ngaysinh, String gioitinh, String diachi, String dienthoai, String email, String tentaikhoan, String matkhau, int idloai) {
        this.id = id;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.dienthoai = dienthoai;
        this.email = email;
        this.tentaikhoan = tentaikhoan;
        this.matkhau = matkhau;
        this.idloai = idloai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }
    
}
