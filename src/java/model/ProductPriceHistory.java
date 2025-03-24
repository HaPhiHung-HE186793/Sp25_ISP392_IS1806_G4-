/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProductPriceHistory {
    private int historyID;
    private int productID;
    private String productName;
    private String image;
    private double price;
    private String priceType;
    private String changedAt;
    private String changedBy;
    private String supplier;


    public ProductPriceHistory() {
    }

    public ProductPriceHistory(int historyID, int productID, String productName, String image, double price, String priceType, String changedAt, String changedBy, String supplier) {
        this.historyID = historyID;
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.priceType = priceType;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
        this.supplier = supplier;
    }
    
    public ProductPriceHistory(int productID, String productName,double price, String priceType, String changedAt, String changedBy, String supplier) {
        
        this.productID = productID;
        this.productName = productName;
       
        this.price = price;
        this.priceType = priceType;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
        this.supplier = supplier;
    }
    

    public ProductPriceHistory(int historyID, int productID, String productName, String image, double price, String priceType, String changedAt, String changedBy) {
        this.historyID = historyID;
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.priceType = priceType;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
    }
    
    
     public ProductPriceHistory(int productID, String productName, double price, String priceType, String changedAt, String changedBy) {
       
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.priceType = priceType;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(String changedAt) {
        this.changedAt = changedAt;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

   

    

    
}
