package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

//takes in Order details for CRUD functionality
public class OrderController implements CrudController<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderDAO orderDAO;
    private  OrderItemDAO orderItemsDAO;
    private Utils utils;
    public OrderController(OrderDAO orderDAO, Utils utils) {
        super();
        orderItemsDAO = new OrderItemDAO();
        this.orderDAO = orderDAO;
        this.utils = utils;
    }
    
    //reads all orders to the logger
    @Override
    public List<Order> readAll() {
        List<Order> orders = orderDAO.readAll();
        for (Order order : orders) {
            LOGGER.info(order);
        }
        return orders;
    }
    
//  creates an Order by taking in user input
    
  @Override
  public Order create() {
      LOGGER.info("Please enter a  customer_id");
      Long customer_id = utils.getLong();
      Order order = orderDAO.create(new Order(customer_id));
      LOGGER.info("Order created");
      return order;
  }


  
  @Override
  public Order update() {
      
      LOGGER.info("Choose what do you want to do:");
      LOGGER.info("ORDER:  TO add item to order");
      LOGGER.info("DELETE: Enter order_id TO delete item from order ");
      LOGGER.info("TOTAL: Enter order_id  TO calculate total of that order");
      String choice = utils.getString();//.toLowerCase();
//      System.out.println("this is the choice value: "+choice);
       
      
      
      if(choice.equals("order")) {
          LOGGER.info(" Enter order_id TO add  to order");
          Long order_id = utils.getLong();
          LOGGER.info(" Enter item_id TO add  to order");
          Long item_id = utils.getLong();
          LOGGER.info(" Enter quantity ->how many items to order");
          Long quantity = utils.getLong();
          orderItemsDAO.create(new OrderItem(order_id, item_id, quantity));
          LOGGER.info("Order Updated!!!");



      }else if (choice.equals("delete") ) {
          LOGGER.info(" Enter order_id from you want to delete an item");
          Long order_id = utils.getLong();
          LOGGER.info(" Enter item_id you want to remove from order");
          Long item_id_to_be_deleted = utils.getLong();
          orderItemsDAO.deleteItem(order_id, item_id_to_be_deleted);
          LOGGER.info("Order Updated!!! the Item was deleted from order!!");
          
          
      }else if (choice.equals("total")) {
          LOGGER.info(" Enter order_id to calculate total");
          Long order_id_total = utils.getLong();
          System.out.println(orderItemsDAO.calcTotal(order_id_total));
         orderItemsDAO.calcTotal(order_id_total);

          ;
          
      } else {
          LOGGER.info("IF FAILED");
      }
      
     return null;
  }
  
  //DELETEs an existing order by order_id;
  
  @Override
  public int delete() {
      LOGGER.info("Please enter the id of the order you would like to delete");
      Long id = utils.getLong();
      orderItemsDAO.delete(id);
      return orderDAO.delete(id);
  }
    
    
}
