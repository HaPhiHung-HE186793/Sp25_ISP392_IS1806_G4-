/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity.Pagination;

/**
 *
 * @author nguyenanh
 */
public class Pagination {
    private int totalPage;
    private int pageLimit;
    private int currentPage;
    private int totalProduct;
    private int startItem;
    private int lastItem;

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public int getStartItem() {
        return startItem;
    }

    public void setStartItem(int startItem) {
        this.startItem = startItem;
    }

    public int getLastItem() {
        return lastItem;
    }

    public void setLastItem(int lastItem) {
        this.lastItem = lastItem;
    }

    public Pagination(int totalProduct, int pageLimit, int currentPage) {
        this.totalProduct = totalProduct;
        this.pageLimit = pageLimit;
        this.currentPage = currentPage;
        totalPage = totalProduct%pageLimit==0?(totalProduct/pageLimit):(totalProduct/pageLimit+1);
        startItem = (currentPage-1)*pageLimit;
        lastItem = currentPage*pageLimit-1;
    }

    public Pagination() {
        
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
