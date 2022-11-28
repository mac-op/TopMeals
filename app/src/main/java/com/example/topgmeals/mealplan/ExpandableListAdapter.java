package com.example.topgmeals.mealplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.topgmeals.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * {@link ExpandableListAdapter} to hold the meals where they are grouped by date.
 *
 * Code adapted from this
 * <a href="http://theopentutorials.com/tutorials/android/listview/android-expandable-list-view-example/">
 * tutorial </a>
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final OnDeletePressedListener listener;

    /**
     * {@link ArrayList} to hold the groups, ie. the dates.
     */
    private final ArrayList<String> dateList;

    /**
     * {@link HashMap} to hold each group's items, ie. each meal associated with the dates.
     */
    private final HashMap<String, ArrayList<Meal>> mealsByDate;

    public ExpandableListAdapter(Context context, ArrayList<String> dateList,
                                 HashMap<String, ArrayList<Meal>> mealsByDate){
        this.context = context;
        this.listener = (OnDeletePressedListener) context;
        this.dateList = dateList;
        this.mealsByDate = mealsByDate;
    }

    @Override
    public int getGroupCount() {
        return dateList.size();
    }

    @Override
    public int getChildrenCount(int groupPos) {
        return mealsByDate.get(getGroup(groupPos)).size();
    }

    @Override
    public String getGroup(int groupPos) {
        return dateList.get(groupPos);
    }

    @Override
    public Meal getChild(int groupPos, int expandedListPos) {
        return mealsByDate.get(dateList.get(groupPos)).get(expandedListPos);
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

    /**
     * This method handles the view for each group in the list, ie. each date.
     * @param i position of the item in the list
     * @param b whether the view is expanded
     * @param view the view to use
     * @param viewGroup the parent of this view
     * @return created view
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String selectedDate = getGroup(i);

        if (view == null){
            LayoutInflater layoutInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.meal_group, null);
        }
        TextView dateText = view.findViewById(R.id.meal_date_text);
        dateText.setText(selectedDate);
        return view;
    }

    /**
     * This methods handles the view for each item in each group, ie. each meal in a date.
     * @param listPos position of date in the list
     * @param expandedListPos position of meal in the date
     * @param b whether the view is expanded
     * @param view the view to use
     * @param viewGroup the parent of the view
     * @return created view
     */
    @Override
    public View getChildView(int listPos, int expandedListPos, boolean b, View view,
                             ViewGroup viewGroup) {
        Meal mealItem = getChild(listPos, expandedListPos);
        if (view == null){
            LayoutInflater layoutInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.meal_item, null);
        }
        TextView mealName = view.findViewById(R.id.meal_name);
        mealName.setText(mealItem.getMealName());

        TextView  mealServing = view.findViewById(R.id.num_serving);
        mealServing.setText(String.format("Servings: %d", mealItem.getNumServings()));

        // Set delete Button
        ImageView delete = view.findViewById(R.id.delete_meal_item);
        delete.setOnClickListener(view1 -> {
            AlertDialog.Builder cancelDialog =
                    new AlertDialog.Builder(view1.getContext());
            cancelDialog.setMessage("Are you sure you want to delete this " +
                            "meal?").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String docRef = mealItem.getDocRef();

                            listener.deleteMeal(docRef);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertCancel = cancelDialog.create();
            alertCancel.setTitle("Delete Confirmation");
            alertCancel.show();
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true ;
    }

    public interface OnDeletePressedListener{
        void deleteMeal(String docRef);
    }
}