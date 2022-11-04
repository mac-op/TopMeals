package com.example.topgmeals.recipe.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredient.storage.Ingredient;

import java.util.List;

public class IngredientRecipeAdapter extends ArrayAdapter<Ingredient> {

    private static final String TAG = "IngredientListAdapter";
    private Context context;
    private int resource;


    public IngredientRecipeAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String description = getItem(position).getDescription();
        Float amount = getItem(position).getAmount();
        String unit = getItem(position).getUnit();
        String category = getItem(position).getCategory();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView description_display = (TextView) convertView.findViewById(R.id.ingredient_description);
        TextView amount_display = (TextView) convertView.findViewById(R.id.ingredient_amount);
        TextView unit_display = (TextView) convertView.findViewById(R.id.ingredient_unit);
        TextView category_display = (TextView) convertView.findViewById(R.id.ingredient_category_id);

        description_display.setText(description);
        amount_display.setText(amount.toString());
        unit_display.setText(unit);
        category_display.setText(category);

        return convertView;
    }
}
