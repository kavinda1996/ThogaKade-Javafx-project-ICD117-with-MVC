package repository.impl;

import db.DBConnection;
import model.dto.Item;
import model.dto.OrderDetail;
import repository.OrderDetailRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderdetailsRepositoryImpl implements OrderDetailRepository {

    @Override
    public boolean addOrderdetails(OrderDetail orderDetail) throws SQLException {
        String SQL = "INSERT INTO orderdetail Values(? ,? ,? ,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,orderDetail.getOrderID());
        psTm.setObject(2,orderDetail.getItemCode());
        psTm.setObject(3,orderDetail.getOrderQty());
        psTm.setObject(4,orderDetail.getDiscount());

        return psTm.executeUpdate() > 0;
    }
}
