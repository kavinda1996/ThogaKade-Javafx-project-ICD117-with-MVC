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

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String dateText = txtOrderDate.getText();
        String custID = txtCustID.getText();

        System.out.println("OrderID: " + orderID);
        System.out.println("OrderDate Text: " + dateText);
        System.out.println("CustomerID: " + custID);

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            // check customer exists
            PreparedStatement check = connection.prepareStatement(
                    "SELECT 1 FROM customer WHERE CustID=?");
            check.setString(1, custID);
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Customer " + custID + " does not exist!");
                new Alert(Alert.AlertType.ERROR, "Customer " + custID + " not found!").show();
                return;
            }

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO orders (orderID, orderDate, custID) VALUES (?, ?, ?)");

            ps.setString(1, orderID);
            ps.setDate(2, java.sql.Date.valueOf(LocalDate.parse(dateText)));
            ps.setString(3, custID);

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Inserted: " + result);
        }
        catch (SQLException e) {
            System.out.println("❌ SQL Error:");
            e.printStackTrace();
        }

        btnViewOnAction(event);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();

        try {Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM orders WHERE orderID = ?");

            preparedStatement.setString(1, orderID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        String orderID = txtOrderID.getText();
        String dateText = txtOrderDate.getText();
        String custID = txtCustID.getText();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE orders SET orderDate=?, custID=? WHERE orderID=?");


            LocalDate orderDate = LocalDate.parse(dateText);
            ps.setDate(1, java.sql.Date.valueOf(orderDate));

            ps.setString(2, custID);
            ps.setString(3, orderID);

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Updated: " + result);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Updated Successfully!");
            alert.show();
            orders.clear();
            btnViewOnAction(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
        void btnViewOnAction (ActionEvent event){

            orders.clear();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ORDERS");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Orders orders1 = new Orders(
                            resultSet.getString("orderID"),
                            resultSet.getDate("orderDate").toLocalDate(),
                            resultSet.getString("custID")
                    );
                    orders.add(orders1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
            colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));


            tblOrders.setItems(orders);

        }
    }