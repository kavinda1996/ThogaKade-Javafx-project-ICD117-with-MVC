package controller.itemController;

import javafx.collections.ObservableList;

import model.Item;

public interface ItemManagementService {

    void AddItem(String itemCode,String description,String packSize,Double unitPrice,Integer qtyOnHand);

    default void DeleteItem(String itemCode) {

    }

    void UpdateItem (Item item);

    ObservableList<Item> getAllItem();

}
