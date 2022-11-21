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

import java.util.List;

/**
 * Recipe Adapter to fit {@link Recipe} objects into {@link ArrayAdapter}
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private static final String TAG = "RecipeListAdapter";
    private Context context;
    private int resource;

    /**
     * Constructor for {@link RecipeAdapter}
     */
    public RecipeAdapter(@NonNull Context context, int resource, @NonNull List<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    /**
     * A {@link View} class to get the view of an {@link Recipe} object
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String title = getItem(position).getTitle();
        String prep_time = getItem(position).getPrepTime();
        String servings = getItem(position).getServings();
        String category = getItem(position).getCategory();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView title_display = (TextView) convertView.findViewById(R.id.recipe_title_id);
        TextView prep_time_display = (TextView) convertView.findViewById(R.id.prep_time_id);
        TextView servings_display = (TextView) convertView.findViewById(R.id.serving_id);
        TextView category_display = (TextView) convertView.findViewById(R.id.category_id);

        title_display.setText(title.toString());
        prep_time_display.setText(prep_time.toString());
        servings_display.setText(servings.toString());
        category_display.setText(category);

        return convertView;
    }
}

