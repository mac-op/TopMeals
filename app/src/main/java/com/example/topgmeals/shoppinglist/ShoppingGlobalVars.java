package com.example.topgmeals.shoppinglist;

import com.example.topgmeals.ingredientstorage.Ingredient;

import java.util.ArrayList;

/**
 * This class is used as a Global Variable Storage for Shopping List events
 */
public class ShoppingGlobalVars {
    /**
     * Stores the instance of the Global Var Storage
     */
    private static ShoppingGlobalVars instance;

    /**
     * An {@link ArrayList} that holds the {@link Ingredient} objects.
     */
    private ArrayList<Ingredient> list;

    /**
     * An {@link ArrayList} that holds the current {@link Ingredient} objects.
     */
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

    /**
     * Initializes an instance for an Activity
     */
    public static ShoppingGlobalVars getInstance(){
        if(instance == null){
            instance = new ShoppingGlobalVars();
        }
        return instance;
    }
}
