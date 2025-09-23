package controller.customerController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerManagementController implements CustomerManagementService{


    @Override
    public void AddCustomer(String custID, String custTitle, String custName, LocalDate DOB, double salary, String custAddress, String city, String province, String postalCode){
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade", "root", "1234")) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, custID);
            preparedStatement.setString(2, custTitle);
            preparedStatement.setString(3, custName);

            if (DOB != null) {
                preparedStatement.setDate(4, java.sql.Date.valueOf(DOB));
            } else {
                preparedStatement.setNull(4, java.sql.Types.DATE);
            }

            preparedStatement.setDouble(5, salary);
            preparedStatement.setString(6, custAddress);
            preparedStatement.setString(7, city);
            preparedStatement.setString(8, province);
            preparedStatement.setString(9, postalCode);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("Customer Added Successfully!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
