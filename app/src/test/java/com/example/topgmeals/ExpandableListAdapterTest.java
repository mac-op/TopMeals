package com.example.topgmeals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.topgmeals.mealplan.ExpandableListAdapter;
import com.example.topgmeals.mealplan.Meal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ExpandableListAdapterTest {
    private ExpandableListAdapter adapter;

    public ExpandableListAdapter mockAdapter(){
        ArrayList<String> mockDates = new ArrayList<>(
                Arrays.asList(
                    "11/16/2020", "09/09/2022", "11/11/2021"
        ));
        HashMap<String, ArrayList<Meal>> map = new HashMap<>();
        for (int i=0; i < 3; i++){
            ArrayList<Meal> meals = new ArrayList<>();
            meals.add(new Meal());
            map.put(mockDates.get(i), meals);
        }
        return new ExpandableListAdapter(null, mockDates, map);
    }

    @Test
    public void getGroupCountTest(){
        adapter = mockAdapter();
        assertEquals(3, adapter.getGroupCount());
    }

    @Test
    public void getGroupTest(){
        adapter = mockAdapter();
        String date = adapter.getGroup(1);
        assertEquals("09/09/2022", date);
    }

    @Test
    public void getChildrenCountTest(){
        adapter = mockAdapter();
        assertEquals(1, adapter.getChildrenCount(1));
    }
}
