package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DashBoardFormController {

    @FXML
    private Button btnLogin;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        if (Objects.equals(txtPassword.getText(), "1234") && Objects.equals(txtUserName.getText(), "kavinda")) {
            Stage stage = new Stage();

            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/customer_management_form.fxml"))));
            stage.show();
        }else new Alert(Alert.AlertType.WARNING, "Invalid Username or Password. Try again!!").show();
    }

}
