package service;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Item;

public interface ItemService {

    ObservableList<Item> getAll();

    void addItem(Item newItem);

    void deleteItem(String itemCode);

    void updateItem(Item updateItem);

    Item searchItem(String itemCode, String description);

    boolean updateItemQuantity(ObservableList<CartItem> cartItemObservableList);
}
