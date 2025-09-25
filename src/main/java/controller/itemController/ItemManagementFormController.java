package controller.itemController;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Item;


import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;



public class ItemManagementFormController implements Initializable {

    public Button btnOrderManagement;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnView;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    ObservableList<Item> item = FXCollections.observableArrayList();

    @FXML
    void btnAddOnAction(ActionEvent event) {


        Item item1=new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.valueOf(txtUnitPrice.getText()),
                Integer.valueOf(txtQtyOnHand.getText())
        );

        ItemManagementService itemManagementService = new ItemManagementController();
        itemManagementService.AddItem(item1);

        btnViewOnAction(event);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String itemCode = txtItemCode.getText().trim();
        if (!itemCode.isEmpty()) {
            ItemManagementService itemManagementService = new ItemManagementController();
            itemManagementService.DeleteItem(itemCode);
            btnViewOnAction(event);
        } else {
            System.out.println("⚠ No item selected to delete!");
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            Item selectedItem = tblItem.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {


                selectedItem.setItemCode(txtItemCode.getText());
                selectedItem.setDescription(txtDescription.getText());
                selectedItem.setPackSize(txtPackSize.getText());
                selectedItem.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                selectedItem.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));

                ItemManagementService itemManagementService= new ItemManagementController();
                itemManagementService.UpdateItem(selectedItem);

                btnViewOnAction(event);

            } else {
                new Alert(Alert.AlertType.ERROR, "⚠ No item selected!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Update failed: " + e.getMessage()).show();
        }
    }


    @FXML
    void btnViewOnAction(ActionEvent event) {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        item.clear();

        ItemManagementService itemManagementService = new ItemManagementController();
        item=itemManagementService.getAllItem();

        tblItem.setItems(item);

    }

    public void btnOrderManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/orders_management_form.fxml"))));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblItem.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtItemCode.setText(newVal.getItemCode());
            }
        });
    }
}
