/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.DAOStore;

/**
 *
 * @author nguyenanh
 */
public class Schedule {
    private int scheduleID;
    private String scheduleName;
    private String startDate;
    private String endDate;
    private String breakStart;
    private String breakEnd;
    private String createAt;
    private String updateAt;
    private int createBy;
    private boolean isDelete;
    private String deleteAt;
    private int deleteBy;
    private int storeid;
    private String workDuration;

    public Schedule(int scheduleID, String scheduleName, String startDate, String endDate, String breakStart, String breakEnd, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy, int storeid, String workDuration) {
        this.scheduleID = scheduleID;
        this.scheduleName = scheduleName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.storeid = storeid;
        this.workDuration = workDuration;
    }

    public Schedule(String scheduleName, String startDate, String endDate, String breakStart, String breakEnd, int createBy, int storeid) {
        this.scheduleName = scheduleName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.createBy = createBy;
        this.storeid = storeid;
    }      
    public Schedule(String scheduleName, String startDate, String endDate, String breakStart, String breakEnd, boolean isDelete, int createBy, int storeid) {
        this.scheduleName = scheduleName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDelete = isDelete;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.createBy = createBy;
        this.storeid = storeid;
    }   

    public Schedule(int scheduleID, String scheduleName, String startDate, String endDate, String breakStart, String breakEnd, boolean isDelete) {
        this.scheduleID = scheduleID;
        this.scheduleName = scheduleName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.isDelete = isDelete;
    }
    
    

    public Schedule() {
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(String breakStart) {
        this.breakStart = breakStart;
    }

    public String getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(String breakEnd) {
        this.breakEnd = breakEnd;
    }
    

    public String getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }        

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public boolean isIsDelete() {
        return isDelete;
    }
    public boolean setIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    public int getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(int deleteBy) {
        this.deleteBy = deleteBy;
    }

    public int getStoreid() {
        return storeid;
    }

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }
    
    public String getCreateName() { 
        DAOStore dao = new DAOStore();
        return dao.getUserNamebyID(createBy);
    }
    
    
}
