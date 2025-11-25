package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.CartItem;
import model.dto.Customer;
import model.dto.Item;
import model.dto.Orders;
import service.*;
import service.impl.PlaceOrderServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXButton btnAddtoCart;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescripstion;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblPrice;

    @FXML
    private TableView<CartItem> tblAddToCart;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtItemCode;

    PlaceOrderService placeOrderService = new PlaceOrderServiceImpl();

    @FXML
    private JFXTextField txtQuantity;

    ObservableList<CartItem> cartItemObservableList = FXCollections.observableArrayList();

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {
        cartItemObservableList.add(new CartItem(
                txtItemCode.getText(),
                lblDescripstion.getText(),
                Integer.parseInt(txtQuantity.getText()),
                Double.parseDouble(lblPrice.getText()),
                Double.parseDouble(lblDiscount.getText()),
                calculateTotal(lblPrice.getText(), txtQuantity.getText())
        ));
        tblAddToCart.setItems(cartItemObservableList);

        clearFields();
        CalculateNetTotal();
    }



    @FXML
    void txtCustomerIdOnAction(ActionEvent event) {
        Customer customer = placeOrderService.getCustomer(txtCustomerId.getText());
        lblCustomerName.setText(customer.getCustomerName());
    }

    @FXML
    void txtItemCodeOnAction(ActionEvent event) {
        Item item = placeOrderService.getItem(txtItemCode.getText());
        lblDescripstion.setText(item.getDescription());
        lblPrice.setText(String.valueOf(item.getUnitPrice()));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        lblDiscount.setText("0.0");
    }

    private double calculateTotal(String unitPrice, String quantity){
        double total = 0.0;
        total = Double.parseDouble(unitPrice) * Integer.parseInt(quantity);
        return total;
    }

    public void clearFields(){
        txtItemCode.setText(null);
        lblDescripstion.setText(null);
        lblPrice.setText(null);
        txtQuantity.setText(null);
        lblDiscount.setText("0.0");
    }

    public void CalculateNetTotal(){
        double netTotal =0.0;
        for (CartItem cartItem: cartItemObservableList){
            netTotal += cartItem.getTotalPrice();
            lblNetTotal.setText(String.valueOf(netTotal));
        }
    }


//    ------------Place Order-----------------

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            placeOrderService.plceOrder(new Orders(
                    txtOrderId.getText(),
                    LocalDate.now(),
                    txtCustomerId.getText()
            ), cartItemObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
