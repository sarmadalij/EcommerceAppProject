package com.sarmadali.ecommerceappproject.Models;

public class CategoryModel {
    int catgimage;
    String catgText;

    public CategoryModel(int catgimage, String catgText) {
        this.catgimage = catgimage;
        this.catgText = catgText;
    }

    public int getCatgimage() {
        return catgimage;
    }

    public void setCatgimage(int catgimage) {
        this.catgimage = catgimage;
    }

    public String getCatgText() {
        return catgText;
    }

    public void setCatgText(String catgText) {
        this.catgText = catgText;
    }
}
