/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ADMIN
 */
public class customers {
    private int customerID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double totalDebt;  
    private String createAt;   
    private String updateAt;   
    private int createBy;
    private boolean isDelete;  
    private String deleteAt;   
    private int deleteBy;

    public customers() {
    }

    public customers(int customerID, String name, String email, String phone, String address, double totalDebt, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy) {
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
    }

    public customers(String name, String email, String phone, String address, double totalDebt, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy) {
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

    public double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(double totalDebt) {
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

    
    
}