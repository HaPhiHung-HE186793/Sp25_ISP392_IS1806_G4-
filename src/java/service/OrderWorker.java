/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import DAO.DAODebtRecords;
import model.OrderTask;
import model.OrderItems;
import DAO.DAOOrders;
import DAO.DAOOrderItems;
import DAO.DAOProduct;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DebtRecords;

/**
 *
 * @author Admin
 */
public class OrderWorker extends Thread {

    private static volatile boolean isRunning = false; // Đảm bảo chỉ chạy 1 Worker duy nhất

    // Lưu trạng thái đơn hàng mới nhất theo userId
    private static final ConcurrentHashMap<Integer, Integer> processedOrders = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, String> orderErrors = new ConcurrentHashMap<>();

    public void run() {
        isRunning = true; // Khi Worker chạy, đánh dấu là true
        while (true) {
            if (!OrderQueue.isEmpty()) {
                OrderTask orderTask = OrderQueue.takeOrder();
                try {
                    processOrder(orderTask);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Thread.sleep(500); // Nghỉ 0.5s rồi tiếp tục xử lý
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processOrder(OrderTask orderTask) throws SQLException {

        // Kiểm tra loại đơn hàng (Nhập kho hoặc Xuất kho)
        int orderType = Integer.parseInt(orderTask.getOrderType());
        BigDecimal calculatedTotalAmount = BigDecimal.ZERO;

        BigDecimal totalAmount = new BigDecimal(orderTask.getTotalAmount());

        BigDecimal paidAmount = new BigDecimal(orderTask.getPaidAmount());

        BigDecimal debtAmount = new BigDecimal(orderTask.getDebtAmount());
        
        int paymentStatus;
        
        if(orderType ==0){
            
           
            paymentStatus = 2;
        
        }else{
            paymentStatus = 0;

        for (OrderItems detail : orderTask.getOrderDetails()) {
            int quantity = DAOProduct.INSTANCE.getProductQuantity(detail.getProductID());
            // Lấy giá sản phẩm thật từ database
            BigDecimal actualUnitPrice = DAOProduct.INSTANCE.getProductPrice(detail.getProductID());
            // Giá do client gửi lên
            BigDecimal receivedUnitPrice = new BigDecimal(detail.getUnitPrice());
            BigDecimal receivedPrice = new BigDecimal(detail.getPrice());
            BigDecimal discount = new BigDecimal(detail.getDiscount());

            if (detail.getQuantity() > quantity) {
                processedOrders.put(orderTask.getUserId(), -1); // Đánh dấu lỗi bằng -1

                return;

            }
             if (actualUnitPrice.compareTo(receivedUnitPrice) != 0) {
                processedOrders.put(orderTask.getUserId(), -1); // Đánh dấu lỗi bằng -1

                return;

            }
            // Tính lại tổng tiền dựa trên giá thực tế từ DB
            BigDecimal expectedPrice = actualUnitPrice.multiply(BigDecimal.valueOf(detail.getQuantity())).subtract(discount);
            if (receivedPrice.compareTo(expectedPrice) != 0) {

                processedOrders.put(orderTask.getUserId(), -1); // Lưu trạng thái lỗi
                return;
            }

            calculatedTotalAmount = calculatedTotalAmount.add(expectedPrice);
           

        }

        if (calculatedTotalAmount.compareTo(totalAmount) != 0) {

            processedOrders.put(orderTask.getUserId(), -1); // Lưu trạng thái lỗi
            return;
        }
        
        }

        // int orderId = DAOOrders.INSTANCE.createOrder(customerId,userId,userId, totalOrderPrice, porter, status);
        int orderId = DAOOrders.INSTANCE.createOrder(orderTask.getCustomerId(), orderTask.getUserId(), orderTask.getUserId(), totalAmount, orderTask.getPorter(), orderTask.getStatus(), orderType, paidAmount);
        if (debtAmount.compareTo(BigDecimal.ZERO) != 0) {

            DebtRecords debtRecord = new DebtRecords(orderTask.getCustomerId(), orderId, orderTask.getDebtAmount(), paymentStatus, orderTask.getUserId(), false);
            DAODebtRecords dao = new DAODebtRecords();
            // cần xử lí thêm việc tạo nợ có cần thành công không
            dao.addDebtRecordFromOrder(debtRecord);

        }
        for (OrderItems detail : orderTask.getOrderDetails()) {
            int quantity = DAOProduct.INSTANCE.getProductQuantity(detail.getProductID());

            BigDecimal price = new BigDecimal(detail.getPrice());
            BigDecimal unitPrice = new BigDecimal(detail.getUnitPrice());

            DAOOrderItems.INSTANCE.createOrderItem(orderId, detail.getProductID(), detail.getProductName(), price, unitPrice, detail.getQuantity());

           
                
                
         // Cập nhật kho hàng
            if (orderType==1) {
                //  Xuất kho: Giảm số lượng sản phẩm trong kho
                DAOProduct.INSTANCE.exportProductQuantity(detail.getProductID(), detail.getQuantity());

            } else if (orderType==0) {
                //  Nhập kho: Tăng số lượng sản phẩm trong kho
                DAOProduct.INSTANCE.importProductQuantity(detail.getProductID(), detail.getQuantity());
                   
                
            }
            
        }

        
processedOrders.put(orderTask.getUserId(), orderId);

    }

    public static Integer getProcessedOrder(int userId) {
        return processedOrders.get(userId);
    }

    public static synchronized void startWorker() {
        if (!isRunning) {
            OrderWorker worker = new OrderWorker();
            worker.start(); // Chạy Worker duy nhất
            isRunning = true;
        }
    }

}
