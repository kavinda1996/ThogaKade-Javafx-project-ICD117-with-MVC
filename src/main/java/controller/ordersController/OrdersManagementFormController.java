package controller.ordersController;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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



    @FXML
    void btnAddOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        LocalDate orderDate = LocalDate.parse(txtOrderDate.getText());
        String custID = txtCustID.getText();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1234");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ORDERS VAlUES(?,?,?)");
            preparedStatement.setObject(1,orderID);
            preparedStatement.setObject(2,orderDate);
            preparedStatement.setObject(3,custID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        btnViewOnAction(event);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderDetailManagementOnAction(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/order_detail_management_form.fxml"))));
        stage.show();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {
        ObservableList<Orders> orders = FXCollections.observableArrayList();
        orders.clear();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ORDERS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Orders orders1= new Orders(
                        resultSet.getString("orderID"),
                        resultSet.getDate("orderDate").toLocalDate(),
                resultSet.getString("custID")
                );
                orders.add(orders1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        colCustID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("custID"));


        tblOrders.setItems(orders);
    }

}
