package com.example.topgmeals.shoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private ArrayList<Ingredient> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor

    public ShoppingListAdapter(Context context, ArrayList<Ingredient> mData) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shoppinglist_content, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String ingredientDescription = mData.get(position).getDescription();
        String ingredientAmount = Float.toString(mData.get(position).getAmount());
        String ingredientUnit = mData.get(position).getUnit();
        String ingredientCategory = mData.get(position).getCategory();

        holder.shopDescription.setText(ingredientDescription);
        holder.shopAmount.setText(ingredientAmount);
        holder.shopUnit.setText(ingredientUnit);
        holder.shopCategory.setText(ingredientCategory);

        final CheckBox cb = holder.itemChecked;

        cb.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    Log.e("I AM ", "HERE-2");
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    }
                    catch (Exception e){

                    }
                    cb.setChecked(false);
                    mData.remove(position);
                    notifyDataSetChanged();

                }
                // Inform to Activity or the Fragment where the RecyclerView reside.
            }
        });
//in some cases, it will prevent unwanted situations
        cb.setOnCheckedChangeListener(null);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shopDescription;
        TextView shopAmount;
        TextView shopUnit;
        TextView shopCategory;
        CheckBox itemChecked;

        ViewHolder(View itemView) {
            super(itemView);
            shopDescription = (TextView) itemView.findViewById(R.id.shoppingListDescription);
            shopAmount = (TextView) itemView.findViewById(R.id.shoppingListAmount);
            shopUnit = (TextView) itemView.findViewById(R.id.shoppingListUnit);
            shopCategory = (TextView) itemView.findViewById(R.id.shoppingListIngredientCategory);
            itemChecked = (CheckBox) itemView.findViewById(R.id.checkItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Log.e("us", "CLIKBAIT");

        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getDescription();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        String ingredientDescription = getItem(position).getDescription();
//        String ingredientAmount = Float.toString(getItem(position).getAmount());
//        String ingredientUnit = getItem(position).getUnit();
//        String ingredientCategory = getItem(position).getCategory();
//
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        convertView = inflater.inflate(resource,parent,false);
//
//        TextView shopDescription = (TextView) convertView.findViewById(R.id.shoppingListDescription);
//        TextView shopAmount = (TextView) convertView.findViewById(R.id.shoppingListAmount);
//        TextView shopUnit = (TextView) convertView.findViewById(R.id.shoppingListUnit);
//        TextView shopCategory = (TextView) convertView.findViewById(R.id.shoppingListIngredientCategory);
//
//        shopDescription.setText(ingredientDescription);
//        shopAmount.setText(ingredientAmount);
//        shopUnit.setText(ingredientUnit);
//        shopCategory.setText(ingredientCategory);
//
//        return convertView;
//    }

}
