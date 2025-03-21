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
import java.util.List;
import model.Schedule;
import model.Store;
import model.User;
import model.pagination.Pagination;

/**
 *
 * @author nguyenanh
 */
public class workSchedule extends HttpServlet {
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
            out.println("<title>Servlet workSchedule</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet workSchedule at " + request.getContextPath () + "</h1>");
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
        if (roleID == 3) {
            response.sendRedirect("ListProducts"); 
            return;
        }
        if (roleID == 1) {
            response.sendRedirect("listusers"); 
            return;
        }
        
        if (request.getParameter("id") != null && !(request.getParameter("id") == "")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Schedule schedule = daos.getScheduleById(id);
            if (schedule != null) {
                request.setAttribute("schedule", schedule);
                session.setAttribute("scheduleIdUpdate", id);
                request.getRequestDispatcher("updateschedule").forward(request, response);
            }
        }  
        
        List<Schedule>  schedule = daos.listSchedule(user_current.getStoreID());

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalSchedule = schedule.size();
        int pageSize = 9;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        Pagination page = new Pagination(totalSchedule, pageSize, currentPage);
        session.setAttribute("page", page);
        request.setAttribute("currentPageUrl", "workschedule"); // Hoặc "products"
        
        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        request.setAttribute("endDate", endDate);
        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher("/schedule/workSchedule.jsp").forward(request, response);
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
        List<Schedule> filteredSchedules = daos.listSchedule(user_current.getStoreID());
        String mess = null;
        String keyword1 = request.getParameter("keyword");
        String startDateCreate = request.getParameter("startDate");
        String endDateCreate = request.getParameter("endDate");
        String action = request.getParameter("selectedAction");
        Integer selectedAction = (action != null && !action.isEmpty()) ? Integer.parseInt(action) : null;        

        String error = null;
        String actionBlock = request.getParameter("actionBlock");
        String ScheduleIDBlock = request.getParameter("ScheduleIDBlock");
        if (ScheduleIDBlock != null && !(ScheduleIDBlock == "")) { 
            int id = Integer.parseInt(ScheduleIDBlock);
            if ("block".equals(actionBlock)) {
                // Gọi DAO để khóa user
                Integer deleteBy = (Integer) session.getAttribute("userID");
                daos.blockScheduleByID(id, deleteBy);
            } else if ("unlock".equals(actionBlock)) {
                // Gọi DAO để mở khóa user
                daos.unblockScheduleByID(id);
            }

        }
        if (error != null) {
            response.setStatus(400); // Báo lỗi cho AJAX
            response.getWriter().write(error);
        } else {
            response.setStatus(200);
        }

        //search
        if (keyword1 != null && !keyword1.isEmpty()) {
            List<Schedule> tempSchedule = daos.getScheduleByKeyword(keyword1, filteredSchedules);
            if (!tempSchedule.isEmpty()) {
                filteredSchedules = tempSchedule; // Chỉ cập nhật nếu có kết quả
            } else {
            mess = "Không tìm thấy ca làm việc nào.";
            }
        }
        if (startDateCreate != null && !startDateCreate.isEmpty()) {
            List<Schedule> tempSchedule = daos.getScheduleByDate(startDateCreate, endDateCreate, filteredSchedules);
            if (!tempSchedule.isEmpty()) {
                filteredSchedules = tempSchedule; // Chỉ cập nhật nếu có kết quả
            } else {
            mess = "Không tìm thấy ca làm việc nào.";
            }
        }
        if (selectedAction != null && selectedAction != -1) {
            List<Schedule> tempSchedule = daos.getScheduleByAction(selectedAction, filteredSchedules);
            if (!tempSchedule.isEmpty()) {
                filteredSchedules = tempSchedule; // Chỉ cập nhật nếu có kết quả
            } else {
            mess = "Không tìm thấy ca làm việc nào.";
            }
        }
        if (filteredSchedules == null || filteredSchedules.isEmpty()) {
            mess = "Không tìm thấy ca làm việc nào.";
            System.out.println(mess);
            filteredSchedules = daos.listSchedule(user_current.getStoreID());
        }

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = filteredSchedules.size();
        int pageSize = 9;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        request.setAttribute("currentPageUrl", "workschedule"); // Hoặc "products"

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);

        // Lấy danh sách user theo trang hiện tại
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Schedule> paginatedUsers = filteredSchedules.subList(startIndex, endIndex);

        request.setAttribute("mess", mess);
        request.setAttribute("schedule", filteredSchedules);
        request.setAttribute("keyword", keyword1);
        request.setAttribute("startDate", startDateCreate);
        request.setAttribute("endDate", endDateCreate);
        request.setAttribute("selectedAction", selectedAction);
        request.setAttribute("user_current", user_current);
        request.getRequestDispatcher("/schedule/workSchedule.jsp").forward(request, response);
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
