/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.schedule;

import DAO.DAOSchedule;
import DAO.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Schedule;
import model.Store;
import model.User;
import model.pagination.Pagination;

/**
 *
 * @author nguyenanh
 */
public class updateSchedule extends HttpServlet {
   DAOUser dao = new DAOUser();
    DAOSchedule daos = new DAOSchedule();
    Pagination Page;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet updateSchedule</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateSchedule at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer roleID = (Integer) session.getAttribute("roleID");
        User user_current = (User) session.getAttribute("user");
        Schedule schedule = (Schedule) request.getAttribute("schedule");
        if (roleID == 3) {
            response.sendRedirect("ListProducts"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        if (roleID == 1) {
            response.sendRedirect("listusers"); 
            return;
        }        
        Integer scheduleIdUpdate = (Integer) session.getAttribute("scheduleIdUpdate");
        Schedule schedules = daos.getScheduleById(scheduleIdUpdate);
        request.setAttribute("schedule", schedules);
        request.getRequestDispatcher("schedule/updateSchedule.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user_current = (User) session.getAttribute("user");
        List<String> errors = new ArrayList<>();
        String shiftId = request.getParameter("shiftId");
        String shiftName = request.getParameter("shiftName");
        String shiftStart = request.getParameter("shiftStart");
        String shiftEnd = request.getParameter("shiftEnd");
        String breakStart = request.getParameter("breakStart");
        String breakEnd = request.getParameter("breakEnd");
        String action = request.getParameter("statusSelect");
        Integer statusSelect = (action != null && !action.isEmpty()) ? Integer.parseInt(action) : null;
        Integer shiftid = (shiftId != null && !shiftId.isEmpty()) ? Integer.parseInt(shiftId) : null;
        
        try {        
        // Kiểm tra shiftId có tồn tại trong database không
        Schedule existingSchedule = daos.getScheduleById(shiftid);        
        if (existingSchedule == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid shift ID");
            return;
        }
    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid shift ID format");
    }

        // Kiểm tra shiftName
        if (shiftName == null || shiftName.trim().isEmpty()) {
            errors.add("Vui lòng nhập tên ca làm việc!");
        }

// Kiểm tra shiftStart
        if (shiftStart == null || shiftStart.trim().isEmpty()) {
            errors.add("Vui lòng nhập thời gian bắt đầu ca làm việc!");
        }

// Kiểm tra shiftEnd
        if (shiftEnd == null || shiftEnd.trim().isEmpty()) {
            errors.add("Vui lòng nhập thời gian kết thúc ca làm việc!");
        }

// Kiểm tra breakStart
        if (breakStart == null || breakStart.trim().isEmpty()) {
            errors.add("Vui lòng nhập thời gian bắt đầu nghỉ!");
        }

// Kiểm tra breakEnd
        if (breakEnd == null || breakEnd.trim().isEmpty()) {
            errors.add("Vui lòng nhập thời gian kết thúc nghỉ!");
        }

// Kiểm tra statusSelect
        if (statusSelect == null) {
            errors.add("Vui lòng chọn trạng thái!");
        }

        // Nếu có lỗi, quay lại trang createUser.jsp với danh sách lỗi và dữ liệu đã nhập
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("shiftId", shiftid);
            request.setAttribute("shiftName", shiftName);
            request.setAttribute("shiftStart", shiftStart);
            request.setAttribute("shiftEnd", shiftEnd);
            request.setAttribute("breakStart", breakStart);
            request.setAttribute("breakEnd", breakEnd);
            request.setAttribute("statusSelect", statusSelect);
            request.setAttribute("u", user_current);
            Schedule schedule = daos.getScheduleById(shiftid);
            request.setAttribute("schedule", schedule);
            request.getRequestDispatcher("/schedule/updateSchedule.jsp").forward(request, response);
            return;
        }
        boolean isDelete = false;
        if(statusSelect == 1){
            isDelete = true;
        }

        Schedule schedule = new Schedule(shiftid, shiftName, shiftStart, shiftEnd, breakStart, breakEnd, isDelete);
        daos.updateSchedule(schedule);

       Schedule schedules = daos.getScheduleById(shiftid);


        request.setAttribute("success", "Cập nhật thành công!");
        request.setAttribute("shiftId", shiftid);
        request.setAttribute("shiftName", shiftName);
        request.setAttribute("shiftStart", shiftStart);
        request.setAttribute("shiftEnd", shiftEnd);
        request.setAttribute("breakStart", breakStart);
        request.setAttribute("breakEnd", breakEnd);
        request.setAttribute("statusSelect", statusSelect);
        request.setAttribute("schedule", schedules);
        request.getRequestDispatcher("/schedule/updateSchedule.jsp").forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
