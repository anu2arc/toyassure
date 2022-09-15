package com.increff.model.enums;

public enum ClientType {
    CLIENT("CLIENT"), CUSTOMER("CUSTOMER");
    private String type;
    ClientType(String value) {
        this.type = value;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type=type;
    }
}
