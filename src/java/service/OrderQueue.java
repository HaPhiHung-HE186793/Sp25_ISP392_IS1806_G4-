/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import utils.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import model.OrderTask;


/**
 *
 * @author Admin
 */
public class OrderQueue {
    
    private static final BlockingQueue<OrderTask> queue = new LinkedBlockingQueue<>();
    
     // Thêm yêu cầu vào hàng đợi
    public static void addOrder(OrderTask orderTask) {
        try {
            queue.put(orderTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    // Lấy yêu cầu, lấy từng hóa đơn ra  từ hàng đợi để xử lí
    public static OrderTask takeOrder() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static boolean isEmpty() {
        return queue.isEmpty();
    }
    
    
    
}
