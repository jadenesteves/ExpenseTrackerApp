package com.example.expensetrackerapp;

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

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Fragment {
    View view;
    Button Expenses;
    Button Category;
    ExpenseTrackerHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);

        Category = view.findViewById(R.id.Category);
        Expenses = view.findViewById(R.id.Expenses);

        Expenses.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getActivity(), Expenses);
            popup.getMenuInflater().inflate(R.menu.nav_transaction, popup.getMenu());

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
            popup.show();

        });

        Category.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getActivity(), Category);
            popup.getMenuInflater().inflate(R.menu.nav_category, popup.getMenu());

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
            popup.show();

        });
        return view;
    }

    // Show dialog boxes are transactions
    private void showTDialog(int id) {
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
            case 0:

                dialog.setContentView(R.layout.transactionadd);

                TitleText = dialog.findViewById(R.id.IdInput);
                DateText = dialog.findViewById(R.id.DateText);
                AmountText = dialog.findViewById(R.id.AmountText);
                CategorySpinner = dialog.findViewById(R.id.CategorySpinner);
                DetailView = dialog.findViewById(R.id.DetailView);
                ADD = dialog.findViewById(R.id.UPDATE);

                helper = new ExpenseTrackerHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                List<String> categoryList = helper.getCategories();

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                CategorySpinner.setAdapter(adapter);

                CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                ADD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean validPhone = true;
                        if (!TitleText.getText().toString().matches("") && !AmountText.getText().toString().matches("")) {
                            if (TitleText.getText().toString().length() < 20) {
                                if (AmountText.getText().toString().matches("^\\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(\\.[0-9][0-9])?$")) {
                                    if (!DateText.getText().toString().matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$") && DateText.getText().toString().length() > 0) {
                                        validPhone = false;
                                    }
                                    if (validPhone) {
                                        String title = TitleText.getText().toString().trim();
                                        String date = DateText.getText().toString().trim();
                                        String amount = AmountText.getText().toString();
                                        String categoryName = CategorySpinner.getSelectedItem().toString().trim();
                                        String details = DetailView.getText().toString();
                                        int cid = 0;

                                        helper = new ExpenseTrackerHelper(getActivity());
                                        SQLiteDatabase db = helper.getWritableDatabase();

                                        String[] categoryArray = {CategorySpinner.getSelectedItem().toString()};
                                        Cursor cursor = db.rawQuery("select * from " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME +
                                                " where " + ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_NAME + " = ?", categoryArray);
                                        if (cursor.moveToFirst()) {
                                            cid = cursor.getInt(0);
                                        }

                                        ContentValues row = new ContentValues();
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_TITLE, title);
                                        if (date.length() > 0) {
                                            row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DATE, date);
                                        }
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_AMOUNT, amount);
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_DETAILS, details);
                                        row.put(ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID, cid);

                                        db.insert(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, null, row);
                                        Toast.makeText(getActivity(), "Transaction successfully added", Toast.LENGTH_SHORT).show();
                                        cursor.close();
                                        db.close();
                                    } else {
                                        Toast.makeText(getActivity(), "Incorrect date format (YYYY-MM-DD)", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Incorrect amount format (_.00", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Your title must be under 20 characters long", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "You must input a title and amount for a transaction", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
                break;
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

                                            db.execSQL("DELETE FROM " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " WHERE " + ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_ID + "=" + id);
                                            db.close();
                                            Toast.makeText(getActivity(), "Expense has been successfully removed", Toast.LENGTH_SHORT).show();

                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), "Please enter 5 digit id", Toast.LENGTH_SHORT).show();
                                        }
                                } else {
                                    Toast.makeText(getActivity(), "Please insert an  expense id", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.show();
                break;
            case 2:
                dialog.setContentView(R.layout.transactionupdate);
                IdInput = dialog.findViewById(R.id.IdInput);
                TitleText = dialog.findViewById(R.id.titleText);
                DateText = dialog.findViewById(R.id.DateText);
                AmountText = dialog.findViewById(R.id.AmountText);
                CategorySpinner = dialog.findViewById(R.id.CategorySpinner);
                DetailView = dialog.findViewById(R.id.DetailView);
                CategorySwitch = dialog.findViewById(R.id.CategorySwitch);
                UPDATE = dialog.findViewById(R.id.UPDATE);

                helper = new ExpenseTrackerHelper(getActivity());
                db = helper.getWritableDatabase();
                categoryList = helper.getCategories();

                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

                CategorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        CategorySpinner.setEnabled(isChecked);
                    }
                });

                UPDATE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(IdInput.getText().toString().length() == 5){
                            String categoryCheck = "";
                            String idCheck = "";
                            if (CategorySpinner.isEnabled()) {
                                categoryCheck = CategorySpinner.getSelectedItem().toString();
                            }

                            if(!IdInput.getText().toString().matches("")) idCheck = (IdInput.getText().toString());

                            Toast.makeText(getActivity(),helper.updateExpense(idCheck, TitleText.getText().toString(), DateText.getText().toString(),
                                    AmountText.getText().toString(), DetailView.getText().toString(), categoryCheck), Toast.LENGTH_SHORT ).show();
                        } else {
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

                                            String qry=  "UPDATE " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " SET " +
                                                    ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + " = " + 1 + " WHERE " +
                                                    ExpenseTrackerContract.ExpenseEntry.COLUMN_NAME_CID + " = " + cursor.getInt(0);
                                            db.execSQL(qry);
                                            db.execSQL("delete from " + ExpenseTrackerContract.CategoryEntry.TABLE_NAME + " where " + ExpenseTrackerContract.CategoryEntry.COLUMN_NAME_CID + " = " + cursor.getInt(0));
                                            Toast.makeText(getActivity(), "Category has been successfully remove", Toast.LENGTH_SHORT).show();
                                            cursor.close();
                                            db.close();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), "Cannot remove default categories", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Category does not exist, please re-enter name", Toast.LENGTH_SHORT).show();
                                    }
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
            case 2:
                dialog.setContentView(R.layout.category_view);
                RecyclerView CategoryViewer = dialog.findViewById(R.id.recycleCategoryView);
                ArrayList<CategoryModel> expenseHolder = new ArrayList<>();
                CategoryViewer.setLayoutManager(new LinearLayoutManager(getActivity()));

                helper = new ExpenseTrackerHelper(getActivity());

                Cursor cursor = helper.getCursorCategories();

                CategoryModel model;
                if(cursor.moveToFirst()) {
                    model = new CategoryModel(cursor.getInt(0), cursor.getString(1));
                    expenseHolder.add(model);
                    while (cursor.moveToNext()) {
                        model = new CategoryModel(cursor.getInt(0), cursor.getString(1));
                        expenseHolder.add(model);
                    }
                }

                CategoryAdapter adapter = new CategoryAdapter(expenseHolder, getActivity());
                CategoryViewer.setAdapter(adapter);
                dialog.show();
                break;
        }
    }
}