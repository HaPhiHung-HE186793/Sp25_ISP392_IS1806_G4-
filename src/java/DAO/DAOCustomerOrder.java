package DAO;

import DAL.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.CustomerOrder;

/**
 *
 * @author ADMIN
 */
public class DAOCustomerOrder extends DBContext {
    public List<CustomerOrder> getCustomerOrder(String sql) {
        List<CustomerOrder> list = new ArrayList<CustomerOrder>();
        try {
            System.out.println("Executing SQL: " + sql); // Ghi lại câu lệnh SQL
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                CustomerOrder customerOrder = new CustomerOrder(orderID, name, email, phone);           
                list.add(customerOrder);
            }
        } catch (SQLException ex) {
            System.err.println("SQL Error in getShowOrder: " + ex.getMessage());
            ex.printStackTrace(); // Ghi lại lỗi SQL
        }
        return list;
    }
}