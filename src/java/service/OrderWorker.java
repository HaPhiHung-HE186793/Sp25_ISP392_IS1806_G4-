/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

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
/**
 *
 * @author Admin
 */
public class OrderWorker extends Thread{
      private static volatile boolean isRunning = false; // Đảm bảo chỉ chạy 1 Worker duy nhất
      
      // Lưu trạng thái đơn hàng mới nhất theo userId
    private static final ConcurrentHashMap<Integer, Integer> processedOrders = new ConcurrentHashMap<>();
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
        //int orderId = OrderDAO.createOrder(orderTask.getUserId()); // 1️⃣ Tạo hóa đơn
        
        BigDecimal totalAmount = new BigDecimal(orderTask.getTotalAmount());
         // Kiểm tra loại đơn hàng (Nhập kho hoặc Xuất kho)
        String orderType = orderTask.getOrderType(); 

        
    // int orderId = DAOOrders.INSTANCE.createOrder(customerId,userId,userId, totalOrderPrice, porter, status);
int orderId = DAOOrders.INSTANCE.createOrder(orderTask.getCustomerId(),orderTask.getUserId(),orderTask.getUserId(),totalAmount, orderTask.getPorter(), orderTask.getStatus());


        for (OrderItems detail : orderTask.getOrderDetails()) {
          BigDecimal price = new BigDecimal(detail.getPrice());
          BigDecimal unitPrice = new BigDecimal(detail.getUnitPrice());
          
        DAOOrderItems.INSTANCE.createOrderItem(orderId, detail.getProductID(), detail.getProductName(),price,unitPrice, detail.getQuantity(),detail.getDescription());

      
      

         // Cập nhật kho hàng
            if ("export".equalsIgnoreCase(orderType)) {
                //  Xuất kho: Giảm số lượng sản phẩm trong kho
                DAOProduct.INSTANCE.exportProductQuantity(detail.getProductID(), detail.getQuantity());

            } else if ("import".equalsIgnoreCase(orderType)) {
                //  Nhập kho: Tăng số lượng sản phẩm trong kho
                DAOProduct.INSTANCE.importProductQuantity(detail.getProductID(), detail.getQuantity());
                   
                
            }
        }

       // Đánh dấu đơn hàng của nhân viên đã hoàn thành
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
