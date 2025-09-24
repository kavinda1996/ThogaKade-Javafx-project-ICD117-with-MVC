package controller.customerController;

import javafx.collections.ObservableList;
import model.Customer;

import java.time.LocalDate;

public interface CustomerManagementService {


    void AddCustomer(Customer customer1);

    void DeleteCustomer(String custID);

    void UpdateCustomer(Customer customer);

    ObservableList<Customer> getAllCustomer();

    ObservableList<Customer> getAllCustomerById(String custID);
}
