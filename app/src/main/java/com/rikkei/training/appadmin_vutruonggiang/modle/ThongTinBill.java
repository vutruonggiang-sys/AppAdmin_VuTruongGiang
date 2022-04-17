package com.rikkei.training.appadmin_vutruonggiang.modle;

import java.util.List;

public class ThongTinBill {
    List<ThongTinNguoiOrder> thongTinNguoiOrderList;

    public ThongTinBill(List<ThongTinNguoiOrder> thongTinNguoiOrderList) {
        this.thongTinNguoiOrderList = thongTinNguoiOrderList;
    }

    public ThongTinBill() {
    }

    public List<ThongTinNguoiOrder> getThongTinNguoiOrderList() {
        return thongTinNguoiOrderList;
    }

    public void setThongTinNguoiOrderList(List<ThongTinNguoiOrder> thongTinNguoiOrderList) {
        this.thongTinNguoiOrderList = thongTinNguoiOrderList;
    }
}
