package repository.impl;

import db.DBConnection;
import model.dto.Item;
import repository.ItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRepositoryImpl implements ItemRepository {

    public ResultSet getAll() throws SQLException {

        String SQL = "SELECT * FROM item";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        ResultSet resultSet = psTm.executeQuery();

        return resultSet;
    }

    @Override
    public void addItem(Item newItem) throws SQLException {
        String SQL = "INSERT INTO item Values(? ,? ,? ,? ,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,newItem.getItemCode());
        psTm.setObject(2,newItem.getDescription());
        psTm.setObject(3,newItem.getPackSize());
        psTm.setObject(4,newItem.getUnitPrice());
        psTm.setObject(5,newItem.getQtyOnHand());

        psTm.executeUpdate();
    }

    @Override
    public void deleteItem(String itemCode) throws SQLException {
        String Sql = "DELETE FROM item WHERE ItemCode = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTM = connection.prepareStatement(Sql);
        psTM.setObject(1,itemCode);
        psTM.executeUpdate();
    }

    @Override
    public void updateItem(Item updateItem) throws SQLException {
        String SQL = "UPDATE item SET Description = ? ,PackSize = ? ,UnitPrice = ? ,QtyOnHand = ? WHERE ItemCode =?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(5,updateItem.getItemCode());
        psTm.setObject(1,updateItem.getDescription());
        psTm.setObject(2,updateItem.getPackSize());
        psTm.setObject(3,updateItem.getUnitPrice());
        psTm.setObject(4,updateItem.getQtyOnHand());
        psTm.executeUpdate();
    }

    @Override
    public ResultSet searchItem(String itemCode, String description) throws SQLException {
        String SQL = "SELECT * FROM item WHERE ItemCode = ? OR Description= ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,itemCode);
        psTm.setObject(2,description);
        ResultSet resultSet = psTm.executeQuery();
        return resultSet;
    }

    @Override
    public boolean updateItemQuantity(String itemCode, int quantity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String SQL = "UPDATE item SET QtyOnHand = QtyOnHand - ? WHERE ItemCode = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setInt(1, quantity);
        preparedStatement.setString(2,itemCode);

        return preparedStatement.executeUpdate() > 0;
    }
}
