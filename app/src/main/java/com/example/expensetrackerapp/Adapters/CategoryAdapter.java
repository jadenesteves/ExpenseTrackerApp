/**
 * Name: CategoryAdapter.java
 * Last Updated: 5/3/2024
 * Description: Adapter class used for display categories on recycle viewer
 */
package com.example.expensetrackerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackerapp.Models.CategoryModel;
import com.example.expensetrackerapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    // Declare storage for the models and context
    ArrayList<CategoryModel> data;
    private Context context;

    // Constructor for category adapter class
    public CategoryAdapter(ArrayList<CategoryModel> holder, Context context){
        this.data = holder;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Set text of holder dependant on position
        holder.DisplayCID.setText(Integer.toString(data.get(position).getCid()));
        holder.CategoryDisplay.setText(data.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    // Class for holding view
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DisplayCID, CategoryDisplay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initiate text views for holder
            DisplayCID = (TextView) itemView.findViewById(R.id.DisplayCID);
            CategoryDisplay = (TextView) itemView.findViewById(R.id.CategoryDisplay);
        }


    }
}

