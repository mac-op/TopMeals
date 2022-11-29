package com.example.topgmeals.shoppinglist;

import com.example.topgmeals.ingredientstorage.Ingredient;

import java.util.ArrayList;

public class ShoppingGlobalVars {
    private static ShoppingGlobalVars instance;

    private ArrayList<Ingredient> list;

    private ArrayList<Ingredient> curIngredientsList;

    public ArrayList getcurIngredientsList() {
        return curIngredientsList;
    }

    public void setcurIngredientsList(ArrayList list) {
        this.curIngredientsList = list;
    }

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    private ShoppingGlobalVars(){}

    public static ShoppingGlobalVars getInstance(){
        if(instance == null){
            instance = new ShoppingGlobalVars();
        }
        return instance;
    }
}