package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    ItemService itemService = new ItemServiceImpl();

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReload;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

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
    private TableView<Item> tblViewItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtPackSize;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        itemService.addItem(new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())));

        loadItemTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        itemService.deleteItem(txtItemCode.getText());
        loadItemTable();
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadItemTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item searchItem = itemService.searchItem(txtItemCode.getText(), txtDescription.getText());
        txtItemCode.setText(searchItem.getItemCode());
        txtDescription.setText(searchItem.getDescription());
        txtPackSize.setText(searchItem.getPackSize());
        txtUnitPrice.setText(searchItem.getUnitPrice().toString());
        txtQtyOnHand.setText(searchItem.getQtyOnHand().toString());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        System.out.println("Hello");
        itemService.updateItem(new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())));

        loadItemTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tblViewItem.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) -> {
            if(null!=newValue){
                setSelectedItem((Item) newValue);
            }
        })));

        loadItemTable();
    }

    private void setSelectedItem(Item item) {
        txtItemCode.setText(item.getItemCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    private void loadItemTable(){
        tblViewItem.setItems(itemService.getAll());
    }
}
