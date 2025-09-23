package controller.itemController;

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

import java.io.IOException;
import java.sql.*;

import static java.time.LocalDate.parse;

public class ItemManagementFormController {

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

        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        Double unitPrice = Double.valueOf(txtUnitPrice.getText());
        Integer qtyOnHand = Integer.valueOf(txtQtyOnHand.getText());

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1234");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ITEM VALUES(?,?,?,?,?)")) {

           
            preparedStatement.setObject(1, itemCode);
            preparedStatement.setObject(2, description);
            preparedStatement.setObject(3, packSize);
            preparedStatement.setObject(4, unitPrice);
            preparedStatement.setObject(5, qtyOnHand);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        btnViewOnAction(event);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String itemCode = txtItemCode.getText();
        if (!itemCode.isEmpty()) {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {
                String sql = "DELETE FROM customer WHERE itemCode=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, itemCode);

                int rowsAffected;
                rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Item deleted successfully.");
                } else {
                    System.out.println("No item found with ID: " + itemCode);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            btnViewOnAction(event);
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

                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {
                    String sql = "UPDATE item SET itemCode=?, description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?";
                    PreparedStatement ps = connection.prepareStatement(sql);

                    ps.setString(1, selectedItem.getItemCode());
                    ps.setString(2, selectedItem.getDescription());
                    ps.setString(3, selectedItem.getPackSize());
                    ps.setDouble(4, selectedItem.getUnitPrice());
                    ps.setInt(5, selectedItem.getQtyOnHand());


                    ps.setString(6, selectedItem.getItemCode());

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Item updated successfully.");
                    } else {
                        System.out.println("No item found with ID: " + selectedItem.getItemCode());
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                
                btnViewOnAction(event);

            } else {
                new Alert(Alert.AlertType.ERROR, "No item selected!").show();
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

        tblItem.setItems(item);

        item.clear();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ITEM");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Item i = new Item(
                resultSet.getString("itemCode"),
                resultSet.getString("description"),
                resultSet.getString("packSize"),
                resultSet.getDouble("unitPrice"),
                resultSet.getInt("qtyOnHand")
                );
                 item.add(i);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnOrderManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/order_management_form.fxml"))));
        stage.show();
    }
}
