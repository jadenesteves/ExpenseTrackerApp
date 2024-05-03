/**
 * Name: Transaction.java
 * Last Updated: 5/3/2024
 * Description: Class for transaction functions, includes searching for specific transactions
 */
package com.example.expensetrackerapp.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.expensetrackerapp.Adapters.ExpenseAdapter;
import com.example.expensetrackerapp.Models.ExpenseModel;
import com.example.expensetrackerapp.ExpenseTracker.ExpenseTrackerHelper;
import com.example.expensetrackerapp.R;

import java.util.ArrayList;

public class Transactions extends Fragment {

    // Declare all needed variables
    ExpenseTrackerHelper helper;
    View view;
    RecyclerView recyclerView;
    ArrayList<ExpenseModel> expenseHolder;
    ExpenseAdapter adapter;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.transactions, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        expenseHolder = new ArrayList<ExpenseModel>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        toolbar = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.toolbar2);

        // Read all expenses and store them into a arraylist
        helper = new ExpenseTrackerHelper(getActivity());
        Cursor cursor = helper.readAllExpenses();
        ExpenseModel expenseModel;
        if (cursor.moveToFirst()) {
            expenseModel = new ExpenseModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
            expenseHolder.add(expenseModel);
            while (cursor.moveToNext()) {
                expenseModel = new ExpenseModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
                expenseHolder.add(expenseModel);
            }
        }

        // Connect adapter and recycle viewer to show to user
        adapter = new ExpenseAdapter(expenseHolder, getActivity());
        recyclerView.setAdapter(adapter);

        // Set action bar to tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar((toolbar));
        toolbar.setTitle("");
        return view;
    }

    // Calls menu when action bar is present
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){

        // Inflate menu with custom search bar
        inflater.inflate(R.menu.nav_search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView SearchView = (SearchView) menuItem.getActionView();

        // Text Listener for search view
        SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            // When the text is changed, display filtered list for user
            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<ExpenseModel> newList = new ArrayList<>();
                for(ExpenseModel model : expenseHolder){
                    String title = model.getTitle().toLowerCase();
                    if(title.contains(newText)){
                        newList.add(model);
                    }
                }

                adapter.setFilter(newList);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
    }
}