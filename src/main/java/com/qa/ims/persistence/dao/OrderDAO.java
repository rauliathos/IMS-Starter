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

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order>{

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long customer_id = resultSet.getLong("customer_id");
         
        return new Order(id,customer_id);
    }
    
    // read all orders from db and return a list of orders
    @Override
    public List<Order> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM order_table");) {
            List<Order> orders = new ArrayList<>();
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
    
    // read latest order added to the db
    public Order readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement
                        .executeQuery("SELECT * FROM order_table ORDER BY id DESC LIMIT 1");) {

            resultSet.next();
            return modelFromResultSet(resultSet);

        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }
    
//  CREATEs an Order in the db
  @Override
  public Order create(Order order) {
      try (Connection connection = DBUtils.getInstance().getConnection();
              PreparedStatement statement = connection
                      .prepareStatement("INSERT INTO order_table(customer_id) VALUES (?)")) {
          statement.setLong(1, order.getCustomer_id());
          statement.executeUpdate();
          return readLatest();
      } catch (Exception e) {
          LOGGER.debug(e);
          LOGGER.error(e.getMessage());
      }
      return null;
  }
  
//read an ORDER by id
  @Override
  public Order read(Long id) {
      
      try (Connection connection = DBUtils.getInstance().getConnection();
              PreparedStatement statement = connection
                      .prepareStatement("SELECT * FROM order_table WHERE id = ?");) {
          statement.setLong(1, id);
          try (ResultSet resultSet = statement.executeQuery();) {
              resultSet.next();
              return modelFromResultSet(resultSet);
          }
      } catch (Exception e) {
          LOGGER.debug(e);
          LOGGER.error(e.getMessage());
      } 
      return null;
  }
  
//UPDATEs an Order in the DB
  @Override
  public Order update(Order order) {
      
      
  //THE 3 OPTIONS LOGIC ??????
      //create an order update (that return null) just to be used in order controller!!
      
//      try (Connection connection = DBUtils.getInstance().getConnection();
//              PreparedStatement statement = connection
//                      .prepareStatement
//                      ("UPDATE order_table SET customer_id = ? WHERE id = ?");) {
//          
//          statement.setLong(1, order.getCustomer_id());
//          statement.setLong(2, order.getId());
//          statement.executeUpdate();
//          return read(order.getId());
//      } catch (Exception e) {
//          LOGGER.debug(e);
//          LOGGER.error(e.getMessage());
//      }
      return null;
  }
    
  //DELETEs an Order from the db
  @Override
  public int delete(long id) {
      try (Connection connection = DBUtils.getInstance().getConnection();
              PreparedStatement statement =
                      connection.prepareStatement("DELETE FROM order_table WHERE id = ?");) {
          statement.setLong(1, id);
          return statement.executeUpdate();
      } catch (Exception e) {
          LOGGER.debug(e);
          LOGGER.error(e.getMessage());
      }
      return 0;
  }

    
    
    
    
    
}
