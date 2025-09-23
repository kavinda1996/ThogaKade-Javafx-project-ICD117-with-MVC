package controller.itemController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Item;

import java.sql.*;

public class ItemManagementController implements ItemManagementService{
    @Override
    public void AddItem(String itemCode, String description, String packSize, Double unitPrice, Integer qtyOnHand) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1234");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ITEM VALUES(?,?,?,?,?)")) {


            preparedStatement.setObject(1, itemCode);
            preparedStatement.setObject(2, description);
            preparedStatement.setObject(3, packSize);
            preparedStatement.setObject(4, unitPrice);
            preparedStatement.setObject(5, qtyOnHand);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void DeleteItem(String itemCode) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            String sql = "DELETE FROM item WHERE ItemCode=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, itemCode);

            System.out.println("Trying to delete item with code: " + itemCode);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("No item found with ID: " + itemCode);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void UpdateItem(Item item) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {
            String sql = "UPDATE item SET ItemCode=?, description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE ItemCode=?";
            PreparedStatement ps = connection.prepareStatement(sql);


            ps.setString(1, item.getItemCode());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getPackSize());
            ps.setDouble(4, item.getUnitPrice());
            ps.setInt(5, item.getQtyOnHand());
            ps.setString(6, item.getItemCode());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("No item found with ID: " + item.getItemCode());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> getAllItem() {
        ObservableList<Item> item= FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ITEM");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Item i = new Item(
                        resultSet.getString("itemCode"),
                        resultSet.getString("description"),
                        resultSet.getString("packSize"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("qtyOnHand")
                );
                item.add(i);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

}
