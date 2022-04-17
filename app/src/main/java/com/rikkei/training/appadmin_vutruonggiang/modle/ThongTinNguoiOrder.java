package com.rikkei.training.appadmin_vutruonggiang.modle;

public class ThongTinNguoiOrder {
    String id,hoTen,sdt,diaChi;
    long giaKhuyenMai;
    String idRes;

    public ThongTinNguoiOrder(){}

    public ThongTinNguoiOrder(String id, long giaKhuyenMai,String hoTen,String sdt,String diaChi, String idRes) {
        this.id = id;
        this.giaKhuyenMai=giaKhuyenMai;
        this.hoTen=hoTen;
        this.sdt=sdt;
        this.diaChi=diaChi;
        this.idRes=idRes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getGiaKhuyenMai() {
        return giaKhuyenMai;
    }

    public void setGiaKhuyenMai(long giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getIdRes() {
        return idRes;
    }

    public void setIdRes(String idRes) {
        this.idRes = idRes;
    }
}
