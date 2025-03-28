/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.zone;

import DAO.DAOZones;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ACER
 */
public class UpdateZoneIsDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int zoneID = Integer.parseInt(request.getParameter("zoneID"));
        boolean isDelete = request.getParameter("isDeleted") != null; // Nếu có 'status' nghĩa là checked

        DAOZones dao = new DAOZones();
        int result = dao.updateZoneIsDelete(zoneID, isDelete);

        if (result > 0) {
            response.sendRedirect("ListZones"); // Reload lại trang nếu thành công
        } else {
            response.getWriter().write("Cập nhật thất bại!");
        }
    }
}
