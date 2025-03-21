/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.DAOStore;
import java.util.List;

/**
 *
 * @author nguyenanh
 */
public class Store{
    private int storeID;
    private String storeName;
    private String createAt;  
    private String updateAt;
    private int createBy;
    private boolean isDelete;
    private String deleteAt;
    private int deleteBy; 
    private String address;
    private String phone;
    private String email;
    private String logostore;

    // Constructor mặc định
    public Store() {}

    // Constructor đầy đủ
    public Store(int storeID,String storeName, String createAt, String updateAt, int createBy,
                 boolean isDelete, String deleteAt, int deleteBy, String address, String phone,
                 String email, String logostore) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.logostore = logostore;
    }

    public Store(int storeID, String storeName, int createBy, String address, String phone, String email, String logostore) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.createBy = createBy;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.logostore = logostore;
    }    

    public Store(int storeID, String storeName) {
        this.storeID = storeID;
        this.storeName = storeName;
    }
    

public Store( String storeName, int createBy, String address, String phone, String email, String logostore) {
        this.storeName = storeName;
        this.createBy = createBy;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.logostore = logostore;
    }     

    // Getter và Setter

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public boolean getIsDelete() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogostore() {
        return logostore;
    }

    public void setLogostore(String logostore) {
        this.logostore = logostore;
    }
    
    public List<String> getOwnerName() { 
        DAOStore dao = new DAOStore();
        return dao.getUserNamesByStoreID(storeID);
    }
    public String getCreateName() { 
        DAOStore dao = new DAOStore();
        return dao.getUserNamebyID(createBy);
    }
    
    public int CountStaff() { 
        DAOStore dao = new DAOStore();
        return dao.CountStaff(storeID);
    }

    @Override
    public String toString() {
        return "Store{" + "storeID=" + storeID +", storeName=" + storeName + ", createAt=" + createAt + ", updateAt=" + updateAt + ", createBy=" + createBy + ", isDelete=" + isDelete + ", deleteAt=" + deleteAt + ", deleteBy=" + deleteBy + ", address=" + address + ", phone=" + phone + ", email=" + email + ", logostore=" + logostore + '}';
    }
    
    
}

