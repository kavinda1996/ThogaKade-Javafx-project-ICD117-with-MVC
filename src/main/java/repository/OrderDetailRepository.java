package repository;

import model.dto.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailRepository {

    boolean addOrderdetails(OrderDetail orderDetail) throws SQLException;
}
