/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dbconnection.KetNoiCSDL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DatBan;
/**
 *
 * @author hi tcc
 */
public class DatBanDAO {
    private Connection connection;

    public DatBanDAO() {
        // Tạo kết nối đến cơ sở dữ liệu
        connection = KetNoiCSDL.getConnection();
    }
    // Phương thức lấy danh sách các bàn
    public List<DatBan> layDanhSachBan() {
        List<DatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM datban"; 
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int maBan = rs.getInt("MaBan");
                String tinhTrang = rs.getString("TinhTrang");
                DatBan datBan = new DatBan(maBan, tinhTrang);
                list.add(datBan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // Phương thức lấy tình trạng của bàn theo mã bàn
    public String layTinhTrangTheoMa(int maBan) {
        String tinhTrang = null;
        String sql = "SELECT TinhTrang FROM datban WHERE MaBan = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, maBan);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    tinhTrang = rs.getString("TinhTrang");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tinhTrang;
    }
    // Phương thức cập nhật tình trạng bàn
    public boolean capnhatTinhTrang(int maBan, String tinhTrang) {
        boolean hangCN = false;
        String sql = "UPDATE datban SET TinhTrang = ? WHERE MaBan = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tinhTrang);
            preparedStatement.setInt(2, maBan);
            hangCN = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hangCN;
    }
    // Phương thức thêm mới bàn
    public boolean themBan(DatBan datBan) {
        boolean hangDT = false;
        String sql = "INSERT INTO datban (MaBan, TinhTrang) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, datBan.getMaBan());
            preparedStatement.setString(2, datBan.getTinhTrang());
            hangDT = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hangDT;
    }
    // Phương thức xóa bàn
    public boolean xoaBan(int maBan) {
        boolean hangDX = false;
        String sql = "DELETE FROM datban WHERE MaBan = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, maBan);
            hangDX = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hangDX;
    }
    // Đóng kết nối cơ sở dữ liệu
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
