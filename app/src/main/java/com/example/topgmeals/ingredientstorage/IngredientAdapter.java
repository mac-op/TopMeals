package com.example.topgmeals.ingredientstorage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topgmeals.R;
import com.example.topgmeals.utils.DateFormat;

import java.util.ArrayList;

/**
 * Custom Adapter to fit {@link Ingredient} objects into {@link RecyclerView}
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    /**
     * List of {@link Ingredient} objects to display
     */
    private ArrayList<Ingredient> ingredientList;

    /**
     * {@link View.OnClickListener} to handle the event where an item in the view is clicked on
     */
    private static View.OnClickListener onItemClickListener;

    /**
     * Method to set an OnItemClickListener for the IngredientAdapter
     * @param clickListener
     */
    public void setOnItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    /**
     * Constructor for {@link IngredientAdapter}
     * @param ingredientList list of {@link Ingredient} objects to display
     */
    public IngredientAdapter(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * A {@link ViewHolder} class to hold the view of an {@link Ingredient} object
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description, bestBefore, count, category, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description_content);
            bestBefore = itemView.findViewById(R.id.best_before);
            count = itemView.findViewById(R.id.count);
            category = itemView.findViewById(R.id.category_);
            location = itemView.findViewById(R.id.location );

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }

        public TextView getDescription(){
            return description;
        }
    }

    /**
     * Method to display the layout of a {@link ViewHolder}. Called when the ViewHolder is created.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_content, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Method to display the content of a {@link ViewHolder}. Called when the ViewHolder is bound to
     * and index of the list
     * @param holder the ViewHolder that holds the object
     * @param position position of the object in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);
        DateFormat dateFormat = new DateFormat();

        float amountFloat = ingredient.getAmount();
        String amountString;
        if (amountFloat % 1 ==0) {
            amountString = String.valueOf((int) amountFloat);
        } else{
            amountString = String.valueOf(amountFloat);
        }

        holder.description.setText(ingredient.getDescription());
        holder.bestBefore.setText(
                String.format("Best Before: %s", dateFormat.parse(ingredient.getBestBefore())));
        holder.count.setText(String.format("Amount: %s %s", amountString, ingredient.getUnit()));
        holder.category.setText(String.format("Category: %s", ingredient.getCategory()));
        holder.location.setText(String.format("Location: %s", ingredient.getLocation()));
    }

    /**
     * Method to get the total item count of the list
     */
    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

}
