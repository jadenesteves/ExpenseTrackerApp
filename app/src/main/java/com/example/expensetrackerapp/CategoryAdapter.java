package com.example.expensetrackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    ArrayList<CategoryModel> data;
    private Context context;
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

        holder.DisplayCID.setText(Integer.toString(data.get(position).getCid()));
        holder.CategoryDisplay.setText(data.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DisplayCID, CategoryDisplay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            DisplayCID = (TextView) itemView.findViewById(R.id.DisplayCID);
            CategoryDisplay = (TextView) itemView.findViewById(R.id.CategoryDisplay);
        }


    }
}

