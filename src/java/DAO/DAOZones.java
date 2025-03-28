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
import model.Zones;
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
    public List<Zones> listZones(String id) {
        List<Zones> list = new ArrayList<>();
        String sql = "SELECT * FROM zones where storeID = ?"; // Cập nhật tên bảng
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id); // Truyền tham số vào dấu ?
            ResultSet rs = pre.executeQuery(); // Thực thi truy vấn
            while (rs.next()) {
                int zoneID = rs.getInt("zoneID");
                String zoneName = rs.getString("zoneName");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                Boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int store = rs.getInt("storeID");

                Zones zone = new Zones(zoneID, zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, store);

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
        String sql = "INSERT INTO zones (zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy,storeID) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, zone.getZoneName());
            pre.setString(2, zone.getCreateAt());
            pre.setString(3, zone.getUpdateAt());
            pre.setInt(4, zone.getCreateBy());
            pre.setBoolean(5, zone.isIsDelete());
            pre.setString(6, zone.getDeleteAt());
            pre.setInt(7, zone.getDeleteBy());
            pre.setInt(8, zone.getStoreID());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOZones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void listAll(String storeID) {
        String sql = "SELECT * FROM zones where storeID = ?"; // Cập nhật tên bảng
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
                int store = rs.getInt("storeID");

                Zones zone = new Zones(zoneID, zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, store);
                System.out.println(zone);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Zones> getEmptyZones(int storeID) {
        List<Zones> list = new ArrayList<>();
        String sql = "SELECT * FROM zones WHERE (productID IS NULL OR productID = 0) AND storeID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeID);  // Thiết lập tham số storeID

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int zoneID = rs.getInt("zoneID");
                    String zoneName = rs.getString("zoneName");
                    String createAt = rs.getString("createAt");
                    String updateAt = rs.getString("updateAt");
                    int createBy = rs.getInt("createBy");
                    Boolean isDelete = rs.getBoolean("isDelete");
                    String deleteAt = rs.getString("deleteAt");
                    int deleteBy = rs.getInt("deleteBy");
                    String image = rs.getString("image");

                    Zones zone = new Zones(zoneID, zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, image);
                    list.add(zone);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean updateZoneWithProduct(int zoneID, int productID, int storeID) {
        String sql = "UPDATE zones SET productID = ? WHERE zoneID = ? AND storeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productID);
            ps.setInt(2, zoneID);
            ps.setInt(3, storeID);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Integer> getSelectedZoneIDsByProductID(int productID) {
        List<Integer> selectedZoneIDs = new ArrayList<>();
        String sql = "SELECT zoneID FROM zones WHERE productID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                selectedZoneIDs.add(rs.getInt("zoneID"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return selectedZoneIDs;
    }

    public List<Zones> listAll1(int storeID) {
        List<Zones> zoneList = new ArrayList<>();
        String sql = "SELECT * FROM zones WHERE storeID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeID); // Gán giá trị storeID vào câu lệnh SQL
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int zoneID = rs.getInt("zoneID");
                String zoneName = rs.getString("zoneName");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int store = rs.getInt("storeID");
                String image = rs.getString("image");
                String navigation = rs.getString("description");
                Zones zone = new Zones(zoneID, zoneName, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeID, image, navigation);
                zoneList.add(zone); // Thêm zone vào danh sách
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return zoneList; // Trả về danh sách zone
    }

    public static void main(String[] args) {
        DAOZones dao = new DAOZones();

        // 1. Thêm một khu vực mới
//        Zones newZone = new Zones( "Zone C", "2023-01-01", "2023-01-01", 1, false, null, 0);
//        int insertResult = dao.insertZone(newZone);
//        System.out.println("Insert result: " + insertResult);
//        // 2. Cập nhật thông tin khu vực
//        zones zoneToUpdate = new zones(2, "Zone AB", "2023-01-02", "2023-01-02", 1, false, null, 0);
//        int updateResult = dao.updateZone(zoneToUpdate);
//        System.out.println("Update result: " + updateResult);
//
//        // 3. Xóa một khu vực
        int removeResult = dao.removeZone(1); // Giả sử ID của khu vực cần xóa
//        System.out.println("Remove result: " + removeResult);

        // 4. Liệt kê tất cả khu vực
//        dao.listAll();
    }
}
