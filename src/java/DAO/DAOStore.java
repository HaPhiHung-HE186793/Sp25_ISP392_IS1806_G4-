/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;

import model.ShowOrder;
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
import model.Store;
import model.User;

/**
 *
 * @author nguyenanh
 */
public class DAOStore extends DBContext {

    public int createStore(Store store) {
        int n = 0;
        String sql = "INSERT INTO store (ownerID, storeName, address, phone, email, logostore, createBy) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, store.getOwnerID()); // ownerID
            pre.setString(2, store.getStoreName()); // storeName
            pre.setString(3, store.getAddress()); // address
            pre.setString(4, store.getPhone()); // phone
            pre.setString(5, store.getEmail()); // email
            pre.setString(6, store.getLogostore()); // logostore
            pre.setInt(7, store.getCreateBy()); // createBy

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateStore2(Store store) {
        int n = 0;
        String sql = "UPDATE store SET storeName = ?, address = ?, phone = ?, email = ?, logostore = ?, updateAt = CURRENT_TIMESTAMP, createBy = ? WHERE storeID = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, store.getStoreName()); // storeName
            pre.setString(2, store.getAddress()); // address
            pre.setString(3, store.getPhone()); // phone
            pre.setString(4, store.getEmail()); // email
            pre.setString(5, store.getLogostore()); // logostore
            pre.setInt(6, store.getCreateBy()); // createBy (người chỉnh sửa)
            pre.setInt(7, store.getStoreID()); // storeID của cửa hàng cần cập nhật

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Store getStoreById(int storeID) {
        Store store = null;
        String sql = "SELECT * FROM store WHERE storeID = ?";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, storeID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int ownerID = rs.getInt("ownerID");
                String storeName = rs.getString("storeName");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String logostore = rs.getString("logostore");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                int isDelete = rs.getInt("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                store = new Store(storeID, ownerID, storeName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, address, phone, email, logostore);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return store;
    }

    public List<Store> listStore() {
        List<Store> storeList = new ArrayList<>();
        String sql = "SELECT * FROM store";

        try (PreparedStatement pre = conn.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {

            while (rs.next()) {
                int storeID = rs.getInt("storeID");
                int ownerID = rs.getInt("ownerID");
                String storeName = rs.getString("storeName");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String logostore = rs.getString("logostore");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                int isDelete = rs.getInt("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Store store = new Store(storeID, ownerID, storeName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, address, phone, email, logostore);
                storeList.add(store);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return storeList;
    }

    public String getUserNamebyID(int id) {
        String username = null;  // Khởi tạo biến user là null
        String sql = "SELECT * FROM users WHERE ID = " + id;  // Câu truy vấn tìm user theo ID
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);

            // Kiểm tra nếu có kết quả trả về từ cơ sở dữ liệu
            if (rs.next()) {
                 username = rs.getString("userName");                              
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return username;  // Trả về đối tượng user nếu tìm thấy, hoặc null nếu không tìm thấy
    }

}
