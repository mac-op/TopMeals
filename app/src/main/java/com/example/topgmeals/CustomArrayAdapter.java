package com.example.topgmeals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Ingredient> {
    private ArrayList<Ingredient> ingredientList;
    private Context context;

    public CustomArrayAdapter(Context context, ArrayList<Ingredient> ingredientList){
        super(context, 0, ingredientList);
        this.ingredientList = ingredientList;
        this.context = context;
    }


    /*
    Create new view on content_ingredient_item.xml and populate with data from ingredientList
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.ingredient_content, parent, false);
        }

        Ingredient ingredient = ingredientList.get(position);
        TextView description = view.findViewById(R.id.description);
        TextView bestBefore = view.findViewById(R.id.best_before);
        TextView count = view.findViewById(R.id.count);
        TextView category = view.findViewById(R.id.category);
        TextView location = view.findViewById(R.id.location );

        description.setText(ingredient.getDescription());
        bestBefore.setText(ingredient.getBestBefore().toString());
        count.setText(ingredient.getAmount() + ingredient.getUnit());
        category.setText(ingredient.getCategory());
        location.setText(ingredient.getLocation());
        return view;
    }
}

