package controller.itemController;

import javafx.collections.ObservableList;
import model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
