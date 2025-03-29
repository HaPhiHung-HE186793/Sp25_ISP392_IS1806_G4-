package model;

public class ProductUnits {

    private int unitID;
    private int productID;
    private int unitSize;

    public ProductUnits(int productID, int unitSize) {
        this.productID = productID;
        this.unitSize = unitSize;
    }

    public int getUnitID() {
        return unitID;
    }

    public int getProductID() {
        return productID;
    }

    public int getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(int unitSize) {
        this.unitSize = unitSize;
    }
}
