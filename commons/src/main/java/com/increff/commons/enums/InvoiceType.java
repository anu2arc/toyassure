package com.increff.commons.enums;
public enum InvoiceType {
    SELF("SELF"),CHANNEL("CHANNEL");
    private String type;
    InvoiceType(String value){this.type=value;}
    public String getInvoiceType() {return this.type;}
    public void setInvoiceType(String type) { this.type=type;}
}
