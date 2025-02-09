/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import DAO.DAOUser;
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
        HttpSession session=request.getSession(true);
        DAOUser dao = new DAOUser();
        String service = request.getParameter("service");
        if (service == null) { 
            service = "listUser";
        }
        try (PrintWriter out = response.getWriter()) {
             if (service.equals("logoutUser")) {
                session.invalidate();
                dao.dispatch(request, response, "login.jsp");
            } 

             else if (service.equals("loginUser")) {
    String userName = request.getParameter("user");
    String pass = request.getParameter("pass");
    int roleID = dao.login(userName, pass); // Get the user's role
    
    if (roleID != -1) {
        session.setAttribute("userName", userName);
        session.setAttribute("roleID", roleID); // Store role in session
        
        switch (roleID) {
    case 1: 
        dao.dispatch(request, response, "/dashboard/homeAdmin.jsp");
        break;
    case 2: 
        dao.dispatch(request, response, "/dashboard/homeOwner.jsp");
        break;
    case 3: // Sửa thành int
        dao.dispatch(request, response, "/dashboard/homeStaff.jsp");
        break;
    default:
        request.setAttribute("message", "Invalid role.");
        dao.dispatch(request, response, "/login.jsp");
        break;
}
    } else {
        request.setAttribute("message", "Your username or password is invalid.");
        dao.dispatch(request, response, "/login.jsp");
    }
}
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
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
private String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");     
            md.update(password.getBytes());      
            byte[] digest = md.digest();     
            StringBuilder sb = new StringBuilder();     
            for (byte b : digest) {         
                sb.append(String.format("%02x", b));
                
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException ex){
            return null;
        }
    }
}
