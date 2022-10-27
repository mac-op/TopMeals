package com.example.topgmeals;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> recipeList;
    private static View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }


    public RecipeAdapter(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, prepTime, servings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            prepTime = itemView.findViewById(R.id.Preparation_Time);
            servings = itemView.findViewById(R.id.Serving);


            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }

    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipee_book_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.title.setText(recipe.getTitle());
        holder.prepTime.setText(recipe.getPrepTime());
        holder.servings.setText(recipe.getServings());


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}

