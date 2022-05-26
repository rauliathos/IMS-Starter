package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;

public class OrderItemDAOTest {
    private final OrderItemDAO DAO = new OrderItemDAO();

    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {
        final OrderItem created = new OrderItem(1L, 2L, 2L);
        assertEquals(created, DAO.create(created));
    }

    @Test
    public void testDeleteItem() {
        assertEquals(1, DAO.deleteItem(1L, 1L));
    }
}