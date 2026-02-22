package com.example.diettracker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<String> dateList;
    public HistoryAdapter(List<String> dateList) {
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String date = dateList.get(position);
        holder.dateTv.setText(date);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TodayDetailsActivity.class);
            intent.putExtra("SELECTED_DATE", date);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.dateTv);
        }
    }
}
