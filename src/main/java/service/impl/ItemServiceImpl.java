package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.dto.CartItem;
import model.dto.Item;
import model.entity.ItemEntity;
import org.hibernate.Session;
import repository.ItemRepository;
import repository.impl.ItemRepositoryImpl;
import service.ItemService;
import util.HibernateUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    
    ItemRepository itemRepository = new ItemRepositoryImpl();
    
    @Override
    public ObservableList<Item> getAll() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = itemRepository.getAll();
            while (resultSet.next()) {
                itemObservableList.add(new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemObservableList;

//       ------------------------------------
//
//        Hibernate Configuration
//
//        Session session = HibernateUtil.getSession();
//        List<Item> fromItem = session.createQuery("FROM Item").list();
//
//        for (Item item:fromItem){
//            itemObservableList.add(item);
//        }
//
//        return itemObservableList;
    }

    @Override
    public void addItem(Item newItem) {

        try {
            itemRepository.addItem(newItem);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItem(String itemCode) {
        Connection connection = null;
        try {
            itemRepository.deleteItem(itemCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Item updateItem) {
        try {
            itemRepository.updateItem(updateItem);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String itemCode, String description) {
        try {
            ResultSet resultSet = itemRepository.searchItem(itemCode, description);
            resultSet.next();
            return new Item(
                    resultSet.getString("ItemCode"),
                    resultSet.getString("Description"),
                    resultSet.getString("PackSize"),
                    resultSet.getDouble("UnitPrice"),
                    resultSet.getInt("QtyOnHand")
            );
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This ItemCode not in DataBase");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItemQuantity(ObservableList<CartItem> cartItemObservableList) {
        boolean isUpdateQuantity = false;

        for (CartItem cartItem:cartItemObservableList){
            try {
                isUpdateQuantity = itemRepository.updateItemQuantity(cartItem.getItemCode(), cartItem.getQuantity());
                if(isUpdateQuantity == false){
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isUpdateQuantity;
    }
}
