package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;

public class OrderItemDAO implements Dao<OrderItem> {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public OrderItem modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long order_id = resultSet.getLong("order_id");
        Long item_id = resultSet.getLong("item_id");
        Long quantity = resultSet.getLong("quantity");
        return new OrderItem(order_id, item_id, quantity);
    }

    @Override
    public List<OrderItem> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM order_item");) {
            List<OrderItem> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(modelFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();

    }

    public OrderItem readLatest() {

        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement
                        .executeQuery("SELECT * FROM order_item ORDER BY order_id DESC LIMIT 1");) {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO order_item(order_id, item_id, quantity) VALUES (?, ?, ?)");) {
            statement.setLong(1, orderItem.getOrder_id());
            statement.setLong(2, orderItem.getItem_id());
            statement.setLong(3, orderItem.getQuantity());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public int deleteItem(Long order_id, Long item_id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM order_item WHERE order_id = ? AND item_id = ?");) {
            statement.setLong(1, order_id);
            statement.setLong(2, item_id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public int deleteOrder(Long order_id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM orders_items WHERE order_id = ?");) {
            statement.setLong(1, order_id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public OrderItem read(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrderItem update(OrderItem t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int delete(long id) {
        // TODO Auto-generated method stub
        return 0;
    }

    public double calcTotal(Long order_id) {
        double cost = 0;
        try (Connection connection = DBUtils.getInstance().getConnection();

                PreparedStatement statement = connection
                        .prepareStatement("SELECT a.order_id, SUM(a.quantity * b.value) AS result"
                                + " FROM order_item a JOIN item_table b ON b.id  WHERE a.order_id = ?");) {
            statement.setLong(1, order_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            cost = resultSet.getDouble("result");

            System.out.println("TOTAL of your order is: " + cost);
            return cost;

        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        System.out.println("this is not good");
        return 0;
    }

}
