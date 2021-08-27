package com.example.demo.payload.reponse;

import java.util.List;

public class ThongKeDoanhThuDirector2 {
    private int month;
    private List<ThongKeDoanhThuDirector> thongKe;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<ThongKeDoanhThuDirector> getThongKe() {
        return thongKe;
    }

    public void setThongKe(List<ThongKeDoanhThuDirector> thongKe) {
        this.thongKe = thongKe;
    }
}
