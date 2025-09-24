package controller.customerController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;
import java.time.LocalDate;

public class CustomerManagementController implements CustomerManagementService {


    @Override
    public void AddCustomer(Customer customer1) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, customer1.getCustID());
            preparedStatement.setString(2, customer1.getCustTitle());
            preparedStatement.setString(3, customer1.getCustName());

            if (customer1.getDOB() != null) {
                preparedStatement.setDate(4, java.sql.Date.valueOf(customer1.getDOB()));
            } else {
                preparedStatement.setNull(4, java.sql.Types.DATE);
            }

            preparedStatement.setDouble(5, customer1.getSalary());
            preparedStatement.setString(6, customer1.getCustAddress());
            preparedStatement.setString(7, customer1.getCity());
            preparedStatement.setString(8, customer1.getProvince());
            preparedStatement.setString(9, customer1.getPostalCode());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("Customer Added Successfully!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void DeleteCustomer(String custID) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {
            String sql = "DELETE FROM customer WHERE custID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, custID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("No customer found with ID: " + custID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateCustomer(Customer customer) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {
            String sql = "UPDATE customer SET custTitle=?, custName=?, dob=?, salary=?, custAddress=?, city=?, province=?, postalCode=? WHERE custID=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, customer.getCustTitle());
            ps.setString(2, customer.getCustName());
            ps.setTimestamp(3, Timestamp.valueOf(customer.getDOB().atStartOfDay()));
            ps.setDouble(4, customer.getSalary());
            ps.setString(5, customer.getCustAddress());
            ps.setString(6, customer.getCity());
            ps.setString(7, customer.getProvince());
            ps.setString(8, customer.getPostalCode());
            ps.setString(9, customer.getCustID());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("No customer found with ID: " + customer.getCustID());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {

        ObservableList<Customer> customer = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {
            String SQL = "SELECT * FROM customer";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("dob");
                LocalDate dob = (ts != null) ? LocalDate.from(ts.toLocalDateTime()) : null;

                Customer c = new Customer(
                        resultSet.getString("custID"),
                        resultSet.getString("custTitle"),
                        resultSet.getString("custName"),
                        dob,
                        resultSet.getDouble("salary"),
                        resultSet.getString("custAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode")
                );

                customer.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    @Override
    public ObservableList<Customer> getAllCustomerById(String custID) {
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            String SQL = "SELECT * FROM customer WHERE custID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, custID);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("dob");
                LocalDate dob = (ts != null) ? LocalDate.from(ts.toLocalDateTime()) : null;
                Customer c = new Customer(
                        resultSet.getString("custID"),
                        resultSet.getString("custTitle"),
                        resultSet.getString("custName"),
                        dob,
                        resultSet.getDouble("salary"),
                        resultSet.getString("custAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode")
                );
                customer.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }


}
