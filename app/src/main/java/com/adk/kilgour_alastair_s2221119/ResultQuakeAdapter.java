package com.adk.kilgour_alastair_s2221119;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultQuakeAdapter extends RecyclerView.Adapter<ResultQuakeViewHolder> {
    ArrayList<ResultQuake> quakes;
    Context context;

    public ResultQuakeAdapter(ArrayList<ResultQuake> quakes, Context context) {
        this.quakes = quakes;
        this.context = context;
    }

    @Override
    public ResultQuakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View quakeView = inflater.inflate(R.layout.resultquake_card, parent, false);
        ResultQuakeViewHolder viewHolder = new ResultQuakeViewHolder(quakeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ResultQuakeViewHolder holder, int position) {
        holder.quakeDate.setText(quakes.get(position).getQuake().getDate());
        holder.quakeLocality.setText(quakes.get(position).getQuake().getLocality());
        holder.quakeRegion.setText(quakes.get(position).getQuake().getRegion());
        holder.moreInfoBtn.setId(position);
        holder.moreInfoBtn.setOnClickListener(v -> { // creates the buttons listener
            ResultQuake e = quakes.get(v.getId()); // gets the ResultQuake this button is attributed with
            Intent moreInfo = new Intent(v.getContext(), MoreInfo.class);
            moreInfo.putExtra("selectedQuake", (Serializable) e);
            this.context.startActivity(moreInfo);
        });
    }

    @Override
    public int getItemCount() {
        return quakes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
