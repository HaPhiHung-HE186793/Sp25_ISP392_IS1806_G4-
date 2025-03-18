/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class BestSeller {
    private String name;
    private Double price;
    private int storeID;
    private String productName;

    public BestSeller() {
    }

    public BestSeller(String name, Double price, int storeID) {
        this.name = name;
        this.price = price;
        this.storeID = storeID;
    }

    public BestSeller(Double price, int storeID, String productName) {
        this.price = price;
        this.storeID = storeID;
        this.productName = productName;
    }

    public BestSeller(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public BestSeller(Double price, String productName) {
        this.price = price;
        this.productName = productName;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    
}
