/**
 * Name: Dashboard.java
 * Last Updated: 5/3/2024
 * Description: Fragment for bottom navigation menu dashboard, allows you to interact with table expenses and category from database
 */
package com.example.expensetrackerapp.Fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.expensetrackerapp.Adapters.CategoryAdapter;
import com.example.expensetrackerapp.Models.CategoryModel;
import com.example.expensetrackerapp.ExpenseTracker.ExpenseTrackerContract;
import com.example.expensetrackerapp.ExpenseTracker.ExpenseTrackerHelper;
import com.example.expensetrackerapp.R;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Fragment {

    // Declare variables
    View view;
    Button Expenses;
    Button Category;
    ExpenseTrackerHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);

        Category = view.findViewById(R.id.Category);
        Expenses = view.findViewById(R.id.Expenses);

        // When expenses are clicked, call a popupmenu to display entries
        Expenses.setOnClickListener(v -> {
            // Declare/Init popup menu and inflate the view
            PopupMenu popup = new PopupMenu(getActivity(), Expenses);
            popup.getMenuInflater().inflate(R.menu.nav_transaction, popup.getMenu());

            // When popup menu item is clicked, show unique dialog box for each option
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    int id = item.getItemId();

                    if (id == R.id.add) {
                        showTDialog(0);
                        return true;
                    } else if (id == R.id.remove) {
                        showTDialog(1);
                        return true;
                    } else if (id == R.id.update){
                        showTDialog(2);
                        return true;
                    }
                    return false;
                }
            });

            // Must call show popup for it to be display
            popup.show();

        });

        // When categories is clicked, call a popmenu to show off options
        Category.setOnClickListener(v -> {

            // Declare/Init popup menu and inflate view
            PopupMenu popup = new PopupMenu(getActivity(), Category);
            popup.getMenuInflater().inflate(R.menu.nav_category, popup.getMenu());

            // When popup menu item is clicked, show unique dialog option for each
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    int id = item.getItemId();

                    if (id == R.id.add) {
                        showCDialog(0);
                        return true;
                    } else if (id == R.id.remove) {
                        showCDialog(1);
                        return true;
                    } else if (id == R.id.view){
                        showCDialog(2);
                        return true;
                    }
                    return false;
                }
            });

            // Must call show for it to be seen
            popup.show();

        });
        return view;
    }

    // Show dialog boxes from transactions menu options
    private void showTDialog(int id) {

        // Declare all necessary variables
        Dialog dialog = new Dialog(getActivity());
        EditText IdInput;
        EditText TitleText;
        EditText DateText;
        EditText AmountText;
        Switch CategorySwitch;
        Spinner CategorySpinner;
        EditText DetailView;
        Button ADD;
        Button UPDATE;

        // Switch statement for checking which menu item had been clicked
        switch (id) {

            // Case 0 represents adding an expense
            case 0:

                // Init all important variables
                dialog.setContentView(R.layout.transactionadd);

                TitleText = dialog.findViewById(R.id.IdInput);
                DateText = dialog.findViewById(R.id.DateText);
                AmountText = dialog.findViewById(R.id.AmountText);
                CategorySpinner = dialog.findViewById(R.id.CategorySpinner);
                DetailView = dialog.findViewById(R.id.DetailView);
                ADD = dialog.findViewById(R.id.UPDATE);

                // Get writable database and call getCategories function to store all categories in array
                helper = new ExpenseTrackerHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                List<String> categoryList = helper.getCategories();


                // Create adapter with categoryList and spinner item
                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Connect adapter on category spinner
                CategorySpinner.setAdapter(adapter);


                // Does nothing
                CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // When add is clicked, begin error checking process
                ADD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Variable for checking if date is valid, start with true
                        boolean validDate = true;

                        // Title and Amount must not be empty to be put in table
                        if (!TitleText.getText().toString().matches("") && !AmountText.getText().toString().matches("")) {

                            // Title must be less than 20 characters
                            if (TitleText.getText().toString().length() < 20) {

                                // Cost must match the regular expression for money
                                if (AmountText.getText().toString().matches("^\\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(\\.[0-9][0-9])?$")) {

                                    // If the date doesn't follow the main format, consider it a non valid number
                                    if (!DateText.getText().toString().matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$") && DateText.getText().toString().length() > 0) {
                                        validDate = false;
                                    }


                                    // If valid date, beginning inserting row into database
                                    if (validDate) {

                                        // Store user inputted values into variables
                                        String title = TitleText.getText().toString().trim();
                                        String date = DateText.getText().toString().trim();
                                        String amount = AmountText.getText().toString();
                                        String categoryName = CategorySpinner.getSelectedItem().toString().trim();
                                        String details = DetailView.getText().toString();
                                        int cid = 0;

                                        helper = new ExpenseTrackerHelper(getActivity());
                                        SQLiteDatabase db = helper.getWritableDatabase();

                                        // Get cid number from user chosen category name
                                        String[] categoryArray = {CategorySpinner.getSelectedItem().toString()};
                                        Cursor cursor = db.rawQuery("select * from " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME +
                                                " where " + ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + " = ?", categoryArray);
                                        if (cursor.moveToFirst()) {
                                            cid = cursor.getInt(0);
                                        }

                                        // Begin putting values into respective columns
                                        ContentValues row = new ContentValues();
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_TITLE, title);

                                        // If there is a date, insert into column
                                        if (date.length() > 0) {
                                            row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE, date);
                                        }
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT, amount);
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DETAILS, details);
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID, cid);

                                        // Insert row into table
                                        db.insert(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, null, row);

                                        // Let user know of transaction success
                                        Toast.makeText(getActivity(), "Transaction successfully added", Toast.LENGTH_SHORT).show();

                                        cursor.close();
                                        db.close();
                                    } else {

                                        // Output error messages to user
                                        Toast.makeText(getActivity(), "Incorrect date format (YYYY-MM-DD)", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    // Output error messages to user
                                    Toast.makeText(getActivity(), "Incorrect amount format (_.00", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                // Output error messages to user
                                Toast.makeText(getActivity(), "Your title must be under 20 characters long", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            // Output error messages to user
                            Toast.makeText(getActivity(), "You must input a title and amount for a transaction", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
                break;


            // Case 1 represents removing expenses
            case 1:
                EditText ExpenseText;


                        // Get simple category add view
                        dialog.setContentView(R.layout.expenseremove);

                        ExpenseText = dialog.findViewById(R.id.ExpenseText);
                        Button Remove = dialog.findViewById(R.id.Remove);

                        Remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // If category name is empty, display error message
                                if (!ExpenseText.getText().toString().matches("")) {



                                        // If category name is greater than 20 characters, display error message
                                        if ((ExpenseText.getText().toString().length() == 5)) {
                                            // Get category name from edittext
                                            int id = Integer.parseInt(ExpenseText.getText().toString());

                                            // Add category to database and display success message
                                            helper = new ExpenseTrackerHelper(getActivity());
                                            SQLiteDatabase db = helper.getWritableDatabase();

                                            // Delete expense from database through the expense id
                                            db.execSQL("DELETE FROM " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " WHERE " + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + "=" + id);
                                            db.close();

                                            // Output success to user
                                            Toast.makeText(getActivity(), "Expense has been successfully removed", Toast.LENGTH_SHORT).show();

                                            // Dismiss dialog box
                                            dialog.dismiss();
                                        } else {

                                            // Output error messages to user
                                            Toast.makeText(getActivity(), "Please enter 5 digit id", Toast.LENGTH_SHORT).show();
                                        }
                                } else {

                                    // Output error messages to user
                                    Toast.makeText(getActivity(), "Please insert an  expense id", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.show();
                break;
            // Case 2 represents updating expenses
            case 2:

                // Init all necessary variables
                dialog.setContentView(R.layout.transactionupdate);
                IdInput = dialog.findViewById(R.id.IdInput);
                TitleText = dialog.findViewById(R.id.titleText);
                DateText = dialog.findViewById(R.id.DateText);
                AmountText = dialog.findViewById(R.id.AmountText);
                CategorySpinner = dialog.findViewById(R.id.CategorySpinner);
                DetailView = dialog.findViewById(R.id.DetailView);
                CategorySwitch = dialog.findViewById(R.id.CategorySwitch);
                UPDATE = dialog.findViewById(R.id.UPDATE);

                // Get categories into a list for spinner
                helper = new ExpenseTrackerHelper(getActivity());
                db = helper.getWritableDatabase();
                categoryList = helper.getCategories();

                // Create adapter for category list and spinner
                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Connect adapter to spinner and set it to false
                CategorySpinner.setAdapter(adapter);
                CategorySpinner.setEnabled(false);

                CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // When category switch is changed, turn category spinner off and on
                CategorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        CategorySpinner.setEnabled(isChecked);
                    }
                });

                // When update is clicked, begin storing input
                UPDATE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Check if expense id is 5 digits
                        if(IdInput.getText().toString().length() == 5){

                            // Store empty strings for if neither fields are filled
                            String categoryCheck = "";
                            String idCheck = "";

                            // Check if category spinner is enabled
                            if (CategorySpinner.isEnabled()) {
                                categoryCheck = CategorySpinner.getSelectedItem().toString();
                            }

                            // Store id into id check
                            if(!IdInput.getText().toString().matches("")) idCheck = (IdInput.getText().toString());

                            // Call update expense, toast will be determined by what it calls back
                            Toast.makeText(getActivity(),helper.updateExpense(idCheck, TitleText.getText().toString(), DateText.getText().toString(),
                                    AmountText.getText().toString(), DetailView.getText().toString(), categoryCheck), Toast.LENGTH_SHORT ).show();
                        } else {

                            // Call error message for user when id is not the right size
                            Toast.makeText(getActivity(), "Expense Id must be 5 digits long", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                dialog.show();
                break;
        }

    }

    // Show dialog boxes for category
    private void showCDialog(int id) {
        Dialog dialog = new Dialog(getActivity());
        EditText CategoryText;

        // Switch statement for checking which menu item had been clicked
        switch (id) {
            // When insert for category is selected
            case 0:
                // Get simple category add view
                dialog.setContentView(R.layout.categoryadd);

                CategoryText = dialog.findViewById(R.id.CategoryText);
                Button Insert = dialog.findViewById(R.id.Insert);

                Insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Get category name from edittext
                        String categoryName = CategoryText.getText().toString();

                        // If category name is empty, display error message
                        if (!categoryName.matches("")) {

                            // If category name contains a white space, display error message
                            if (!categoryName.contains(" ")) {

                                // If category name is greater than 20 characters, display error message
                                if (!(categoryName.length() > 20)) {

                                    // Add category to database and display success message
                                    helper = new ExpenseTrackerHelper(getActivity());
                                    SQLiteDatabase db = helper.getWritableDatabase();
                                    ContentValues row = new ContentValues();
                                    row.put(ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME, categoryName);
                                    db.insert(ExpenseTrackerContract.CategoryEntry.TABLE_NAME, null, row);
                                    db.close();
                                    Toast.makeText(getActivity(), "Category has been successfully added", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Category name must be no more than 20 characters", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "No white spaces allowed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please insert a category name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                break;

            // Case 1 represents removing a category
            case 1:
                // Get simple category add view
                dialog.setContentView(R.layout.categoryremove);

                CategoryText = dialog.findViewById(R.id.CategoryText);
                Button Remove = dialog.findViewById(R.id.Remove);

                Remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get category name from edittext
                        String[] categoryName = {CategoryText.getText().toString()};


                        // If category name is empty, display error message
                        if (!categoryName[0].matches("")) {

                            // If category name contains a white space, display error message
                            if (!categoryName[0].contains(" ")) {

                                // If category name is greater than 20 characters, display error message
                                if (!(categoryName[0].length() > 20)) {

                                    // Add category to database and display success message
                                    helper = new ExpenseTrackerHelper(getActivity());
                                    SQLiteDatabase db = helper.getWritableDatabase();
                                    Cursor cursor = db.rawQuery("select * from " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME + " where " + ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + " = ? COLLATE NOCASE", categoryName);

                                    // Check if category exist, if it does, remove it from the database and give a success message
                                    if (cursor.moveToFirst()) {
                                        if(!(cursor.getInt(0) == 1 || cursor.getInt(0) == 2 || cursor.getInt(0) == 3)){

                                            // Update already existing expenses with default category general
                                            String qry=  "UPDATE " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " SET " +
                                                    ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + " = " + 1 + " WHERE " +
                                                    ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + " = " + cursor.getInt(0);
                                            db.execSQL(qry);

                                            // Delete category from table
                                            db.execSQL("delete from " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME + " where " + ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_CID + " = " + cursor.getInt(0));

                                            // Display success message and close dialog
                                            Toast.makeText(getActivity(), "Category has been successfully remove", Toast.LENGTH_SHORT).show();
                                            cursor.close();
                                            db.close();
                                            dialog.dismiss();
                                        } else {

                                            // Error message for default categories 1 2 3
                                            Toast.makeText(getActivity(), "Cannot remove default categories", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {

                                        // Error message for when category is not found
                                        Toast.makeText(getActivity(), "Category does not exist, please re-enter name", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    // Error message for when category is too long
                                    Toast.makeText(getActivity(), "Category name must be no more than 20 characters", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                // Error message for when whitespaces are found
                                Toast.makeText(getActivity(), "No white spaces allowed", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            // Error message for when category name is blank
                            Toast.makeText(getActivity(), "Please insert a category name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                break;

            // Case 2 represents viewing a category
            case 2:

                // Init all important variables
                dialog.setContentView(R.layout.category_view);
                RecyclerView CategoryViewer = dialog.findViewById(R.id.recycleCategoryView);
                ArrayList<CategoryModel> expenseHolder = new ArrayList<>();
                CategoryViewer.setLayoutManager(new LinearLayoutManager(getActivity()));

                // Get cursor for all categories
                helper = new ExpenseTrackerHelper(getActivity());
                Cursor cursor = helper.getCursorCategories();

                // Store categories in a model for category model array
                CategoryModel model;
                if(cursor.moveToFirst()) {
                    model = new CategoryModel(cursor.getInt(0), cursor.getString(1));
                    expenseHolder.add(model);
                    while (cursor.moveToNext()) {
                        model = new CategoryModel(cursor.getInt(0), cursor.getString(1));
                        expenseHolder.add(model);
                    }
                }

                // Connect adapter to recycle viewer and show dialog
                CategoryAdapter adapter = new CategoryAdapter(expenseHolder, getActivity());
                CategoryViewer.setAdapter(adapter);
                dialog.show();
                break;
        }
    }
}