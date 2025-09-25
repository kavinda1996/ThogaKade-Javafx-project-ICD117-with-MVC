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

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String itemCode = txtItemCode.getText();
        Integer orderQTY = Integer.valueOf(txtOrderQTY.getText());
        Integer discount= Integer.valueOf(txtDiscount.getText());


        System.out.println("OrderID: " + orderID);
        System.out.println("ItemCode: " + itemCode);
        System.out.println("OrderQTY: " + orderQTY);
        System.out.println("Discount: " + discount);

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {


            PreparedStatement check = connection.prepareStatement(
                    "SELECT 1 FROM orderDetail WHERE orderID=?");
            check.setString(1, orderID);
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Item " + itemCode + " does not exist!");
                new Alert(Alert.AlertType.ERROR, "Item " + itemCode + " not found!").show();
                return;
            }

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO orderDetail (orderID, itemCode, orderQTY,discount) VALUES (?, ?, ?, ?)");

            ps.setString(1, orderID);
            ps.setString(2,itemCode);
            ps.setString(3, String.valueOf(orderQTY));
            ps.setString(4, String.valueOf(discount));

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
        String itemCode = txtItemCode.getText();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM orderDetail WHERE orderID = ? AND itemCode = ?");

            ps.setString(1, orderID);
            ps.setString(2, itemCode);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Deleted Successfully!");
            } else {
                System.out.println("⚠ No matching record found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnViewOnAction(event);
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String orderID = txtOrderID.getText();
        String itemCode = txtItemCode.getText();
        int orderQTY = Integer.parseInt(txtOrderQTY.getText());
        int discount = Integer.parseInt(txtDiscount.getText());

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE orderDetail SET orderQTY=?, discount=? WHERE orderID=? AND itemCode=?");


            ps.setInt(1, orderQTY);
            ps.setInt(2, discount);
            ps.setString(3, orderID);
            ps.setString(4, itemCode);

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Updated: " + result);

            if(result > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "OrderDetail Updated Successfully!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No matching OrderDetail found!");
                alert.show();
            }


            btnViewOnAction(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnViewOnAction(ActionEvent event) {
        ObservableList<OrderDetail> orderDetail = FXCollections.observableArrayList();
        orderDetail.clear();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orderDetail");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail od = new OrderDetail(
                        rs.getString("orderID"),
                        rs.getString("itemCode"),
                        rs.getInt("orderQTY"),
                        rs.getInt("discount")
                );
                orderDetail.add(od);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
