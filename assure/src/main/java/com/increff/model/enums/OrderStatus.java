package com.increff.model.enums;

public enum OrderStatus {
    CREATED("CREATED"),ALLOCATED("ALLOCATED"),FULFILLED("FULFILLED");
    private String status;
    OrderStatus(String status){ this.status=status;}
    public void setStatus(String status){this.status=status;}
    public String getStatus() {return this.status;}
}
