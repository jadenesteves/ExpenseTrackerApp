/**
 * Name: ExpenseTrackerHelper.java
 * Last Updated: 5/3/2024
 * Description: DBhelper class for expense tracker database
 */
package com.example.expensetrackerapp.ExpenseTracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetrackerapp.ExpenseTracker.ExpenseTrackerContract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerHelper extends SQLiteOpenHelper {

    // Variables to hold database version and database name
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ExpenseTracker.db";

    // Holds sql entry for creating category table
    private static final String SQL_CREATE_TYPE = "CREATE TABLE IF NOT EXISTS " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME + " (" +
            ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_CID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + " TEXT UNIQUE)";

    // Holds sql entry for creating expense table
    private static final String SQL_CREATE_EXPENSES = "CREATE TABLE IF NOT EXISTS " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " (" +
            ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
            ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE + " TEXT NOT NULL DEFAULT CURRENT_DATE, " +
            ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT + " TEXT NOT NULL, " +
            ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DETAILS + " TEXT NOT NULL, " +
            ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + " INTEGER NOT NULL DEFAULT 1, " +
            "FOREIGN KEY (" + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + ") REFERENCES " +
            ExpenseTrackerContract.CategoryEntry.TABLE_NAME + "(" + ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_CID + "))";


    public ExpenseTrackerHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create table for types and expenses
        db.execSQL(SQL_CREATE_TYPE);
        db.execSQL(SQL_CREATE_EXPENSES);

        // Inserted a couple categories already for the user to ues
        db.execSQL("INSERT OR REPLACE INTO " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME + "(" +
                ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_CID + ", " +
                ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + ")" +
                "VALUES" + "(1, 'General'), (2, 'Misc'), (3, 'Utilities')");


        // Set starting id for future transactions
        db.execSQL("INSERT OR REPLACE INTO " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + "(" +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_TITLE + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DETAILS + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + ")" +
                "VALUES(10000, 'temp', '2024-24-24', 00.00, '', 1)");

        db.execSQL("DELETE FROM " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " WHERE "
                + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + " = " + 10000);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Method for getting all categories in a string arraylist
    public List<String> getCategories(){

        List<String> categories = new ArrayList<String>();
        String query = "SELECT * FROM " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                categories.add(cursor.getString(1));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    // Method for return a cursor of all categories
    public Cursor getCursorCategories(){
        String query = "SELECT * FROM " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    // Method that reads for all expenses and returns a cursor of it
    public Cursor readAllExpenses(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry="SELECT " + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_TITLE + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DETAILS + ", " +
                ExpenseTrackerContract.CategoryEntry.TABLE_NAME + "." +
                ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_CID + ", " +
                ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + " FROM " +
                ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " INNER JOIN " +
                ExpenseTrackerContract.CategoryEntry.TABLE_NAME + " USING (" +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + ") ORDER BY " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.rawQuery(qry, null);
        return cursor;
    }

    // Method that returns the five most recently added expenses as a cursor
    public Cursor readFiveExpenses(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry="SELECT " + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_TITLE + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT + ", " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DETAILS + ", " +
                ExpenseTrackerContract.CategoryEntry.TABLE_NAME + "." +
                ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_CID + ", " +
                ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + " FROM " +
                ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " INNER JOIN " +
                ExpenseTrackerContract.CategoryEntry.TABLE_NAME + " USING (" +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + ") ORDER BY " +
                ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + " DESC LIMIT 5";

        Cursor cursor = db.rawQuery(qry, null);
        return cursor;
    }

    // Calculate total expenses utilizing the big decimal object for precision
    public BigDecimal totalExpenses(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT " + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT + " FROM " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(qry, null);
        BigDecimal sum = new BigDecimal("0.00");
        if(cursor.moveToFirst()){
            sum = sum.add(new BigDecimal(cursor.getString(0)));
            while(cursor.moveToNext()){
               sum = sum.add(new BigDecimal(cursor.getString(0)));
            }
        }
        return sum;
    }

    // Calculate total expenses from the last 30 days using big decimal for precision
    public BigDecimal totalExpenses30(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry =   "SELECT " + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT + " FROM " +  ExpenseTrackerContract.ExpenseEntry.TABLE_NAME +
                " WHERE " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + "." + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE + " > date('now', '-30 days')";
        Cursor cursor = db.rawQuery(qry, null);
        BigDecimal sum = new BigDecimal("0.00");
        if(cursor.moveToFirst()){
            sum = sum.add(new BigDecimal(cursor.getString(0)));
            while(cursor.moveToNext()){
                sum = sum.add(new BigDecimal(cursor.getString(0)));
            }
        }
        return sum;
    }

    // Method for updating expenses
    public String updateExpense(String id, String name, String date, String amount, String detail, String cidName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        String[] idArg = {id};
        Cursor cursor = db.rawQuery("SELECT * FROM " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " WHERE " + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + "=?", idArg);
        boolean doesExist = true;

        // Check if id exist in expense table, if not, return message
        if(!cursor.moveToFirst()){;
            return "Expense Id does not exist";
        }

        // If all columns are empty, return error messsage
        if(name.matches("") && date.matches("") && amount.matches("") && detail.matches("") && cidName.matches("")){
            return "No new values inserted";
        }

        // Error checking for title
        if (!(name.matches(""))){

            // if greater than 20 characters, return error message
            if(name.length() > 20){
                return "Name must be less than 20 characters";
            }

            row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_TITLE, name);
        }

        // Error checking for date
        if (!(date.matches(""))){

            // Check if the date doesnt match correct formatting
            if (!date.matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$")){
                return "Incorrect date format (YYYY-MM-DD)";
            }
            row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE, date);
        }

        // Error checking for details
        if (!(detail.matches(""))){
            row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DETAILS, detail);
        }

        // Error checking for cid
        if (!(cidName.matches(""))) {

            // Search for cid number from cid name and store it
            cursor = db.rawQuery("SELECT * FROM " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME +
                    " WHERE " + ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + "=?", new String[]{cidName});
            if(cursor.moveToFirst()){
                row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID, cursor.getInt(0));
            }
        }

        // Error checking for amount
        if (!(amount.matches(""))) {

            // Check if amount matches currency regex
            if (!amount.matches("^\\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(\\.[0-9][0-9])?$")){
                return "Incorrect amount format (_.00)";
            }
            row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT, amount);
        }

        // Update expense inside table and return successful message
        db.update(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, row, ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + "=?", idArg);
        db.close();
        return "Transaction has been successfully updated";
    }
}
