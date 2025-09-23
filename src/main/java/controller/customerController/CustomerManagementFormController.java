package controller.customerController;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.sql.Timestamp;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import static java.time.LocalDate.parse;
import static javafx.fxml.FXMLLoader.load;


public class CustomerManagementFormController implements Initializable {

    public Button btnItemManagement;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    public Button btnSearch;

    @FXML
    private TableColumn<Customer, String> colCity;

    @FXML
    private TableColumn<Customer, String> colCustAddress;

    @FXML
    private TableColumn<Customer, String> colCustID;

    @FXML
    private TableColumn<Customer, String> colCustName;

    @FXML
    private TableColumn<Customer, String> colCustTitle;

    @FXML
    private TableColumn<Customer, LocalDateTime> colDOB;

    @FXML
    private TableColumn<Customer, String> colPostalCode;

    @FXML
    private TableColumn<Customer, String> colProvince;

    @FXML
    private TableColumn<Customer, Double> colSalary;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtCustAddress;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private JFXTextField txtCustTitle;

    @FXML
    private JFXTextField txtDOB;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtcustID;

    ObservableList<Customer> customer = FXCollections.observableArrayList();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String custID = txtcustID.getText();
        String custTitle = txtCustTitle.getText();
        String custName = txtCustName.getText();

        LocalDate DOB = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DOB = parse(txtDOB.getText(), formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please use yyyy-MM-dd");
            new Alert(Alert.AlertType.INFORMATION, "Invalid date format! Please use yyyy-MM-dd").show();
        }

        double salary = Double.parseDouble(txtSalary.getText());
        String custAddress = txtCustAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

    CustomerManagementService customerManagementService;
        customerManagementService = new CustomerManagementController();
        customerManagementService.AddCustomer(custID,custTitle,custName,DOB,salary,custAddress,city,province,postalCode);

        viewCustomer();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String custID = txtcustID.getText();
        if (!custID.isEmpty()) {
            deleteCustomer(custID);
            viewCustomer();

        }
    }

    private void deleteCustomer(String custID) {
        CustomerManagementService customerManagementService = new CustomerManagementController();
        customerManagementService.DeleteCustomer(custID);
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            Customer selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {

                selectedCustomer.setCustTitle(txtCustTitle.getText());
                selectedCustomer.setCustName(txtCustName.getText());

                LocalDate dob = null;
                if (!txtDOB.getText().isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dob = LocalDate.from(parse(txtDOB.getText(), formatter).atStartOfDay());
                }
                selectedCustomer.setDOB(LocalDate.from(dob));

                selectedCustomer.setSalary(Double.parseDouble(txtSalary.getText()));
                selectedCustomer.setCustAddress(txtCustAddress.getText());
                selectedCustomer.setCity(txtCity.getText());
                selectedCustomer.setProvince(txtProvince.getText());
                selectedCustomer.setPostalCode(txtPostalCode.getText());

                updateCustomer(selectedCustomer);
                viewCustomer();

            } else {
                new Alert(Alert.AlertType.ERROR, "No customer selected!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Update failed: " + e.getMessage()).show();
        }
    }


    private void updateCustomer(Customer customer) {
        CustomerManagementService customerManagementService= new CustomerManagementController();
        customerManagementService.UpdateCustomer(customer);
    }


    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) {
        String custID = txtcustID.getText();
        viewCustomerByID(custID);
    }

    private void viewCustomer() {
        customer.clear();

        CustomerManagementService customerManagementService = new CustomerManagementController();
        customer=customerManagementService.getAllCustomer();

    }

    private void viewCustomerByID(String custID) {

        customer.clear();
        CustomerManagementService customerManagementService = new CustomerManagementController();
        customer= customerManagementService.getAllCustomerById(custID);
        ObservableList<Customer> result = customerManagementService.getAllCustomerById(custID);
        tblCustomer.setItems(result);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colCustTitle.setCellValueFactory(new PropertyValueFactory<>("custTitle"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        viewCustomer();

        tblCustomer.setItems(customer);

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtcustID.setText(String.valueOf(newSelection.getCustID()));
                        txtCustTitle.setText(newSelection.getCustTitle());
                        txtCustName.setText(newSelection.getCustName());
                        txtDOB.setText(String.valueOf(newSelection.getDOB()));
                        txtSalary.setText(String.valueOf(newSelection.getSalary()));
                        txtCustAddress.setText((newSelection.getCustAddress()));
                        txtCity.setText(newSelection.getCity());
                        txtProvince.setText(newSelection.getProvince());
                        txtPostalCode.setText(newSelection.getPostalCode());
                    }
                }
        );
    }

    public void btnItemManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(load(getClass().getResource("/view/Item_management_form.fxml"))));
        stage.show();
    }

}
