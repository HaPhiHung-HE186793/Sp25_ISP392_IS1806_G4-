/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class ShowOrder {
    private int orderID;
    private String name;
    private String userName;
    private double totalAmount; 
    private String createAt; 
    private String updateAt;    
    private int porter; 
    private String status;

    public ShowOrder(int orderID, String name, String userName, double totalAmount, String createAt, String updateAt, int porter, String status) {
        this.orderID = orderID;
        this.name = name;
        this.userName = userName;
        this.totalAmount = totalAmount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.porter = porter;
        this.status = status;
    }

    public ShowOrder() {
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
