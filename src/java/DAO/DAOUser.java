/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Entity.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DAOUser extends DBContext {
    private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Sử dụng SHA-256
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException ex) {
        return null;
    }
}
      public int login(String email, String password) {
    String sql = "SELECT roleID FROM Users WHERE email=? AND userPassword=?";
    String hashedPassword = hashPassword(password); // Hash the password

    try {
        PreparedStatement pre = conn.prepareStatement(sql);
        pre.setString(1, email);
        pre.setString(2, hashedPassword); // Use the hashed password
        ResultSet rs = pre.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("roleID");
        }
    } catch (SQLException ex) {
        Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return -1; // Return null if login fails
}


    public int insertUser(User user) {
    int n = 0;
    String sql = "INSERT INTO Users (userName, userPassword, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement pre = conn.prepareStatement(sql);
        pre.setString(1, user.getUserName()); // userName
        pre.setString(2, user.getUserPassword()); // userPassword
        pre.setString(3, user.getEmail()); // email
        pre.setInt(4, user.getRoleID()); // roleID
        pre.setString(5, user.getImage());
        pre.setString(6, user.getCreateAt()); // createAt
        pre.setString(7, user.getUpdateAt()); // updateAt
        pre.setInt(8, user.getCreateBy()); // createBy
        pre.setBoolean(9, user.getIsDelete()); // isDelete
        pre.setString(10, user.getDeleteAt()); // deleteAt
        pre.setInt(11, user.getDeleteBy()); // deleteBy
        n = pre.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
    }
    return n;
}

    public int updateUser(User user) {
    int n = 0;
    String sql = "UPDATE users SET userName=?, userPassword=?, email=?, roleID=?, image=? ,createAt=?, updateAt=?, createBy=?, isDelete=?, deleteAt=?, deleteBy=? WHERE ID=?";
    try {
        PreparedStatement pre = conn.prepareStatement(sql);
        pre.setString(1, user.getUserName()); // userName
        pre.setString(2, user.getUserPassword()); // userPassword
        pre.setString(3, user.getEmail()); // email
        pre.setInt(4, user.getRoleID()); // roleID
        pre.setString(5, user.getImage());
        pre.setString(6, user.getCreateAt()); // createAt
        pre.setString(7, user.getUpdateAt()); // updateAt
        pre.setInt(8, user.getCreateBy()); // createBy
        pre.setBoolean(9, user.getIsDelete()); // isDelete
        pre.setString(10, user.getDeleteAt()); // deleteAt
        pre.setInt(11, user.getDeleteBy()); // deleteBy
        pre.setInt(12, user.getID()); // userID
        n = pre.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
    }
    return n;
}

//    public int removeUser(int userID) {
//        int n = 0;
//        String sql = "DELETE FROM Users WHERE ID=?";
//        try {
//            PreparedStatement pre = conn.prepareStatement(sql);
//            pre.setInt(1, userID);
//            n = pre.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return n;
//    }

//    public Vector<User> getUsers(String sql) {
//    Vector<User> vector = new Vector<User>();
//    try {
//        Statement state = conn.createStatement();
//        ResultSet rs = state.executeQuery(sql);
//        while (rs.next()) {
//            int userID = rs.getInt("ID");
//            String username = rs.getString("userName");
//            String password = rs.getString("userPassword");
//            String email = rs.getString("email");
//            int roleID = rs.getInt("roleID"); // Giả sử role là kiểu int
//            String image = rs.getString("image");
//            String createAt = rs.getString("createAt");
//            String updateAt = rs.getString("updateAt");
//            int createBy = rs.getInt("createBy");
//            Boolean isDelete = rs.getBoolean("isDelete");
//            String deleteAt = rs.getString("deleteAt");
//            int deleteBy = rs.getInt("deleteBy");
//
//            User user = new User(roleID, username, password, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
//            vector.add(user);
//        }
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//    }
//    return vector;
//}
    public List<User> getUsers(String sql) {
    List<User> list = new ArrayList<>();
    try {
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {
            int userID = rs.getInt("ID");
            String username = rs.getString("userName");
            String password = rs.getString("userPassword");
            String email = rs.getString("email");
            int roleID = rs.getInt("roleID"); // Giả sử role là kiểu int
            String image = rs.getString("image");
            String createAt = rs.getString("createAt");
            String updateAt = rs.getString("updateAt");
            int createBy = rs.getInt("createBy");
            Boolean isDelete = rs.getBoolean("isDelete");
            String deleteAt = rs.getString("deleteAt");
            int deleteBy = rs.getInt("deleteBy");

            User user = new User(roleID, username, password, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
            list.add(user);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return list;
}
    public List<User> listUsers() {
        String sql = "SELECT * FROM Users";
         List<User> UsersList = new ArrayList<User>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
            int userID = rs.getInt("ID");
            String username = rs.getString("userName");
            String password = rs.getString("userPassword");
            String email = rs.getString("email");
            int roleID = rs.getInt("roleID"); // Giả sử role là kiểu int
            String image = rs.getString("image");
            String createAt = rs.getString("createAt");
            String updateAt = rs.getString("updateAt");
            int createBy = rs.getInt("createBy");
            Boolean isDelete = rs.getBoolean("isDelete");
            String deleteAt = rs.getString("deleteAt");
            int deleteBy = rs.getInt("deleteBy");

            UsersList.add(new User(userID, username, password, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return UsersList;     
}
    
    public List<User> getUsersByRole(int roleId) {
    String sql = "SELECT * FROM Users WHERE roleID = ?";
    List<User> usersList = new ArrayList<>();
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, roleId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            int userID = rs.getInt("ID");
            String username = rs.getString("userName");
            String password = rs.getString("userPassword");
            String email = rs.getString("email");
            int roleID = rs.getInt("roleID");
            String image = rs.getString("image");
            String createAt = rs.getString("createAt");
            String updateAt = rs.getString("updateAt");
            int createBy = rs.getInt("createBy");
            Boolean isDelete = rs.getBoolean("isDelete");
            String deleteAt = rs.getString("deleteAt");
            int deleteBy = rs.getInt("deleteBy");

            usersList.add(new User(userID, username, password, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } 
    return usersList;     
}
    
    
    public List<User> getUsersByKeyword(String keyword) {
    String sql = "SELECT * FROM Users WHERE userName LIKE ? OR email LIKE ?";
    List<User> usersList = new ArrayList<>();
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%"); // Tìm kiếm username chứa keyword
        ps.setString(2, "%" + keyword + "%"); // Tìm kiếm email chứa keyword
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            int userID = rs.getInt("ID");
            String username = rs.getString("userName");
            String password = rs.getString("userPassword");
            String email = rs.getString("email");
            int roleID = rs.getInt("roleID");
            String image = rs.getString("image");
            String createAt = rs.getString("createAt");
            String updateAt = rs.getString("updateAt");
            int createBy = rs.getInt("createBy");
            Boolean isDelete = rs.getBoolean("isDelete");
            String deleteAt = rs.getString("deleteAt");
            int deleteBy = rs.getInt("deleteBy");

            usersList.add(new User(userID, username, password, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } 
    return usersList;     
}


    public List<User> getUsersByRoleAndKeyword(int roleID, String keyword) {
    String sql = "SELECT * FROM Users WHERE roleID = ? AND (userName LIKE ? OR email LIKE ?)";
    List<User> usersList = new ArrayList<>();

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, roleID);
        ps.setString(2, "%" + keyword + "%");
        ps.setString(3, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            usersList.add(new User(
                rs.getInt("ID"), rs.getString("userName"), rs.getString("userPassword"),
                rs.getString("email"), rs.getInt("roleID"), rs.getString("image"),
                rs.getString("createAt"), rs.getString("updateAt"),
                rs.getInt("createBy"), rs.getBoolean("isDelete"),
                rs.getString("deleteAt"), rs.getInt("deleteBy")
            ));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return usersList;
}


    public static void main(String[] args) {
      DAOUser dao = new DAOUser();

     //1. Thêm một người dùng mới
//        User newUser = new User( "nhat", "123", "anh13.9.04@gmail.com", 1,"nhat.jsp" ,"2023-01-01", "2023-01-01", 1, false, null, 0);
//        int insertResult = dao.insertUser(newUser);
//        System.out.println("Insert result: " + insertResult);
String a = "nhat";
System.out.println(dao.getUsersByRoleAndKeyword(1, a));

//        User userToUpdate = new User();
//        userToUpdate.setID(2); // ID của người dùng cần cập nhật
//        userToUpdate.setUserName("minhPhung"); // Thay đổi userName thành "minh2"
//        userToUpdate.setUserPassword("password123"); // Giữ nguyên password
//        userToUpdate.setEmail("quangminhsao3009@gmail.com"); // Giữ nguyên email
//        userToUpdate.setRoleID(2); // Giữ nguyên roleID
//        userToUpdate.setImage("minh.jsp");
//        userToUpdate.setCreateAt("2023-01-01 00:00:00.000"); // Giữ nguyên createAt
//        userToUpdate.setUpdateAt("2023-01-01 00:00:00.000"); // Giữ nguyên updateAt
//        userToUpdate.setCreateBy(1); // Giữ nguyên createBy
//        userToUpdate.setIsDelete(false); // Giữ nguyên isDelete
//        userToUpdate.setDeleteAt(null); // Giữ nguyên deleteAt
//        userToUpdate.setDeleteBy(0); // Giữ nguyên deleteBy
//        int updateResult = dao.updateUser(userToUpdate);
//        System.out.println("Update result: " + updateResult);

        // 3. Xóa một người dùng
      //  int removeResult = dao.removeUser(2); // Giả sử ID của người dùng cần xóa là 1
        

        // 4. Liệt kê tất cả người dùng
 //       dao.listAll();

}

}
