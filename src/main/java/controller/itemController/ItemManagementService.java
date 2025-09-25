package controller.itemController;

import javafx.collections.ObservableList;

import model.Item;

public interface ItemManagementService {

    void AddItem(Item item1);

    default void DeleteItem(String itemCode) {

    }

    void UpdateItem (Item item);

    ObservableList<Item> getAllItem();

}
