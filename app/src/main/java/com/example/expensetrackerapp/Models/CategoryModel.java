/**
 * Name: CategoryModel.java
 * Last Updated: 5/3/2024
 * Description: Class for entries from category table
 */

package com.example.expensetrackerapp.Models;

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
