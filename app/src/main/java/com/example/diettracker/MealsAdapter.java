package com.example.diettracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    List<MealModel> mealList;

    public MealsAdapter(List<MealModel> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MealModel meal = mealList.get(position);

        holder.foodNameTv.setText(meal.getFoodName());
        holder.macrosTv.setText(
                "Protein: " + meal.getProtein() + "g | " +
                        "Carbs: " + meal.getCarbs() + "g | " +
                        "Fats: " + meal.getFats() + "g"
        );
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView foodNameTv, macrosTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTv = itemView.findViewById(R.id.foodNameTv);
            macrosTv = itemView.findViewById(R.id.macrosTv);
        }
    }
}
