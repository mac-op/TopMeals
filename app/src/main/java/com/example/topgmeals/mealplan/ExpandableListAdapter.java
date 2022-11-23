package com.example.topgmeals.mealplan;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.topgmeals.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final ArrayList<String> dateList;
    private final HashMap<String, ArrayList<Meal>> mealsByDate;

    public ExpandableListAdapter(Context context, ArrayList<String> dateList,
                                 HashMap<String, ArrayList<Meal>> mealsByDate){
        this.context = context;
        this.dateList = dateList;
        this.mealsByDate = mealsByDate;
    }

    @Override
    public int getGroupCount() {
        return dateList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mealsByDate.get(getGroup(i)).size();
    }

    @Override
    public String getGroup(int i) {
        return dateList.get(i);
    }

    @Override
    public Meal getChild(int listPos, int expandedListPos) {
        return mealsByDate.get(dateList.get(listPos)).get(expandedListPos);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int listPos, int expandedListPos) {
        return expandedListPos;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String selectedDate = getGroup(i);

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.meal_group, null);
        }
        TextView dateText = view.findViewById(R.id.meal_date_text);
        dateText.setText(selectedDate);
        return view;
    }

    @Override
    public View getChildView(int listPos, int expandedListPos, boolean b, View view, ViewGroup viewGroup) {
        Meal mealItem = getChild(listPos, expandedListPos);
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.meal_item, null);
        }
        TextView mealName = view.findViewById(R.id.meal_name);
        mealName.setText(mealItem.getMealName());

        TextView  mealServing = view.findViewById(R.id.meal_serving);
        mealServing.setText(String.format("Servings: %d", mealItem.getNumServings()));
        // TODO: setOnClickListener for edit and delete
        ImageView editButton = view.findViewById(R.id.edit_meal_item);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test edit button","Edit clicked");
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true ;
    }
}
