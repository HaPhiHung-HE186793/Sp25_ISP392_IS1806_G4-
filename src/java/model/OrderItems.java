/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class OrderItems {
    private int orderitemID;
    private int orderID;
    private int productID;
    private String productName;
    private double price;      
    private double unitPrice;  
    private int quantity;
    private String description;

    public OrderItems() {
    }

    public OrderItems(int orderitemID, int orderID, int productID, String productName, double price, double unitPrice, int quantity, String description) {
        this.orderitemID = orderitemID;
        this.orderID = orderID;
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.description = description;
    }

    public OrderItems(int orderID, int productID, String productName, double price, double unitPrice, int quantity, String description) {
        this.orderID = orderID;
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.description = description;
    }

    public int getOrderitemID() {
        return orderitemID;
    }

    public void setOrderitemID(int orderitemID) {
        this.orderitemID = orderitemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
