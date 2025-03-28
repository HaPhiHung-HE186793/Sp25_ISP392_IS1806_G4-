/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Zones {

    private int zoneID;
    private String zoneName;
    private String createAt; // Có thể sử dụng LocalDateTime nếu cần
    private String updateAt; // Có thể sử dụng LocalDateTime nếu cần
    private int createBy;
    private boolean isDelete; // Giả sử isDelete là kiểu int
    private String deleteAt; // Có thể sử dụng LocalDateTime nếu cần
    private int deleteBy;
    private int storeID;
    private String image;
    // Thêm các thuộc tính liên quan đến sản phẩm
    private String productName;
    private String productImage;
    private int quantity;
    private int productID;
    private String navigation;
    private int ZoneQuantities;

    public Zones() {
    }

    public Zones(int zoneID, String zoneName, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy, int storeID) {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.storeID = storeID;
    }

    public Zones(int zoneID, String zoneName, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy, String image) {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.image = image;
    }

    // Constructor với đầy đủ thông tin zone và product
    public Zones(int zoneID, String zoneName, String createAt, String updateAt, int createBy, boolean isDelete,
            String deleteAt, int deleteBy, String image, int productID, String productName, String productImage, int quantity, String navigation) {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.image = image;
        this.productID = productID;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.navigation = navigation;
    }

    public Zones(String zoneName, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy) {
        this.zoneName = zoneName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.navigation = navigation;
    }

    public Zones(int zoneID, String zoneName, String createAt, String updateAt, int createBy, boolean isDelete, String deleteAt, int deleteBy, int storeID, String image, String navigation) {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
        this.deleteBy = deleteBy;
        this.storeID = storeID;
        this.image = image;
        this.navigation = navigation;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }

    public int getZoneQuantities() {
        return ZoneQuantities;
    }

    public void setZoneQuantities(int ZoneQuantities) {
        this.ZoneQuantities = ZoneQuantities;
    }

    @Override
    public String toString() {
        return "Zones{" + "zoneID=" + zoneID + ", zoneName=" + zoneName + ", createAt=" + createAt + ", updateAt=" + updateAt + ", createBy=" + createBy + ", isDelete=" + isDelete + ", deleteAt=" + deleteAt + ", deleteBy=" + deleteBy + ", storeID=" + storeID + '}';
    }

}
