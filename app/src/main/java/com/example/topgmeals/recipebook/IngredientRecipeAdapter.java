package com.example.topgmeals.recipebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.Ingredient;

import java.util.List;

/**
 * Ingredient Recipe Adapter to fit {@link Ingredient} objects into {@link ArrayAdapter}
 */
public class IngredientRecipeAdapter extends ArrayAdapter<Ingredient> {

    private static final String TAG = "IngredientListAdapter";
    private Context context;
    private int resource;

    /**
     * Constructor for {@link IngredientRecipeAdapter}
     */
    public IngredientRecipeAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    /**
     * A {@link View} class to get the view of an {@link Ingredient} object
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String description = getItem(position).getDescription();
        Float amount = getItem(position).getAmount();
        String unit = getItem(position).getUnit();
        String category = getItem(position).getCategory();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView description_display = convertView.findViewById(R.id.ingredient_description);
        TextView amount_unit_display = convertView.findViewById(R.id.ingredient_amount_unit);
        TextView category_display = convertView.findViewById(R.id.ingredient_category_id);

        description_display.setText(description);
        amount_unit_display.setText(amount.toString() + " " + unit);
        category_display.setText(category);

        return convertView;
    }
}
