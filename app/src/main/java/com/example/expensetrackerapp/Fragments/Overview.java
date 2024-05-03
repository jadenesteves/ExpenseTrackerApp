/**
 * Name: Overview.java
 * Last Updated: 5/3/2024
 * Description: Class for holding all overview functions, displays total loss and 5 most recent additions
 */
package com.example.expensetrackerapp.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetrackerapp.Adapters.ExpenseAdapter;
import com.example.expensetrackerapp.Models.ExpenseModel;
import com.example.expensetrackerapp.ExpenseTracker.ExpenseTrackerHelper;
import com.example.expensetrackerapp.R;

import java.util.ArrayList;

public class Overview extends Fragment {

    // Declare variables
    ExpenseTrackerHelper helper;
    View view;
    RecyclerView recyclerView;
    ArrayList<ExpenseModel> expenseHolder;
    TextView ExpenseTotal;

    public Overview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.overview, container, false);
        expenseHolder = new ArrayList<ExpenseModel>();
        ExpenseTotal = (TextView) view.findViewById(R.id.totalExpenses);
        recyclerView = (RecyclerView) view.findViewById(R.id.overviewRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get the total expenses from the last 30 days
        helper = new ExpenseTrackerHelper(getActivity());
        ExpenseTotal.setText("$-" + helper.totalExpenses30().toString());

        // Get the last five expenses cursor and store each expense in array
        Cursor cursor = helper.readFiveExpenses();
        ExpenseModel expenseModel;
        if(cursor.moveToFirst()) {
            expenseModel = new ExpenseModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
            expenseHolder.add(expenseModel);
            while (cursor.moveToNext()) {
                expenseModel = new ExpenseModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
                expenseHolder.add(expenseModel);
            }
        }

        // Connect adapter and recycle viewer
        ExpenseAdapter adapter = new ExpenseAdapter(expenseHolder, getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}