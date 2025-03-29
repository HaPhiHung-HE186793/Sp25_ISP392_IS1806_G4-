/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.DAOStore;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class User {
    private int ID;
    private String userName;
    private String userPassword;
    private String email;
    private int roleID;
    private String image;
    private String createAt;
    private String updateAt;
    private int createBy;
    private Boolean isDelete;
    private String deleteAt;
    private int deleteBy;
    private List<String> creatorName;
    private int storeID;

    public User() {
    }

    public User(int ID, String userName, String userPassword, String email, int roleID, String image, String createAt, String updateAt, int createBy, Boolean isDelete, String deleteAt, int deleteBy, int storeID) {
        this.ID = ID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.roleID = roleID;
        this.image = image;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.storeID = storeID;
    }
    
    public User(int ID, String userName, String userPassword, String email, int roleID, String image, String createAt, String updateAt, int createBy, Boolean isDelete, String deleteAt, int deleteBy) {
        this.ID = ID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.roleID = roleID;
        this.image = image;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
    }

    public User(String userName, String userPassword, String email, int roleID, String image, String createAt, String updateAt, int createBy, Boolean isDelete, String deleteAt, int deleteBy) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.roleID = roleID;
        this.image = image;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
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

    public List<String> getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(List<String> creatorName) {
        this.creatorName = creatorName;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    public String getStoreName() {
        DAOStore daos = new DAOStore();
        String name = daos.getStoreNamesByStoreID(storeID);
        return name;
    }
    public List<String> getOwnerName() { 
        DAOStore dao = new DAOStore();
        return dao.getUserNamesByStoreID(storeID);
    }

              
    
    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", userName=" + userName + ", userPassword=" + userPassword + ", email=" + email + ", roleID=" + roleID + ", image=" + image + ", createAt=" + createAt + ", updateAt=" + updateAt + ", createBy=" + createBy + ", isDelete=" + isDelete + ", deleteAt=" + deleteAt + ", deleteBy=" + deleteBy + '}';
    }

    
    
    
}