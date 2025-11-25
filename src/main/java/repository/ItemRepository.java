package repository;

import model.dto.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ItemRepository {
    ResultSet getAll() throws SQLException;

    void addItem(Item newItem) throws SQLException;

    void deleteItem(String itemCode) throws SQLException;

    void updateItem(Item updateItem) throws SQLException;

    ResultSet searchItem(String itemCode, String description) throws SQLException;

    boolean updateItemQuantity(String itemCode, int quantity) throws SQLException;
}
