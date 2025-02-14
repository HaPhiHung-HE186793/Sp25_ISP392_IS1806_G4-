/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Orders {
    private int orderID;
    private int customerID;
    private int userID;
    private double totalAmount; 
    private String createAt; 
    private String updateAt; 
    private int createBy;
    private boolean isDelete; 
    private String deleteAt; 
    private int deleteBy;
    private int porter; 
    private String status;

    public Orders() {
    }

    public Orders(int orderID, int customerID, int userID, double totalAmount, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy, int porter, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.userID = userID;
        this.totalAmount = totalAmount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.porter = porter;
        this.status = status;
    }

    public Orders(int customerID, int userID, double totalAmount, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy, int porter, String status) {
        this.customerID = customerID;
        this.userID = userID;
        this.totalAmount = totalAmount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.porter = porter;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public int getPorter() {
        return porter;
    }

    public void setPorter(int porter) {
        this.porter = porter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}
