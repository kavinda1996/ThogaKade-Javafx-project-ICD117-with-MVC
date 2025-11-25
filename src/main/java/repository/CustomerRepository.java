package repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerRepository {
    ResultSet searchCustomer(String customerId) throws SQLException;
}
