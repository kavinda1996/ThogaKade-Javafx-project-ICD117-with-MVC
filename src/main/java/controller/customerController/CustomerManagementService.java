package controller.customerController;

import java.time.LocalDate;

public interface CustomerManagementService {


    void AddCustomer(String custID, String custTitle, String custName, LocalDate DOB, double salary, String custAddress, String city, String province, String postalCode);
}
