package com.example.expensetrackerapp;

import android.provider.BaseColumns;

public final class ExpenseTrackerContract {
    private ExpenseTrackerContract(){}

    // Holds all the columns for the expense table
    public static class ExpenseEntry implements BaseColumns{
        public static final String TABLE_NAME = "expenses";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DETAILS = "details";
        public static final String COLUMN_NAME_CID = "cid";
    }

    // Holds all the columns for the category table
    public static class CategoryEntry implements BaseColumns{
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_CID = "cid";
        public static final String COLUMN_NAME_NAME = "name";
    }
}
