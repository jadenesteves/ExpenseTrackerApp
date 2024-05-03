package com.example.expensetrackerapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Overview extends Fragment {
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

        helper = new ExpenseTrackerHelper(getActivity());
        ExpenseTotal.setText("$-" + helper.totalExpenses30().toString());

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

        ExpenseAdapter adapter = new ExpenseAdapter(expenseHolder, getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}