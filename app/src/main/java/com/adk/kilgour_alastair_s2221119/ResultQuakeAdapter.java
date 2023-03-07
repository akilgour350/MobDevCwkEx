package com.adk.kilgour_alastair_s2221119;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultQuakeAdapter extends RecyclerView.Adapter<ResultQuakeViewHolder> {
    ArrayList<ResultQuake> quakes;
    Context context;

    public ResultQuakeAdapter(ArrayList<ResultQuake> quakes, Context context) {
        this.quakes = quakes;
        this.context = context;
    }
}
