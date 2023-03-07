package com.adk.kilgour_alastair_s2221119;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ResultQuakeViewHolder extends RecyclerView.ViewHolder {
    TextView quakeDate;
    TextView quakeLocality;
    TextView quakeRegion;
    View view;

    public ResultQuakeViewHolder(View itemView) {
        super(itemView);
        quakeDate
                = (TextView)itemView
                .findViewById(R.id.quakeDate);
        quakeLocality
                = (TextView)itemView
                .findViewById(R.id.quakeLocality);
        quakeRegion
                = (TextView)itemView
                .findViewById(R.id.quakeRegion);
        view  = itemView;
    }
}
