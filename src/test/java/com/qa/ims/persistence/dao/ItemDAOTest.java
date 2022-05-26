package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAOTest {
    private final ItemDAO DAO = new ItemDAO();

    
    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }
    
    @Test
    public void testCreate() {
        final Item created = new Item(4L,"glass", 1.5);
        assertEquals(created, DAO.create(created));
    }

    
    @Test
    public void testReadAll() {
        List<Item> expected = new ArrayList<>();
        expected.add(new Item("mouse",3.5 ));
        expected.add(new Item("glass",1.5 ));
        expected.add(new Item("spoon",2.5 ));
        expected.add(new Item("car",10000D ));
    }
    
    @Test
    public void testReadLatest() {
        assertEquals(new Item(3L, "spoon", 2.5), DAO.readLatest() );
    }
    
    @Test
    public void testRead() {
        final long ID = 1L;
        assertEquals(new Item(ID, "mouse", 3.5), DAO.read(ID));
    }
    
    @Test
    public void testUpdate() {
        final Item updated = new Item(1L, "glass", 1.5);
        assertEquals(updated, DAO.update(updated));

    }
    
    @Test
    public void testDelete() {
        assertEquals(1, DAO.delete(1));
    }
     
}
