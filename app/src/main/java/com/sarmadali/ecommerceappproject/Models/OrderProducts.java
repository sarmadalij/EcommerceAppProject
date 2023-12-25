package com.sarmadali.ecommerceappproject.Models;

public class OrderProducts {

    String orderUName, orderUEmail, orderUPhone, orderUAddress, orderTotalPrice;

    String productOrderImage;
    String productOrderName;
    String productOrderQuantity;

    public OrderProducts() {
    }

    //constructor for order user info and price
    public OrderProducts(String orderUName, String orderUEmail, String orderUPhone,
                         String orderUAddress, String orderTotalPrice) {
        this.orderUName = orderUName;
        this.orderUEmail = orderUEmail;
        this.orderUPhone = orderUPhone;
        this.orderUAddress = orderUAddress;
        this.orderTotalPrice = orderTotalPrice;
    }

    //constructor for order products details.

    public OrderProducts(String productOrderImage, String productOrderName, String productOrderQuantity) {
        this.productOrderImage = productOrderImage;
        this.productOrderName = productOrderName;
        this.productOrderQuantity = productOrderQuantity;
    }

    //getter and setter


    public String getProductOrderImage() {
        return productOrderImage;
    }

    public void setProductOrderImage(String productOrderImage) {
        this.productOrderImage = productOrderImage;
    }

    public String getProductOrderName() {
        return productOrderName;
    }

    public void setProductOrderName(String productOrderName) {
        this.productOrderName = productOrderName;
    }

    public String getProductOrderQuantity() {
        return productOrderQuantity;
    }

    public void setProductOrderQuantity(String productOrderQuantity) {
        this.productOrderQuantity = productOrderQuantity;
    }

    public String getOrderUName() {
        return orderUName;
    }

    public void setOrderUName(String orderUName) {
        this.orderUName = orderUName;
    }

    public String getOrderUEmail() {
        return orderUEmail;
    }

    public void setOrderUEmail(String orderUEmail) {
        this.orderUEmail = orderUEmail;
    }

    public String getOrderUPhone() {
        return orderUPhone;
    }

    public void setOrderUPhone(String orderUPhone) {
        this.orderUPhone = orderUPhone;
    }

    public String getOrderUAddress() {
        return orderUAddress;
    }

    public void setOrderUAddress(String orderUAddress) {
        this.orderUAddress = orderUAddress;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }
}
