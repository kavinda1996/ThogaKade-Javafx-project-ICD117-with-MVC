package controller.ordersController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Orders;

import java.sql.*;


public class OrdersManagementController implements OrdersManagementService{


    @Override
    public void addOrders(Orders orders) {
        try { Connection connection = DBConnection.getInstance().getConnection();


            PreparedStatement check = connection.prepareStatement(
                    "SELECT 1 FROM customer WHERE CustID=?");
            check.setString(1, orders.getCustID());
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Customer " + orders.getCustID() + " does not exist!");
                new Alert(Alert.AlertType.ERROR, "Customer " + orders.getCustID() + " not found!").show();
                return;
            }

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO orders (orderID, orderDate, custID) VALUES (?, ?, ?)");

            ps.setString(1, orders.getOrderID());
            ps.setDate(2, Date.valueOf(orders.getOrderDate()));
            ps.setString(3, orders.getCustID());

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Inserted: " + result);
        }
        catch (SQLException e) {
            System.out.println("❌ SQL Error:");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrders( String orderID) {

        try { Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM orders WHERE orderID = ? ");

            preparedStatement.setString(1, orderID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrders(Orders orders) {

        try {Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE orders SET orderDate=?, custID=? WHERE orderID=?");


            ps.setDate(1, Date.valueOf(orders.getOrderDate()));

            ps.setString(2, orders.getCustID());
            ps.setString(3, orders.getOrderID());

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Updated: " + result);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Updated Successfully!");
            alert.show();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Orders> getAllOrders() {
        ObservableList<Orders> orders = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ORDERS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Orders orders1 = new Orders(
                        resultSet.getString("orderID"),
                        resultSet.getDate("orderDate").toLocalDate(),
                        resultSet.getString("custID")
                );
                orders.add(orders1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("table not found");
        }
        return orders;
    }
}
