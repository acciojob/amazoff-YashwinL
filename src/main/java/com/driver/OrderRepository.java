package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
   private HashMap<String,Order> OrderDB;
   private HashMap<String,DeliveryPartner> PartnerDB;
   private HashMap<DeliveryPartner, List<Order>> PartnerAndOrdersDB;
   private HashMap<String,Order> OrdersAssignedDB;

   public OrderRepository(){
       this.OrderDB = new HashMap<>();
       this.PartnerDB = new HashMap<>();
       this.PartnerAndOrdersDB = new HashMap<>();
       this.OrdersAssignedDB = new HashMap<>();
   }



    public void addOrder(Order order){
        OrderDB.put(order.getId(),order);
    }

    public void addPartner(String partnerID){
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerID,0);
        PartnerDB.put(partnerID,deliveryPartner);
    }
    public void addOrderPartnerPair(String orderid,String partnerid){
       if(OrderDB.containsKey(orderid) && PartnerDB.containsKey(partnerid)){
           if(PartnerAndOrdersDB.containsKey(PartnerDB.get(partnerid))){
               List<Order> temp = PartnerAndOrdersDB.get(PartnerDB.get(partnerid));
               temp.add(OrderDB.get(orderid));
               PartnerAndOrdersDB.put(PartnerDB.get(partnerid),temp);
               OrdersAssignedDB.put(orderid,OrderDB.get(orderid));
           }else{
               List<Order> orderList = new ArrayList<>();
               orderList.add(OrderDB.get(orderid));
               PartnerAndOrdersDB.put(PartnerDB.get(partnerid),orderList);
               OrdersAssignedDB.put(orderid,OrderDB.get(orderid));
           }
       }


    }

    public Order getOrderById(String orderid){
        return OrderDB.get(orderid);
    }

    public DeliveryPartner getPartnerById(String partnerid){
       Integer value = PartnerAndOrdersDB.get(PartnerDB.get(partnerid)).size();
       PartnerDB.get(partnerid).setNumberOfOrders(value);
        return PartnerDB.get(partnerid);
    }

    public Integer getOrderCountByPartnerId(String partnerid){
       Integer count =0;
       if(PartnerAndOrdersDB.containsKey(PartnerDB.get(partnerid))){
           count = PartnerAndOrdersDB.get(PartnerDB.get(partnerid)).size();
       }
       return count;
    }

    public List<String> getOrdersByPartnerId(String partnerid){
        List<String> PartnerOrderList = new ArrayList<>();
        for(Order order : PartnerAndOrdersDB.get(PartnerDB.get(partnerid))){
            PartnerOrderList.add(order.getId());
        }
        return PartnerOrderList;
    }
    public List<String>  getAllOrders(){
        List<String> ans = new ArrayList<>();
        for(String i : OrderDB.keySet()){
            ans.add(i);
        }
        return ans;
    }

    public Integer getCountOfUnassignedOrders(){
        Integer ans=0;
        for(String i : OrderDB.keySet()){
            if(!OrdersAssignedDB.containsKey(i)){
                ans++;
            }
        }
        return ans;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerid){
        Integer count=0;
        String[] t = time.split(":");
        Integer timeInMinutes = (Integer.valueOf(t[0])*60) + Integer.valueOf(t[1]);
        for(Order order : PartnerAndOrdersDB.get(PartnerDB.get(partnerid))){
            if(order.getDeliveryTime()>timeInMinutes){
                count++;
            }
        }
        return count;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerid){
        int lasttime=0;
        String ans ="";
        List<Order> temp = PartnerAndOrdersDB.get(PartnerDB.get(partnerid));
        for(Order order : PartnerAndOrdersDB.get(PartnerDB.get(partnerid))){
            if(order.getDeliveryTime()>lasttime){
                lasttime = order.getDeliveryTime();
                ans = order.getRealtime();
            }
        }
        return ans;

    }

    public void deletePartnerById(String partnerid){
            for(Order order : PartnerAndOrdersDB.get(PartnerDB.get(partnerid))){
                OrdersAssignedDB.remove(order.getId());
            }
            PartnerAndOrdersDB.get(PartnerDB.get(partnerid)).clear();
            PartnerDB.remove(partnerid);
    }


    public void deleteOrderById(String orderid){
        for(DeliveryPartner p : PartnerAndOrdersDB.keySet()){
            for(Order order : PartnerAndOrdersDB.get(p)){
                if(Objects.equals(order.getId(), orderid)){
                     List<Order> temp = PartnerAndOrdersDB.get(p);
                     temp.remove(OrderDB.get(orderid));
                     PartnerAndOrdersDB.put(p,temp);
                     break;
                }
            }
        }
        OrderDB.remove(orderid);
    }


}
