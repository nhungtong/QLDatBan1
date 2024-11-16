/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.GoiMonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.GoiMon;
import java.util.List;
import javax.servlet.RequestDispatcher;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hi tcc
 */
@WebServlet(name = "GoiMonServlet", urlPatterns = {"/GoiMonServlet"})
public class GoiMonServlet extends HttpServlet {
    // Đảm bảo tương thích phiên bản
    private static final long serialVersionUID = 1L;

    // Khởi tạo đối tượng GoiMonDAO
    private GoiMonDAO goiMonDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo DAO nếu chưa khởi tạo
        goiMonDAO = new GoiMonDAO();
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
            out.println("<title>Servlet GoiMonServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GoiMonServlet at " + request.getContextPath() + "</h1>");
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
    // Hiển thị danh sách đơn gọi món
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách tất cả đơn gọi món
        try {
            List<GoiMon> danhSachGoiMon = goiMonDAO.layDanhSachGoiMon();
            // Chuyển danh sách đơn gọi món tới view (JSP)
            request.setAttribute("danhSachGoiMon", danhSachGoiMon);
            // Điều hướng đến trang GoiMon.jsp để hiển thị dữ liệu
            RequestDispatcher dispatcher = request.getRequestDispatcher("/goimon/GoiMon.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy danh sách gọi món.");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Thêm đơn gọi món mới
            try {
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                int maMonAn = Integer.parseInt(request.getParameter("maMonAn"));
                String tinhTrang = request.getParameter("tinhTrang");

                GoiMon goiMon = new GoiMon(maBan, maMonAn, tinhTrang);
                boolean result = goiMonDAO.themGoiMon(goiMon);

                if (result) {
                    // Nếu thêm thành công, quay lại trang danh sách
                    response.sendRedirect("GoiMonServlet");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể thêm gọi món.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi thêm gọi món.");
            }
        } else if ("update".equals(action)) {
            // Cập nhật trạng thái đơn gọi món
            try {
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                int maMonAn = Integer.parseInt(request.getParameter("maMonAn"));
                String tinhTrang = request.getParameter("tinhTrang");

                boolean result = goiMonDAO.capNhatTinhTrang(maBan, maMonAn, tinhTrang);

                if (result) {
                    // Nếu cập nhật thành công, quay lại trang danh sách
                    response.sendRedirect("GoiMonServlet");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể cập nhật trạng thái gọi món.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật trạng thái gọi món.");
            }
        } else if ("delete".equals(action)) {
            // Xóa đơn gọi món
            try {
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                int maMonAn = Integer.parseInt(request.getParameter("maMonAn"));

                boolean result = goiMonDAO.xoaGoiMon(maBan, maMonAn);

                if (result) {
                    // Nếu xóa thành công, quay lại trang danh sách
                    response.sendRedirect("GoiMonServlet");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể xóa gọi món.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xóa gọi món.");
            }
        }else if ("search".equals(action)) {
            // Tìm kiếm đơn gọi món theo mã bàn và mã món ăn
            try {
                int maBan = Integer.parseInt(request.getParameter("maBan"));
                int maMonAn = Integer.parseInt(request.getParameter("maMonAn"));

                GoiMon goiMon = goiMonDAO.layGoiMonTheoMa(maBan, maMonAn);

                if (goiMon != null) {
                    request.setAttribute("goiMon", goiMon);
                    // Điều hướng đến trang GoiMon.jsp để hiển thị kết quả tìm kiếm
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/goimon/GoiMon.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy gọi món.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tìm kiếm gọi món.");
            }
        }
    }
    @Override
    public void destroy() {
        try {
            if (goiMonDAO != null) {
                goiMonDAO.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
