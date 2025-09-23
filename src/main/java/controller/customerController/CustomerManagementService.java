package controller.customerController;

import javafx.collections.ObservableList;
import model.Customer;

import java.time.LocalDate;

public interface CustomerManagementService {


    void AddCustomer(String custID, String custTitle, String custName, LocalDate DOB, double salary, String custAddress, String city, String province, String postalCode);

    void DeleteCustomer(String custID);

    void UpdateCustomer(Customer customer);

    ObservableList<Customer> getAllCustomer();

    ObservableList<Customer> getAllCustomerById(String custID);
}
