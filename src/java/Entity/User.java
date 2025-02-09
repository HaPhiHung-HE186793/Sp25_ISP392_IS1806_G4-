/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

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
    private String createBy;
    private String isDelete;
    private String deleteAt;
    private String deleteBy;

    
    public User() {
    }

    public User(String userName, String userPassword, String email, int roleID, String image, String createAt, String updateAt, String createBy, String isDelete, String deleteAt, String deleteBy) {
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
    
    public User(int ID, String userName, String userPassword, String email, int roleID, String image, String createAt, String updateAt, String createBy, String isDelete, String deleteAt, String deleteBy) {
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
    
    public User(String userName, String userPassword, String email, int roleID, String createAt, String updateAt, String createBy, String isDelete, String deleteAt, String deleteBy) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.roleID = roleID;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
    }

    public User(int ID, String userName, String userPassword, String email, int roleID, String createAt, String updateAt, String createBy, String isDelete, String deleteAt, String deleteBy) {
        this.ID = ID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.roleID = roleID;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
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

    @Override
    public String toString() {
        return "User{" + " userName=" + userName + ", userPassword=" + userPassword + ", email=" + email + ", roleID=" + roleID + ", createAt=" + createAt + ", updateAt=" + updateAt + ", createBy=" + createBy + ", isDelete=" + isDelete + ", deleteAt=" + deleteAt + ", deleteBy=" + deleteBy + '}';
    }
    
   
    
}
