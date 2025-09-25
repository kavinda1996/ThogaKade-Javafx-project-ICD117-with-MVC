package controller.orderDetailController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.OrderDetail;

import java.sql.*;

public class OrderDetailManagementController implements OrderDetailManagementService{

    @Override
    public void addOrderDetail(OrderDetail orderDetail) {

        try {Connection connection = DBConnection.getInstance().getConnection();


            PreparedStatement check = connection.prepareStatement(
                    "SELECT 1 FROM orderDetail WHERE orderID=?");
            check.setString(1, orderDetail.getOrderID());
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Item " + orderDetail.getItemCode() + " does not exist!");
                new Alert(Alert.AlertType.ERROR, "Item " + orderDetail.getItemCode() + " not found!").show();
                return;
            }

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO orderDetail (orderID, itemCode, orderQTY,discount) VALUES (?, ?, ?, ?)");

            ps.setString(1, orderDetail.getOrderID());
            ps.setString(2,orderDetail.getItemCode());
            ps.setString(3, String.valueOf(orderDetail.getOrderQTY()));
            ps.setString(4, String.valueOf(orderDetail.getDiscount()));

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Inserted: " + result);
        }
        catch (SQLException e) {
            System.out.println("❌ SQL Error:");
            e.printStackTrace();
        }


    }

    @Override
    public void deleteOrderDetail(String orderID, String itemCode) {
        try {Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM orderDetail WHERE orderID = ? AND itemCode = ?");

            ps.setString(1, orderID);
            ps.setString(2, itemCode);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Deleted Successfully!");
            } else {
                System.out.println("⚠ No matching record found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {

        try {Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE orderDetail SET orderQTY=?, discount=? WHERE orderID=? AND itemCode=?");


            ps.setInt(1, orderDetail.getOrderQTY());
            ps.setInt(2, orderDetail.getDiscount());
            ps.setString(3, orderDetail.getOrderID());
            ps.setString(4, orderDetail.getItemCode());

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Updated: " + result);

            if(result > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "OrderDetail Updated Successfully!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No matching OrderDetail found!");
                alert.show();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<OrderDetail> getAllOrderDetail() {
        ObservableList<OrderDetail> orderDetail = FXCollections.observableArrayList();
        try { Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orderDetail");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail od = new OrderDetail(
                        rs.getString("orderID"),
                        rs.getString("itemCode"),
                        rs.getInt("orderQTY"),
                        rs.getInt("discount")
                );
                orderDetail.add(od);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }
}
