package controller.itemController;

import javafx.collections.ObservableList;
import model.Customer;

public interface ItemManagementService {

    void AddItem(String itemCode,String description,String packSize,Double unitPrice,Integer qtyOnHand);

}
