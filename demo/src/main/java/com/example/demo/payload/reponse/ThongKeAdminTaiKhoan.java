package com.example.demo.payload.reponse;

import java.util.List;

public class ThongKeAdminTaiKhoan {
    private int month;
    private List<ThongKeAdminTaiKhoan2> thongKe;

    public List<ThongKeAdminTaiKhoan2> getThongKe() {
        return thongKe;
    }

    public void setThongKe(List<ThongKeAdminTaiKhoan2> thongKe) {
        this.thongKe = thongKe;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

}
