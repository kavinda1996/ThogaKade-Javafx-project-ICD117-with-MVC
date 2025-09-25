package controller.ordersController;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Item;
import model.Orders;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class OrdersManagementFormController {


    @FXML
    public TableView tblOrders;
    @FXML
    public JFXTextField txtOrderID;
    @FXML
    public JFXTextField txtOrderDate;
    @FXML
    public JFXTextField txtCustID;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnOrderDetailManagement;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnView;

    @FXML
    private TableColumn<?, ?> colCustID;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableView<Orders> tblItems;

    ObservableList<Orders> orders = FXCollections.observableArrayList();

    OrdersManagementService ordersManagementService= new OrdersManagementController();

    @FXML
    void btnAddOnAction(ActionEvent event) {

        Orders orders = new Orders(
                txtOrderID.getText(),
                Date.valueOf(txtOrderDate.getText()).toLocalDate(),
                txtCustID.getText()
        );


        ordersManagementService.addOrders(orders);

        btnViewOnAction(event);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String custID = txtCustID.getText();


       ordersManagementService.deleteOrders(orderID,custID);
        btnViewOnAction(event);
    }

    @FXML
    void btnOrderDetailManagementOnAction(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/order_detail_management_form.fxml"))));
        stage.show();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        orders.clear();

        Orders orders = new Orders(
                txtOrderID.getText(),
                Date.valueOf(txtOrderDate.getText()).toLocalDate(),
                txtCustID.getText()
        );


        ordersManagementService.updateOrders(orders);

        btnViewOnAction(event);
    }

    @FXML
        void btnViewOnAction (ActionEvent event){

            orders.clear();


            ordersManagementService.getAllOrders();

            colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
            colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));


            tblOrders.setItems(orders);

        }
    }