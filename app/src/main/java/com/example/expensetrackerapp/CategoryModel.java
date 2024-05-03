package com.example.expensetrackerapp;

public class CategoryModel {
    int cid;
    String category;

    public CategoryModel(int cid, String category) {
        this.cid = cid;
        this.category = category;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
