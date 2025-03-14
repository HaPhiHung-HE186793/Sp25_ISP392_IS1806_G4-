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
        System.out.println("🚀 Đang xử lý đơn hàng của user ID: " + orderTask.getUserId());

        // Kiểm tra loại đơn hàng (Nhập kho hoặc Xuất kho)
        int orderType = Integer.parseInt(orderTask.getOrderType());
        System.out.println("📌 Loại đơn hàng: " + (orderType == 0 ? "Nhập kho" : "Xuất kho"));

        BigDecimal calculatedTotalAmount = BigDecimal.ZERO;

        BigDecimal totalAmount = new BigDecimal(orderTask.getTotalAmount());
        System.out.println("💰 Tổng tiền đơn hàng: " + totalAmount);

        BigDecimal paidAmount = new BigDecimal(orderTask.getPaidAmount());
        System.out.println("💳 Số tiền đã thanh toán: " + paidAmount);

        Double debtAmount = orderTask.getDebtAmount();
        System.out.println("📉 Số tiền nợ: " + debtAmount);

        int paymentStatus=-1;
        //nhập
        if (orderType == 0) {// nhập

           
            
             if (debtAmount< 0) {
                paymentStatus = 2; // chủ đi vay
                 debtAmount = debtAmount * (-1);
               
                System.out.println("📉 Số tiền nợ: " + debtAmount);
                
            } else if (debtAmount > 0) {
                paymentStatus = 3; // Chủ đi trả
            }

        } else {// xuất
            
            if (debtAmount< 0) {
                paymentStatus = 0; // Khách nợ chủ
                debtAmount = debtAmount * (-1); // Đổi dấu thành số dương
                System.out.println("📉 Số tiền nợ: " + debtAmount);
                
            } else if (debtAmount > 0) {
                paymentStatus = 1; // khách trả nợ
            }

            System.out.println("📦 Đơn hàng xuất kho, kiểm tra tồn kho và giá...");

            
            

        }
        System.out.println("📥 Đang tạo đơn hàng trong DB...");

        int orderId = DAOOrders.INSTANCE.createOrder(orderTask.getCustomerId(), orderTask.getUserId(), orderTask.getUserId(), totalAmount, orderTask.getStatus(), orderType, paidAmount);
        if (orderId <= 0) {
            System.out.println("❌ Lỗi: Không thể tạo đơn hàng trong DB!");
            processedOrders.put(orderTask.getUserId(), -1);

            return;
        }
        System.out.println("✅ Đơn hàng đã tạo thành công! Order ID: " + orderId);

        if (debtAmount != 0) {
            System.out.println("📄 Đang tạo bản ghi nợ...");

            DebtRecords debtRecord = new DebtRecords(orderTask.getCustomerId(), orderId,debtAmount, paymentStatus, orderTask.getUserId(), false);

            DAODebtRecords dao = new DAODebtRecords();
            // cần xử lí thêm việc tạo nợ có cần thành công không
            Boolean success = dao.addDebtRecordFromOrder(debtRecord);
            if (success == false) {
                processedOrders.put(orderTask.getUserId(), -1);

                return;
            }

            System.out.println("✅ Bản ghi nợ đã tạo thành công!");

        }
        System.out.println("🔄 Đang thêm sản phẩm vào đơn hàng...");

        for (OrderItems detail : orderTask.getOrderDetails()) {

            BigDecimal price = new BigDecimal(detail.getPrice());
            BigDecimal unitPrice = new BigDecimal(detail.getUnitPrice());

            DAOOrderItems.INSTANCE.createOrderItem(orderId, detail.getProductID(), detail.getProductName(), price, unitPrice, detail.getQuantity());
            System.out.println("✅ Đã thêm sản phẩm vào đơn hàng - ID: " + detail.getProductID());

            // Cập nhật kho hàng
            if (orderType == 1) {
                //  Xuất kho: Giảm số lượng sản phẩm trong kho
                System.out.println("📉 Xuất kho - Giảm số lượng tồn kho cho sản phẩm ID: " + detail.getProductID());

                DAOProduct.INSTANCE.exportProductQuantity(detail.getProductID(), detail.getQuantity());

            } else if (orderType == 0) {
                //  Nhập kho: Tăng số lượng sản phẩm trong kho
                DAOProduct.INSTANCE.importProductQuantity(detail.getProductID(), detail.getQuantity());

            }

        }

        processedOrders.put(orderTask.getUserId(), orderId);
        System.out.println("✅ [DONE] Đơn hàng đã xử lý xong! User ID: " + orderTask.getUserId() + " | Order ID: " + orderId);

    }

    public static Integer getProcessedOrder(int userId) {
        Integer status = processedOrders.get(userId);
        System.out.println("📡 Kiểm tra trạng thái đơn hàng cho user ID: " + userId + " | Trạng thái: " + (status == null ? "Đang xử lý" : status));
        return status;
    }

    public static synchronized void startWorker() {
        if (!isRunning) {
            OrderWorker worker = new OrderWorker();
            worker.start(); // Chạy Worker duy nhất
            isRunning = true;
        }
    }

    public static void clearProcessedOrder(int userId) {
        processedOrders.remove(userId);
    }

}
