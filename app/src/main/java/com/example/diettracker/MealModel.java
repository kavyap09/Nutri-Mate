package com.example.diettracker;

public class MealModel {

    String foodName;
    double protein;
    double carbs;
    double fats;

    public MealModel() {}

    public MealModel(String foodName, double protein, double carbs, double fats) {
        this.foodName = foodName;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getFoodName() { return foodName; }
    public double getProtein() { return protein; }
    public double getCarbs() { return carbs; }
    public double getFats() { return fats; }
}
