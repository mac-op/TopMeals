package com.example.topgmeals;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Ingredient> ingredientList;
    private static View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }


    public CustomAdapter(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description, bestBefore, count, category, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            bestBefore = itemView.findViewById(R.id.best_before);
            count = itemView.findViewById(R.id.count);
            category = itemView.findViewById(R.id.category);
            location = itemView.findViewById(R.id.location );

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);
        DateFormat dateFormat = new DateFormat();

        holder.description.setText(ingredient.getDescription());
        holder.bestBefore.setText(dateFormat.parse(ingredient.getBestBefore()));
        holder.count.setText(ingredient.getAmount() + ingredient.getUnit());
        holder.category.setText(ingredient.getCategory());
        holder.location.setText(ingredient.getLocation());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

}
