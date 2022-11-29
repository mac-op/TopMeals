package com.example.topgmeals.shoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.Ingredient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Custom Adapter to fit {@link Ingredient} objects into {@link RecyclerView}
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    /**
     * List of {@link Ingredient} objects to display
     */
    private ArrayList<Ingredient> mData;

    /**
     * {@link LayoutInflater} objects to display
     */
    private LayoutInflater mInflater;

    /**
     * {@link ItemClickListener} to listen for clicks on items
     */
    private ItemClickListener mClickListener;

    /**
     * data is passed into the constructor
     * @param context
     * @param mData
     */
    public ShoppingListAdapter(Context context, ArrayList<Ingredient> mData) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * inflates the row layout from xml when needed
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shoppinglist_content, parent, false);
        return new ViewHolder(view);
    }

    /**
     * binds the data to the TextView in each row
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String ingredientDescription = mData.get(position).getDescription();
        String ingredientAmount = Float.toString(mData.get(position).getAmount());
        String ingredientUnit = mData.get(position).getUnit();
        String ingredientCategory = mData.get(position).getCategory();

        holder.shopDescription.setText(ingredientDescription);
        holder.shopAmount.setText(ingredientAmount + " " + ingredientUnit);
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

    /**
     * total number of rows
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * stores and recycles views as they are scrolled off screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shopDescription;
        TextView shopAmount;
        TextView shopCategory;
        CheckBox itemChecked;

        ViewHolder(View itemView) {
            super(itemView);
            shopDescription = (TextView) itemView.findViewById(R.id.shoppingListDescription);
            shopAmount = (TextView) itemView.findViewById(R.id.shoppingListAmount);
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

    /**
     * convenience method for getting data at click position
     * @param id
     * @return
     */
    String getItem(int id) {
        return mData.get(id).getDescription();
    }

    /**
     * allows clicks events to be caught
     * @param itemClickListener
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * parent activity will implement this method to respond to click events
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}