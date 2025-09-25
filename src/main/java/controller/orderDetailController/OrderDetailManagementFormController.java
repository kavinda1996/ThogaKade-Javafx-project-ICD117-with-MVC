package controller.orderDetailController;

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
import model.OrderDetail;

import java.io.IOException;
import java.sql.*;


public class OrderDetailManagementFormController {

    @FXML
    public Button btnBacktoCustomerManagement;
    @FXML
    public JFXTextField txtOrderID;
    @FXML
    public JFXTextField txtOrderQTY;
    @FXML
    public JFXTextField txtDiscount;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnView;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colOrderQTY;

    @FXML
    private TableView<OrderDetail> tblOrderDetail;

    @FXML
    private JFXTextField txtItemCode;

  ObservableList<OrderDetail> orderDetail  = FXCollections.observableArrayList();

    OrderDetailManagementService orderDetailManagementService = new OrderDetailManagementController();

    @FXML
    void btnAddOnAction(ActionEvent event) {


        OrderDetail orderDetail = new OrderDetail(
                txtOrderID.getText(),
                txtItemCode.getText(),
                Integer.valueOf(txtOrderQTY.getText()),
                Integer.valueOf(txtDiscount.getText())
        );



       orderDetailManagementService.addOrderDetail(orderDetail);
        btnViewOnAction(event);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String itemCode = txtItemCode.getText();


        orderDetailManagementService.deleteOrderDetail(orderID,itemCode);
        btnViewOnAction(event);
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        OrderDetail orderDetail= new OrderDetail(
                txtOrderID.getText(),
                txtItemCode.getText(),
                Integer.parseInt(txtOrderQTY.getText()),
                Integer.parseInt(txtDiscount.getText())
        );



        orderDetailManagementService.updateOrderDetail(orderDetail);
        btnViewOnAction(event);
    }


    @FXML
    void btnViewOnAction(ActionEvent event) {
        ObservableList<OrderDetail> orderDetail = FXCollections.observableArrayList();
        orderDetail.clear();


       orderDetail=orderDetailManagementService.getAllOrderDetail();

        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderQTY.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tblOrderDetail.setItems(orderDetail);
    }


    public void btnBacktoCustomerManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/customer_management_form.fxml"))));
        stage.show();
    }
}
