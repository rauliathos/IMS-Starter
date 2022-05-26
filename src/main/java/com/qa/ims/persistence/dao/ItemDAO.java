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

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAO implements Dao<Item> {

    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Item modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Double value = resultSet.getDouble("value");
        return new Item(id, name, value);
    }

    // read all items from db
    @Override
    public List<Item> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM item_table");) {
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(modelFromResultSet(resultSet));
            }
            return items;
        } catch (SQLException e) { 
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    // read latest item added to the db
    public Item readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement
                        .executeQuery("SELECT * FROM item_table ORDER BY id DESC LIMIT 1");) {

            resultSet.next();
            return modelFromResultSet(resultSet);

        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }
    
    
//    CREATEs a item in the db
    @Override
    public Item create(Item item) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO item_table(name , value) VALUES (?, ?)");) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getValue());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }
    
    //read an Item by id
    @Override
    public Item read(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM item_table WHERE id = ?");) {
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
    
    //UPDATEs an Item in the DB
    @Override
    public Item update(Item item) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement
                        ("UPDATE item_table SET name = ?, value = ? WHERE id = ?");) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getValue());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
            return read(item.getId());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }
    
    //DELETEs an Item from the db
    @Override
    public int delete(long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("DELETE FROM item_table WHERE id = ?");) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

}
