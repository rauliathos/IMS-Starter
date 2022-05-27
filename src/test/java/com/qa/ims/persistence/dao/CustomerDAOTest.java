package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAOTest  {
 
    private final CustomerDAO DAO = new CustomerDAO();
 
    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {
        final Customer created = new Customer(5L, "chris", "perrins");
        assertEquals(created, DAO.create(created));
    }
 
    @Test
    public void testReadAll() {
        List<Customer> expected = new ArrayList<>();
        expected.add(new Customer( 1L,"jordan", "harrison"));
        expected.add(new Customer( 2L,"raul", "ciobanu"));
        expected.add(new Customer( 3L,"mark", "owen"));
        expected.add(new Customer( 4L,"matei", "tudor"));
        assertEquals(expected, DAO.readAll());
    }

    @Test
    public void testReadLatest() {
        assertEquals(new Customer(4L, "matei", "tudor"), DAO.readLatest());
    }

    @Test
    public void testRead() {
        final long ID = 1L;
        assertEquals(new Customer(ID, "jordan", "harrison"), DAO.read(ID));
    }

    @Test
    public void testUpdate() {
        final Customer updated = new Customer(1L, "chris", "perrins");
        assertEquals(updated, DAO.update(updated));

    }

    @Test
    public void testDelete() {
        assertEquals(1, DAO.delete(1));
    }
}
