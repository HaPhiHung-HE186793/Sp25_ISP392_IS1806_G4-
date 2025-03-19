/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class OrderPaper {
    private int orderID;
    private int orderitemID;
    private String productName;
    private Double price; 
    private int quantity;
    private String createAt;
    private String name;
    private String userName;
    private int orderType;
    private int storeID;
    private Double unitPrice;
    private Double paidAmount;
    private Double totalAmount;

    public OrderPaper() {
    }

    public OrderPaper(int orderID, int orderitemID, String productName, Double price, int quantity, String createAt, String name, String userName, int orderType, int storeID, Double unitPrice, Double paidAmount, Double totalAmount) {
        this.orderID = orderID;
        this.orderitemID = orderitemID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.createAt = createAt;
        this.name = name;
        this.userName = userName;
        this.orderType = orderType;
        this.storeID = storeID;
        this.unitPrice = unitPrice;
        this.paidAmount = paidAmount;
        this.totalAmount = totalAmount;
    }

    

    public OrderPaper(int orderID, int orderitemID, String productName, Double price, int quantity, String createAt, String name, String userName, int orderType, int storeID) {
        this.orderID = orderID;
        this.orderitemID = orderitemID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.createAt = createAt;
        this.name = name;
        this.userName = userName;
        this.orderType = orderType;
        this.storeID = storeID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderitemID() {
        return orderitemID;
    }

    public void setOrderitemID(int orderitemID) {
        this.orderitemID = orderitemID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
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

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    
    
}
