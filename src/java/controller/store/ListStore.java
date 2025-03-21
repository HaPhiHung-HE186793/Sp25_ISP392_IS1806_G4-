/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.store;

import DAO.DAOStore;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Store;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.pagination.Pagination;
import model.User;

/**
 *
 * @author nguyenanh
 */
public class ListStore extends HttpServlet {
    DAOStore dao = new DAOStore();
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
            out.println("<title>Servlet ListStore</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListStore at " + request.getContextPath () + "</h1>");
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
        Integer userID = (Integer) session.getAttribute("userID");
        Integer storeID = (Integer) session.getAttribute("storeID");
        User user_current = (User) session.getAttribute("user");
        if (user_current.getRoleID() != 1) {
            response.sendRedirect("ListProducts"); // sửa thành đường dẫn của trang chủ sau khi hoàn thành code
            return;
        }
        String error = null;

        if (request.getParameter("id") != null && !(request.getParameter("id") == "")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Store Store = dao.getStoreById(id);
            if (Store != null) {               
                    request.setAttribute("Store", Store);
                    session.setAttribute("storeIdUpdate", Store.getStoreID());
                    request.getRequestDispatcher("updatestore").forward(request, response);
            }
        }
        if (request.getParameter("idDetail") != null && !(request.getParameter("id") == "")) {
            int id = Integer.parseInt(request.getParameter("idDetail"));
            Store Store = dao.getStoreById(id);
            if (Store != null) {               
                    request.setAttribute("Store", Store);
                    request.getRequestDispatcher("storedetail").forward(request, response);
            }
        }


        List<Store> Store = null;
        Store = dao.listStore();
        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalStore = Store.size();
        int pageSize = 4;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }

        Pagination page = new Pagination(totalStore, pageSize, currentPage);
        session.setAttribute("page", page);
        request.setAttribute("currentPageUrl", "liststore"); // Hoặc "products"
        

        String endDateCreate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        request.setAttribute("endDateCreate", endDateCreate);
        request.setAttribute("error", error);
        request.setAttribute("user_current", user_current);
        session.setAttribute("Store", Store);
        request.getRequestDispatcher("store/listStore.jsp").forward(request, response);
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
        List<Store> filteredStore = dao.listStore();
        String mess = null;
        String keyword1 = request.getParameter("keyword1");
        String startDateCreate = request.getParameter("startDateCreate");
        String endDateCreate = request.getParameter("endDateCreate");
        String action = request.getParameter("selectedAction");
        Integer selectedAction = (action != null && !action.isEmpty()) ? Integer.parseInt(action) : null;        

        

        //search
        if (keyword1 != null && !keyword1.isEmpty()) {
            List<Store> tempStore = dao.getStoreByKeyword(keyword1, filteredStore);
            if (!tempStore.isEmpty()) {
                filteredStore = tempStore; // Chỉ cập nhật nếu có kết quả
            } else {
            mess = "Không tìm thấy cửa hàng nào.";
            }
        }
        if (startDateCreate != null && !startDateCreate.isEmpty()) {
            List<Store> tempStore = dao.getStoreByDate(startDateCreate, endDateCreate, filteredStore);
            if (!tempStore.isEmpty()) {
                filteredStore = tempStore; // Chỉ cập nhật nếu có kết quả
            } else {
            mess = "Không tìm thấy cửa hàng nào.";
            }
        }
        if (selectedAction != null && selectedAction != -1) {
            List<Store> tempStore = dao.getStoreByAction(selectedAction, filteredStore);
            if (!tempStore.isEmpty()) {
                filteredStore = tempStore; // Chỉ cập nhật nếu có kết quả
            } else {
            mess = "Không tìm thấy cửa hàng nào.";
            }
        }
        if (filteredStore == null || filteredStore.isEmpty()) {
            mess = "Không tìm thấy cửa hàng nào.";
            System.out.println(mess);
            filteredStore = dao.listStore();

        }

        // Cập nhật pagination dựa trên số lượng kết quả tìm kiếm
        int totalUsers = filteredStore.size();
        int pageSize = 4;
        int currentPage = 1;

        if (request.getParameter("cp") != null) {
            currentPage = Integer.parseInt(request.getParameter("cp"));
        }
        request.setAttribute("currentPageUrl", "liststore"); // Hoặc "products"

        Pagination page = new Pagination(totalUsers, pageSize, currentPage);
        session.setAttribute("page", page);

        // Lấy danh sách user theo trang hiện tại
        int startIndex = page.getStartItem();
        int endIndex = Math.min(startIndex + pageSize, totalUsers);
        List<Store> paginatedUsers = filteredStore.subList(startIndex, endIndex);

        request.setAttribute("mess", mess);
        request.setAttribute("Store", filteredStore);
        request.setAttribute("keyword1", keyword1);
        request.setAttribute("startDateCreate", startDateCreate);
        request.setAttribute("endDateCreate", endDateCreate);
        request.setAttribute("selectedAction", selectedAction);
        request.setAttribute("user_current", user_current);
        request.getRequestDispatcher("store/listStore.jsp").forward(request, response);
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
