package DAO;

import DAL.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Entity.User;

public class DAOForget extends DBContext {
    
    // Kiểm tra xem email có tồn tại trong database hay không
    public User checkEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("ID")); 
                user.setUserName(rs.getString("userName")); 
                user.setUserPassword(rs.getString("userPassword")); 
                user.setEmail(rs.getString("email"));
                user.setRoleID(rs.getInt("roleID")); 
                user.setCreateAt(rs.getString("createAt")); 
                user.setUpdateAt(rs.getString("updateAt")); 
                user.setCreateBy(rs.getInt("createBy")); 
                user.setIsDelete(rs.getBoolean("isDelete")); 
                user.setDeleteAt(rs.getString("deleteAt"));
                user.setDeleteBy(rs.getInt("deleteBy")); 
            
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOForget.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Trả về null nếu email không tồn tại
    }


    public boolean updatePassword(String email, String password) {
    String sql = "UPDATE [dbo].[users] SET [userPassword] = ? WHERE [email] = ?";
    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, password);
        pre.setString(2, email);
        int rowsUpdated = pre.executeUpdate();
        return rowsUpdated > 0; // Trả về true nếu có ít nhất một hàng được cập nhật
    } catch (SQLException ex) {
        Logger.getLogger(DAOForget.class.getName()).log(Level.SEVERE, null, ex);
        return false; // Trả về false nếu có lỗi xảy ra
    }
}
public static void main(String[] args) {
    DAOForget dao = new DAOForget(); // Thay đổi thành DAOForget
    String emailToUpdate = "admin@gmail.com"; // Địa chỉ email của người dùng cần cập nhật
    String newPassword = "newPassword123"; // Mật khẩu mới

    // Gọi phương thức updatePassword và kiểm tra kết quả
    boolean updateSuccess = dao.updatePassword(emailToUpdate, newPassword);

    if (updateSuccess) {
        System.out.println("Mật khẩu đã được cập nhật thành công.");
    } else {
        System.out.println("Cập nhật mật khẩu không thành công.");
    }
    
    // Nếu cần, có thể gọi thêm các phương thức khác
    // dao.listAll(); // Nếu bạn cần hiển thị tất cả người dùng
}
}
