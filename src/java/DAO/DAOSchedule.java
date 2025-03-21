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
import model.Schedule;
import model.Store;
import model.User;

/**
 *
 * @author nguyenanh
 */
public class DAOSchedule extends DBContext {

    public List<Schedule> listSchedule(int store) {
        List<Schedule> scheduleList = new ArrayList<>();
        String sql = "SELECT scheduleID, scheduleName, startDate, endDate, breakStart, breakEnd, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeid, workDuration FROM schedule where storeid = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, store);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int scheduleID = rs.getInt("scheduleID");
                String scheduleName = rs.getString("scheduleName");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String breakStart = rs.getString("breakStart");
                String breakEnd = rs.getString("breakEnd");
                String createAt = rs.getString("createAt");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeid = rs.getInt("storeid");
                String workDuration = rs.getString("workDuration");

                Schedule schedule = new Schedule(scheduleID, scheduleName, startDate, endDate, breakStart, breakEnd, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeid, workDuration);
                scheduleList.add(schedule);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return scheduleList;
    }

    public int createSchedule(Schedule schedule) {
        int n = 0;
        String sql = "INSERT INTO schedule (scheduleName, startDate, endDate, breakStart, breakEnd, createBy, isDelete,storeid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, schedule.getScheduleName());
            pre.setString(2, schedule.getStartDate());
            pre.setString(3, schedule.getEndDate());
            pre.setString(4, schedule.getBreakStart());
            pre.setString(5, schedule.getBreakEnd());
            pre.setInt(6, schedule.getCreateBy());
            pre.setBoolean(7, schedule.isIsDelete());
            pre.setInt(8, schedule.getStoreid());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSchedule.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    public int updateSchedule(Schedule schedule) {
    int n = 0;
    String sql = "UPDATE schedule SET scheduleName = ?, startDate = ?, endDate = ?, breakStart = ?, breakEnd = ?, updateAt = CURRENT_TIMESTAMP, isDelete = ? WHERE scheduleID = ?";

    try (PreparedStatement pre = conn.prepareStatement(sql)) {
        pre.setString(1, schedule.getScheduleName());
        pre.setString(2, schedule.getStartDate());
        pre.setString(3, schedule.getEndDate());
        pre.setString(4, schedule.getBreakStart());
        pre.setString(5, schedule.getBreakEnd());
        pre.setBoolean(6, schedule.isIsDelete()); // Thêm trường isDelete
        pre.setInt(7, schedule.getScheduleID()); // shiftId vẫn ở cuối câu lệnh

        n = pre.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(DAOSchedule.class.getName()).log(Level.SEVERE, null, ex);
    }

    return n;
}


    public Schedule getScheduleById(int scheduleID) {
        Schedule schedule = null;
        String sql = "SELECT * FROM schedule WHERE scheduleID = ?";

        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, scheduleID);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                String scheduleName = rs.getString("scheduleName");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String breakStart = rs.getString("breakStart");
                String breakEnd = rs.getString("breakEnd");
                String createAt = rs.getString("createAt");
                String workDuration = rs.getString("workDuration");
                String updateAt = rs.getString("updateAt");
                int createBy = rs.getInt("createBy");
                boolean isDelete = rs.getBoolean("isDelete");
                String deleteAt = rs.getString("deleteAt");
                int deleteBy = rs.getInt("deleteBy");
                int storeid = rs.getInt("storeid");

                schedule = new Schedule(scheduleID, scheduleName, startDate, endDate, breakStart, breakEnd, createAt, updateAt, createBy, isDelete, deleteAt, deleteBy, storeid, workDuration);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return schedule;
    }

    public void blockScheduleByID(int id, int deleteByID) {
        String updateSql = "UPDATE schedule SET isDelete = 1, deleteBy = ?, deleteAt = CURRENT_TIMESTAMP WHERE scheduleid = ?";
        try {
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, deleteByID);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void unblockScheduleByID(int id) {
        String updateSql = "UPDATE schedule SET isDelete = 0, deleteBy = NULL, deleteAt = NULL, updateAt = CURRENT_TIMESTAMP WHERE scheduleid = ?";
        try {
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, id);
            updateStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Schedule> getScheduleByKeyword(String keyword, List<Schedule> S) {
        List<Schedule> schedules = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase(); // Chuyển keyword về chữ thường

        for (Schedule schedule : S) {
            if (schedule.getScheduleName().toLowerCase().contains(lowerKeyword)
                    || schedule.getCreateName().toLowerCase().contains(lowerKeyword)) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    public List<Schedule> getScheduleByAction(int selectedAction, List<Schedule> S) {
        List<Schedule> schedules = new ArrayList<>();
        for (Schedule schedule : S) {
            if ((schedule.isIsDelete() ? 1 : 0) == selectedAction) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    public List<Schedule> getScheduleByDate(String startDate, String endDate, List<Schedule> S) {
        List<Schedule> schedules = new ArrayList<>();
        for (Schedule schedule : S) {
            if (schedule.getCreateAt().compareTo(startDate) >= 0 && schedule.getCreateAt().compareTo(endDate) <= 0) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    public static void main(String[] args) {
        DAOSchedule dao = new DAOSchedule();
        System.out.println(dao.getScheduleById(17));
        
    }

}
