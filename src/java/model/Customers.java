/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class Customers {
    private int customerID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private BigDecimal totalDebt;  
    private String createAt;   
    private String updateAt;   
    private int createBy;
    private boolean isDelete;  
    private String deleteAt;   
    private int deleteBy;
    private int storeID;
    

    public Customers() {
    }

    public Customers(int customerID, String name, String email, String phone, String address, BigDecimal totalDebt, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy, int storeID) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.totalDebt = totalDebt;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.storeID = storeID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(BigDecimal totalDebt) {
        this.totalDebt = totalDebt;
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

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    @Override
    public String toString() {
        return "Customers{" + "customerID=" + customerID + ", name=" + name + ", email=" + email + ", phone=" + phone + ", address=" + address + ", totalDebt=" + totalDebt + ", createAt=" + createAt + ", updateAt=" + updateAt + ", createBy=" + createBy + ", isDelete=" + isDelete + ", deleteAt=" + deleteAt + ", deleteBy=" + deleteBy + ", storeID=" + storeID + '}';
    }

    
    
}