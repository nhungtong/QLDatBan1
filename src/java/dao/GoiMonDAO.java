/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbconnection.KetNoiCSDL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.GoiMon;
/**
 *
 * @author hi tcc
 */
public class GoiMonDAO {
     private Connection connection;

    public GoiMonDAO() {
        // Tạo kết nối đến cơ sở dữ liệu
        connection = KetNoiCSDL.getConnection();
    }
    // Phương thức lấy danh sách tất cả đơn gọi món
    public List<GoiMon> layDanhSachGoiMon() throws SQLException {
        List<GoiMon> danhSachGoiMon = new ArrayList<>();
        String sql = "SELECT * FROM goimon";  // Giả sử bảng là 'goimon'

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int maBan = rs.getInt("MaBan");
                int maMonAn = rs.getInt("MaMonAn");
                String tinhTrang = rs.getString("TinhTrang");
                GoiMon goiMon = new GoiMon(maBan, maMonAn, tinhTrang);
                danhSachGoiMon.add(goiMon);
            }
        }
        return danhSachGoiMon;
    }
    // Phương thức thêm đơn gọi món mới
    public boolean themGoiMon(GoiMon goiMon) throws SQLException {
        String sql = "INSERT INTO goimon (MaBan, MaMonAn, TinhTrang) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, goiMon.getMaBan());
            pstmt.setInt(2, goiMon.getMaMonAn());
            pstmt.setString(3, goiMon.getTinhTrang());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Phương thức cập nhật trạng thái đơn gọi món
    public boolean capNhatTinhTrang(int maBan, int maMonAn, String tinhTrang) throws SQLException {
        String sql = "UPDATE goimon SET TinhTrang = ? WHERE MaBan = ? AND MaMonAn = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tinhTrang);
            pstmt.setInt(2, maBan);
            pstmt.setInt(3, maMonAn);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Phương thức xóa đơn gọi món
    public boolean xoaGoiMon(int maBan, int maMonAn) throws SQLException {
        String sql = "DELETE FROM goimon WHERE MaBan = ? AND MaMonAn = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, maBan);
            pstmt.setInt(2, maMonAn);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Phương thức tìm kiếm đơn gọi món theo mã bàn và mã món ăn
    public GoiMon layGoiMonTheoMa(int maBan, int maMonAn) throws SQLException {
        String sql = "SELECT * FROM goimon WHERE MaBan = ? AND MaMonAn = ?";
        GoiMon goiMon = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, maBan);
            pstmt.setInt(2, maMonAn);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String tinhTrang = rs.getString("TinhTrang");
                    goiMon = new GoiMon(maBan, maMonAn, tinhTrang);
                }
            }
        }
        return goiMon;
    }

    // Đảm bảo đóng kết nối khi kết thúc
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
