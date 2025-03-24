/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.login;

import DAO.DAOUser;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="loginController", urlPatterns={"/loginURL"})
public class loginController extends HttpServlet {
   
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
        processRequest(request, response);
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
    HttpSession session = request.getSession(true);
    session.setMaxInactiveInterval(30 * 60);
    DAOUser dao = new DAOUser();
    String service = request.getParameter("service");
    
    if (service == null) { 
        service = "listUser";
    }
    
    try (PrintWriter out = response.getWriter()) {
        if (service.equals("logoutUser")) {
            session.invalidate();
            dao.dispatch(request, response, "login/login.jsp");
        } else if (service.equals("loginUser")) {
            String userName = request.getParameter("user");
            String pass = request.getParameter("pass");
            User user = dao.login(userName, pass); // Get the user's role
            
            if (user != null) {
                session.setAttribute("user", user); // Store user in session
                session.setAttribute("userID", user.getID()); 
                session.setAttribute("roleID", user.getRoleID());
                session.setAttribute("username", user.getUserName());
                session.setAttribute("createBy", user.getCreateBy());
                session.setAttribute("storeID", user.getStoreID());
                
                if (user.getIsDelete()) {
                    request.setAttribute("message", "Tài khoản của bạn đã bị cấm.");
                    dao.dispatch(request, response, "login/login.jsp");
                    return;
                }

                // New condition for roleID
                if (user.getRoleID() == 1) {
                    dao.dispatch(request, response, "listusers");
                } else {
                    // Existing logic for other roles
                    switch (user.getRoleID()) {
                        case 2: 
                        case 3: 
                            response.sendRedirect("dashboard");
                            break;
                        default:
                            request.setAttribute("message", "Invalid role.");
                            dao.dispatch(request, response, "login/login.jsp");
                            break;
                    }
                }
            } else {
                request.setAttribute("message", "Email hoặc mật khẩu không đúng.");
                dao.dispatch(request, response, "login/login.jsp");
            }
        }
    }
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
