package com.sarmadali.ecommerceappproject.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductDetails implements Parcelable {
    String productImageName, productImageUri;
    String productName, productCategory, productDescription, productPrice;
    String productQuantity;

    int totalProductPrice;
    //empty constructor
    public ProductDetails() {
    }

    //constructor for all
    public ProductDetails(String productImageName, String productImageUri, String productName,
                          String productCategory, String productDescription, String productPrice) {
        this.productImageName = productImageName;
        this.productImageUri = productImageUri;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    //constructor for add to cart

    public ProductDetails(String productImageUri, String productName, String productCategory,
                         String productPrice, String productQuantity,int totalProductPrice) {
        this.productImageUri = productImageUri;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.totalProductPrice = totalProductPrice;
    }


    //getter and setter


    public int getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(int totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    protected ProductDetails(Parcel in) {
        productImageName = in.readString();
        productImageUri = in.readString();
        productName = in.readString();
        productCategory = in.readString();
        productDescription = in.readString();
        productPrice = in.readString();
    }

    public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel in) {
            return new ProductDetails(in);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };

    public String getProductImageName() {
        return productImageName;
    }

    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }


    public String getProductImageUri() {
        return productImageUri;
    }

    public void setProductImageUri(String productImageUri) {
        this.productImageUri = productImageUri;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(productImageName);
        dest.writeString(productImageUri);
        dest.writeString(productName);
        dest.writeString(productCategory);
        dest.writeString(productDescription);
        dest.writeString(productPrice);
    }
}
