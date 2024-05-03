/**
 * Name: ExpenseModel.java
 * Last Updated: 5/3/2024
 * Description: Model class for holding information about expense entries
 */
package com.example.expensetrackerapp.Models;

public class ExpenseModel {
    int id;
    String title;
    String date;
    String amount;
    String details;
    int cid;
    String category;

    public ExpenseModel(int id, String title, String date, String amount, String details, int cid, String category) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.details = details;
        this.cid = cid;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
