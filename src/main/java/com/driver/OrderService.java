package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order){
       orderRepository.addOrder(order);
    }

    public void addPartner(String partnerID){
       orderRepository.addPartner(partnerID);
    }
    public void addOrderPartnerPair(String orderid,String partnerid){
        orderRepository.addOrderPartnerPair(orderid,partnerid);
    }

    public Order getOrderById(String orderid){
        return orderRepository.getOrderById(orderid);
    }

    public DeliveryPartner getPartnerById(String partnerid){
        return orderRepository.getPartnerById(partnerid);
    }
    public Integer getOrderCountByPartnerId(String partnerid){
        return orderRepository.getOrderCountByPartnerId(partnerid);
    }

    public List<String> getOrdersByPartnerId(String partnerid){
     return orderRepository.getOrdersByPartnerId(partnerid);
    }

    public List<String>  getAllOrders(){
       return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders(){
       return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerid){
       return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerid);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerid){
       return orderRepository.getLastDeliveryTimeByPartnerId(partnerid);

    }

    public void deletePartnerById(String partnerid){
      orderRepository.deletePartnerById(partnerid);
    }
    public void deleteOrderById(String orderid){
       orderRepository.deleteOrderById(orderid);
    }
}
