package com.sarmadali.ecommerceappproject.Models;

public class ProductModel {

    int prodImg;
    String prodName, prodPrice;

    public ProductModel(int prodImg, String prodName, String prodPrice) {
        this.prodImg = prodImg;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }

    public int getProdImg() {
        return prodImg;
    }

    public void setProdImg(int prodImg) {
        this.prodImg = prodImg;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }
}
