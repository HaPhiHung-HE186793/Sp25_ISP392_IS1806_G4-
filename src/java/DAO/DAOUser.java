/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import model.User;
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

    public User getCurrentUser(String userName) {
        String sql = "SELECT * FROM Users WHERE userName = ?";
        User user = null;

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, userName);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                int userID = rs.getInt("ID");
                String email = rs.getString("email");
                String password = rs.getString("userPassword");
                int roleID = rs.getInt("roleID");
                String image = rs.getString("image");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                user = new User(userID, userName, password, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // In lỗi ra console nếu có lỗi xảy ra
        }

        return user; // Trả về user nếu tìm thấy, nếu không thì null
    }

    public String hashPassword(String password) {
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

    public User login(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email=? AND userPassword=?";
        String hashedPassword = hashPassword(password); // Hash the password

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, email);
            pre.setString(2, hashedPassword);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("ID"),
                        rs.getString("userName"),
                        rs.getString("userPassword"),
                        rs.getString("email"),
                        rs.getInt("roleID"),
                        rs.getString("image"),
                        rs.getString("createAt"),
                        rs.getString("updateAt"),
                        rs.getInt("createBy"),
                        rs.getBoolean("isDelete"),
                        rs.getString("deleteAt"),
                        rs.getInt("deleteBy")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Trả về null nếu không tìm thấy user
    }
    
    public boolean updatePassword(int userId, String newPassword) {
    String sql = "UPDATE Users SET userPassword = ? WHERE ID = ?";
    String hashedPassword = hashPassword(newPassword); // Hash mật khẩu mới

    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, hashedPassword);
        pre.setInt(2, userId);

        int rowsUpdated = pre.executeUpdate();
        return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException ex) {
        Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false; // Trả về false nếu cập nhật thất bại
}

    
    public boolean updateUserName(int userId, String newUserName) {
    String sql = "UPDATE Users SET userName = ? WHERE ID = ?";

    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, newUserName);
        pre.setInt(2, userId);

        int rowsUpdated = pre.executeUpdate();
        return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException ex) {
        Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false; // Trả về false nếu cập nhật thất bại
}

    public int insertUser(User user) {
        int n = 0;
        String sql = "INSERT INTO Users (userName, userPassword, email, roleID, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getUserName()); // userName
            // pre.setString(2, user.getUserPassword()); // userPassword
            pre.setString(2, hashPassword(user.getUserPassword()));
            pre.setString(3, user.getEmail()); // email
            pre.setInt(4, user.getRoleID()); // roleID       
            pre.setString(5, user.getCreateAt()); // createAt
            pre.setString(6, user.getUpdateAt()); // updateAt
            pre.setInt(7, user.getCreateBy()); // createBy
            pre.setBoolean(8, user.getIsDelete()); // isDelete
            pre.setString(9, user.getDeleteAt()); // deleteAt
            pre.setInt(10, user.getDeleteBy()); // deleteBy
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertNewUser(User user) {
        int n = 0;
        String sql = "INSERT INTO Users (userName, userPassword, email, roleID, createBy) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getUserName()); // userName
            pre.setString(2, hashPassword(user.getUserPassword())); // userPassword
            pre.setString(3, user.getEmail()); // email
            pre.setInt(4, user.getRoleID()); // roleID
            pre.setInt(5, user.getCreateBy()); // createBy
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

    public int updateUser2(User user) {
        int n = 0;
        String sql = "UPDATE Users SET userName = ?, userPassword = ?, email = ?, roleID = ?, updateAt = CURRENT_TIMESTAMP, createBy = ? WHERE ID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, user.getUserName()); // userName
            pre.setString(2, hashPassword(user.getUserPassword())); // userPassword (hashed)
            pre.setString(3, user.getEmail()); // email
            pre.setInt(4, user.getRoleID()); // roleID
            pre.setInt(5, user.getCreateBy()); // createBy (người chỉnh sửa)
            pre.setInt(6, user.getID()); // ID của user cần cập nhật

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateUser3(User user) {
        int n = 0;
        String sql = "UPDATE Users SET userName = ?, email = ?, roleID = ?, updateAt = CURRENT_TIMESTAMP, createBy = ? WHERE ID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, user.getUserName()); // userName
            pre.setString(2, user.getEmail()); // email
            pre.setInt(3, user.getRoleID()); // roleID
            pre.setInt(4, user.getCreateBy()); // createBy (người chỉnh sửa)
            pre.setInt(5, user.getID()); // ID của user cần cập nhật

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int banUserByEmail(String email) {
        String sql = "UPDATE Users SET isDelete = ? WHERE email = ?";
        int result = 0;

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setBoolean(1, true); // Đặt isDelete thành true để ban người dùng
            pre.setString(2, email);
            result = pre.executeUpdate(); // Thực thi cập nhật
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result; // Trả về số hàng bị ảnh hưởng
    }

    public boolean isUserBanned(int userId) {
        String sql = "SELECT isDelete FROM Users WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isDelete");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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

    public User getUserbyID(int id) {
        User user = null;  // Khởi tạo biến user là null
        String sql = "SELECT * FROM users WHERE ID = " + id;  // Câu truy vấn tìm user theo ID
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);

            // Kiểm tra nếu có kết quả trả về từ cơ sở dữ liệu
            if (rs.next()) {
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

                // Tạo đối tượng User từ dữ liệu truy vấn
                user = new User(userID, username, password, email, roleID, image, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;  // Trả về đối tượng user nếu tìm thấy, hoặc null nếu không tìm thấy
    }

    public void blockUserByID(int id, int deleteByID) {
        String updateSql = "UPDATE users SET isDelete = 1, deleteBy = ?, deleteAt = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, deleteByID);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void unlockUserByID(int id, int createByID) {
        String updateSql = "UPDATE users SET isDelete = 0, deleteBy = NULL, deleteAt = NULL, createBy = ?, updateAt = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, createByID);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    public List<User> listUsersByOwner(int createBy) {
        String sql = "SELECT * FROM Users WHERE createBy = ?";
        List<User> usersList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, createBy);
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
                int createdBy = rs.getInt("createBy");
                boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                usersList.add(new User(userID, username, password, email, roleID, image, createAt, updateAt, createdBy, isDelete, deleteAt, deleteBy));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersList;
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

    
    public List<User> getUsersByRole(int roleId, List<User> U) {
        List<User> users = new ArrayList<>();
        for (User user : U) {
            if (user.getRoleID() == roleId) {
                users.add(user);
            }
        }
        return users;
    }

    public List<User> getUsersByKeyword(String keyword, List<User> U) {
        List<User> users = new ArrayList<>();
        for (User user : U) {
            if (user.getUserName().contains(keyword) || user.getEmail().contains(keyword)) {
                users.add(user);
            }
        }
        return users;
    }
    
    public List<User> getUsersByKeyword(String keyword) {
        String sql = "SELECT * FROM Users WHERE userName LIKE ? OR email LIKE ?";
        List<User> usersList = new ArrayList<>();
        String trimmedKeyword = keyword.trim();
        if (trimmedKeyword.isEmpty()) {
            trimmedKeyword = "!@#$%^&*"; // Một chuỗi chắc chắn không khớp với bất kỳ user nào
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + trimmedKeyword + "%"); // Tìm kiếm username chứa keyword
            ps.setString(2, "%" + trimmedKeyword + "%"); // Tìm kiếm email chứa keyword
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

    public List<User> getUsersByKeywordAndOwner(String keyword, int createByID) {
        String sql = "SELECT * FROM Users WHERE (userName LIKE ? OR email LIKE ?) AND createBy = ?";
        List<User> usersList = new ArrayList<>();
        String trimmedKeyword = keyword.trim();
        if (trimmedKeyword.isEmpty()) {
            trimmedKeyword = "!@#$%^&*"; // Một chuỗi chắc chắn không khớp với bất kỳ user nào
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + trimmedKeyword + "%"); // Tìm kiếm theo userName
            ps.setString(2, "%" + trimmedKeyword + "%"); // Tìm kiếm theo email
            ps.setInt(3, createByID); // Lọc theo createBy

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

    public List<User> getUsersByRoleAndKeyword(int roleID, String keyword, boolean[] isKeywordSearchEmpty) {
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
        if (usersList.isEmpty()) {
            isKeywordSearchEmpty[0] = true; // Gán biến cờ để servlet biết không có kết quả từ khóa
            usersList = getUsersByRole(roleID);
        } else {
            isKeywordSearchEmpty[0] = false; // Có kết quả từ khóa
        }
        return usersList;
    }

    public boolean isEmailExists(String email) {
        if (conn == null) { // Kiểm tra kết nối trước khi thực hiện truy vấn
            System.out.println("Database connection is NULL!");
            return false;
        }
        String query = "SELECT COUNT(*) FROM Users WHERE email = ? AND isDelete = 0";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error in isEmailExists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        DAOUser dao = new DAOUser();
        System.out.println(dao.getUsersByKeywordAndOwner("      ", 15));
        //1. Thêm một người dùng mới
//<<<<<<< Updated upstream

////        User newUser = new User( "nhat", "123", "anh13.9.04@gmail.com", 1,"nhat.jsp" ,"2023-01-01", "2023-01-01", 1, false, null, 0);
//        int insertResult = dao.insertUser(newUser);
////        System.out.println("Insert result: " + insertResult);
//String a = "nhat";
//System.out.println(dao.getUsersByRoleAndKeyword(1, a));
//if (dao.isEmailExists("anh13.9.04@gmail.com")) {
//            System.out.println("Email đã tồn tại!");
//        }
//else System.out.println("khong ton tai");
//=======
//        User newUser = new User( "hung", "password123", "hunghphe186793@fpt.edu.vn", 1,"hung.jsp" ,"2023-01-01", "2023-01-01", "1", "0", null, null);
//        int insertResult = dao.insertUser(newUser);
//        System.out.println("Insert result: " + insertResult);
//>>>>>>> Stashed changes
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
