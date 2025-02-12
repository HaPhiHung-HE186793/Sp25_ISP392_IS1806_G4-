/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
import DAL.DBContext;
import Entity.Zones; 
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOZones extends DBContext {

//    public Vector<zones> getZones(String sql) {
//        Vector<zones> vector = new Vector<zones>();
//        try {
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(sql);
//            while (rs.next()) {
//                int zoneID = rs.getInt("zoneID");
//                String zoneName = rs.getString("zoneName");
//                String createAt = rs.getString("createAt");
//                String updateAt = rs.getString("updateAt");
//                int createBy = rs.getInt("createBy");
//                Boolean isDelete = rs.getBoolean("isDelete");
//                String deleteAt = rs.getString("deleteAt");
//                int deleteBy = rs.getInt("deleteBy");
//
//                zones zone = new zones(zoneID, zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
//                vector.add(zone);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return vector;
//    }
     public List<Zones> getZones(String sql) {
        List<Zones> list = new ArrayList<>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int zoneID = rs.getInt("zoneID");
                String zoneName = rs.getString("zoneName");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Zones zone = new Zones(zoneID, zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                list.add(zone);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int removeZone(int zoneID) {
        int n = 0;
        String sql = "DELETE FROM zones WHERE zoneID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, zoneID);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOZones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateZone(Zones zone) {
        int n = 0;
        String sql = "UPDATE zones SET zoneName=?, createAt=?, updateAt=?, createBy=?, isDelete=?, deleteAt=?, deleteBy=? WHERE zoneID=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, zone.getZoneName());
            pre.setString(2, zone.getCreateAt());
            pre.setString(3, zone.getUpdateAt());
            pre.setInt(4, zone.getCreateBy());
            pre.setBoolean(5, zone.isIsDelete());
            pre.setString(6, zone.getDeleteAt());
            pre.setInt(7, zone.getDeleteBy());
            pre.setInt(8, zone.getZoneID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOZones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int insertZone(Zones zone) {
        int n = 0;
        String sql = "INSERT INTO zones (zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, zone.getZoneName());
            pre.setString(2, zone.getCreateAt());
            pre.setString(3, zone.getUpdateAt());
            pre.setInt(4, zone.getCreateBy());
            pre.setBoolean(5, zone.isIsDelete());
            pre.setString(6, zone.getDeleteAt());
            pre.setInt(7, zone.getDeleteBy());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOZones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void listAll() {
        String sql = "SELECT * FROM zones"; // Cập nhật tên bảng
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int zoneID = rs.getInt("zoneID");
                String zoneName = rs.getString("zoneName");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");

                Zones zone = new Zones(zoneID, zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy);
                System.out.println(zone);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAOZones dao = new DAOZones();

        // 1. Thêm một khu vực mới
        Zones newZone = new Zones( "Zone C", "2023-01-01", "2023-01-01", 1, false, null, 0);
        int insertResult = dao.insertZone(newZone);
        System.out.println("Insert result: " + insertResult);

//        // 2. Cập nhật thông tin khu vực
//        zones zoneToUpdate = new zones(2, "Zone AB", "2023-01-02", "2023-01-02", 1, false, null, 0);
//        int updateResult = dao.updateZone(zoneToUpdate);
//        System.out.println("Update result: " + updateResult);
//
//        // 3. Xóa một khu vực
        int removeResult = dao.removeZone(1); // Giả sử ID của khu vực cần xóa
//        System.out.println("Remove result: " + removeResult);

        // 4. Liệt kê tất cả khu vực
        dao.listAll();
    }
}
