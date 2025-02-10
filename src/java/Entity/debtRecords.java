/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ADMIN
 */
public class debtRecords {
    private int debtID;
    private int customerID;
    private int orderID;
    private double amount; 
    private int paymentStatus;
    private String createAt; 
    private String updateAt; 
    private String createBy;
    private int isDelete; 
    private String deleteAt; 
    private String deleteBy;

    public debtRecords(int debtID, int customerID, int orderID, double amount, int paymentStatus, String createAt, String updateAt, String createBy, int isDelete, String deleteAt, String deleteBy) {
        this.debtID = debtID;
        this.customerID = customerID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
    }

    public debtRecords() {
    }

    public debtRecords(int customerID, int orderID, double amount, int paymentStatus, String createAt, String updateAt, String createBy, int isDelete, String deleteAt, String deleteBy) {
        this.customerID = customerID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
    }

    public int getDebtID() {
        return debtID;
    }

    public void setDebtID(int debtID) {
        this.debtID = debtID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }
    
}
