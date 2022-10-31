package com.example.topgmeals;

public class IngredientRecipe {
    private String name;
    private String description;
    private Integer quantity;
    private String categoryIngredient;

    public IngredientRecipe(String name, String description, int quantity, String categoryIngredient) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.categoryIngredient = categoryIngredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategoryIngredient() {
        return categoryIngredient;
    }

    public void setCategoryIngredient(String categoryIngredient) {
        this.categoryIngredient = categoryIngredient;
    }
}

