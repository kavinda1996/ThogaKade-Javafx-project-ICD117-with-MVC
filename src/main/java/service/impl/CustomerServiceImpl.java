package service.impl;

import model.dto.Customer;
import repository.CustomerRepository;
import repository.impl.CustomerRepositoryImpl;
import service.CustomerService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository = new CustomerRepositoryImpl();

    public Customer getCustomer(String customerId){
        try {
            ResultSet resultSet = customerRepository.searchCustomer(customerId);
            resultSet.next();
            return new Customer(
                    resultSet.getString("CustID"),
                    resultSet.getString("CustTitle"),
                    resultSet.getString("CustName"),
                    resultSet.getDate("DOB").toLocalDate(),
                    resultSet.getDouble("salary"),
                    resultSet.getString("CustAddress"),
                    resultSet.getString("City"),
                    resultSet.getString("Province"),
                    resultSet.getString("PostalCode")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
