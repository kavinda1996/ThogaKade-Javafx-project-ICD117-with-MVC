package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    Stage itemStage = new Stage();

    @FXML
    private JFXButton btnCustomerManagement;

    @FXML
    private JFXButton btnItemManagement;

    @FXML
    private JFXButton btnOrderDetailsManagament;

    @FXML
    private JFXButton btnOrderManagement;

    @FXML
    void btnCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        try {
            itemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/item.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemStage.setResizable(false);
        itemStage.show();
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }

    Stage placeOrderStage = new Stage();
    @FXML
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try {
            placeOrderStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrder.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        placeOrderStage.show();
    }
}
