/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hi tcc
 */
public class DatBan {
    private int MaBan;
    private String TinhTrang;

    public DatBan() {
    }

    public DatBan(int MaBan, String TinhTrang) {
        this.MaBan = MaBan;
        this.TinhTrang = TinhTrang;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int MaBan) {
        this.MaBan = MaBan;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }
    
}
