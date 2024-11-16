/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.DatBanDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DatBan;
import java.util.List;
import javax.servlet.RequestDispatcher;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hi tcc
 */
@WebServlet(name = "DatBanServlet", urlPatterns = {"/DatBanServlet"})
public class DatBanServlet extends HttpServlet {
    // Đảm bảo tương thích phiên bản
    private static final long serialVersionUID = 1L;

    // Khởi tạo đối tượng DatBanDAO
    private DatBanDAO datBanDAO;
    @Override
    public void init() throws ServletException {
        // Khởi tạo DAO, nếu chưa khởi tạo
        datBanDAO = new DatBanDAO();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DatBanServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DatBanServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Dùng để lấy hoặc truy vấn dữ liệu
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // Lấy danh sách bàn từ DAO
            List<DatBan> danhSachBan = datBanDAO.layDanhSachBan();
            // Chuyển danh sách bàn tới view (JSP)
            request.setAttribute("danhSachBan", danhSachBan);
            // Điều hướng đến trang DatBan.jsp để hiển thị dữ liệu
            RequestDispatcher dispatcher = request.getRequestDispatcher("/datban/DatBan.jsp");
            dispatcher.forward(request, response);
        }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Dùng để gửi dữ liệu từ client đến server
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                // Thêm bàn
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                String tinhTrang = request.getParameter("tinhTrang");
                DatBan banMoi = new DatBan(maBan, tinhTrang);
                datBanDAO.themBan(banMoi);

            } else if ("update".equals(action)) {
                // Sửa bàn
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                String tinhTrang = request.getParameter("tinhTrang");
                datBanDAO.capnhatTinhTrang(maBan, tinhTrang);

            } else if ("delete".equals(action)) {
                // Xóa bàn
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                datBanDAO.xoaBan(maBan);

            } else if ("search".equals(action)) {
                // Tìm kiếm bàn
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                String banTimDuoc = datBanDAO.layTinhTrangTheoMa(maBan);
                if (banTimDuoc != null && !banTimDuoc.isEmpty()) {
                    // Nếu tìm thấy bàn, truyền kết quả vào request
                    request.setAttribute("ketQuaTimKiem", banTimDuoc);
                    request.setAttribute("thongBao", "Có tìm thấy bàn với mã " + maBan);
                } else {
                    // Nếu không tìm thấy, thông báo không có kết quả
                    request.setAttribute("ketQuaTimKiem", ""); 
                    request.setAttribute("thongBao", "Không tìm thấy bàn với mã " + maBan);
                }

    // Điều hướng đến trang DatBan.jsp để hiển thị kết quả
    RequestDispatcher dispatcher = request.getRequestDispatcher("/datban/DatBan.jsp");
    dispatcher.forward(request, response);
            }

            // Sau khi xử lý, lấy lại danh sách bàn để cập nhật trang
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xử lý yêu cầu.");
        }
    }
    @Override
    public void destroy() {
        if (datBanDAO != null) {
            try {
                datBanDAO.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(DatBanServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
