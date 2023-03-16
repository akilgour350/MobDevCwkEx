package com.adk.kilgour_alastair_s2221119;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ResultQuakeViewHolder extends RecyclerView.ViewHolder {
    TextView quakeDate;
    TextView quakeLocality;
    TextView quakeRegion;
    Button moreInfoBtn;
    View view;

    public ResultQuakeViewHolder(View itemView) {
        super(itemView);
        quakeDate = itemView
                .findViewById(R.id.quakeDate);
        quakeLocality = itemView
                .findViewById(R.id.quakeLocality);
        quakeRegion = itemView
                .findViewById(R.id.quakeRegion);
        moreInfoBtn = itemView.findViewById(R.id.moreInfo);
        view  = itemView;
    }
}
