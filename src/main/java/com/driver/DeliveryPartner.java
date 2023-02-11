package com.driver;

public class DeliveryPartner {

    private String id;
    private int numberOfOrders;

    public DeliveryPartner(String id,int numberOfOrders) {
        this.id = id;
        this.numberOfOrders = 0;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders(){
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}