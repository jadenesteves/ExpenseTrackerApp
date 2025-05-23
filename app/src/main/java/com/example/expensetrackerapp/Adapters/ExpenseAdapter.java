/**
 * Name: ExpenseAdapter.java
 * Last Updated: 5/3/2024
 * Description: Adapter class for expense entries
 */
package com.example.expensetrackerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackerapp.Models.ExpenseModel;
import com.example.expensetrackerapp.R;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder>{

    // Declare list for storing data and context
    ArrayList<ExpenseModel> data;
    private Context context;

    public ExpenseAdapter(ArrayList<ExpenseModel> holder, Context context){
        this.data = holder;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenserow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Set information dependant on position in array
        holder.DisplayId.setText(Integer.toString(data.get(position).getId()));
        holder.DisplayTitle.setText(data.get(position).getTitle());
        holder.DisplayDate.setText(data.get(position).getDate());
        holder.DisplayAmount.setText("$"+ data.get(position).getAmount());
        holder.DisplayCategory.setText(data.get(position).getCategory());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DisplayId, DisplayTitle, DisplayDate, DisplayAmount, DisplayCategory;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            DisplayId = (TextView) itemView.findViewById(R.id.DisplayCID);
            DisplayTitle = (TextView) itemView.findViewById(R.id.CategoryDisplay);
            DisplayDate = (TextView) itemView.findViewById(R.id.DisplayDate);
            DisplayAmount = (TextView) itemView.findViewById(R.id.DisplayAmount);
            DisplayCategory = (TextView) itemView.findViewById(R.id.DisplayCategory);
        }


    }

    // Method for setting array as new filtered array
    public void setFilter(ArrayList<ExpenseModel> newList){
        data = new ArrayList<>();
        data.addAll(newList);
        notifyDataSetChanged();
    }
}

