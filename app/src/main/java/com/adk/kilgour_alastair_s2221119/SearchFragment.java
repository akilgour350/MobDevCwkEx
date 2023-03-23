/*
Name: Alastair Kilgour
SN: S2221119
Programme: Computer
*/

package com.adk.kilgour_alastair_s2221119;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    ArrayList<ResultQuake> quakes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        quakes = new ArrayList<>();

        if (getArguments() != null) {
            Bundle extras = getArguments();
            quakes = extras.getParcelableArrayList("resultQuakes");

            ArrayAdapter<ResultQuake> adapter = new ArrayAdapter<>(getContext(), R.layout.resultquake_card, quakes);
            ListView quakeList = v.findViewById(R.id.search_list);
            quakeList.setAdapter(adapter);

            quakeList.setOnItemClickListener((adapterView, view, i, l) -> {
                ResultQuake rq = adapter.getItem(i);
                Intent search = new Intent(getContext(), MoreInfo.class);
                search.putExtra("selectedQuake", rq.getQuake());
                startActivity(search);
            });

            for (int i = 0; i < quakes.size(); i++) {
                View child = quakeList.getChildAt(i);
                switch (Integer.parseInt(quakes.get(i).getQuake().getMagnitude().substring(0, 1))) {
                    case 0:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#00be36"));
                        break;

                    case 1:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#4daf00"));
                        break;

                    case 2:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#699f00"));
                        break;

                    case 3:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#7c8e00"));
                        break;

                    case 4:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#8a7c00"));
                        break;

                    case 5:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#946a00"));
                        break;

                    case 6:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#9b5700"));
                        break;

                    case 7:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#9e4200"));
                        break;

                    case 8:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#9e2900"));
                        break;

                    case 9:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#9b0000"));
                        break;

                    default:
                        child.findViewById(R.id.quake_label).setBackgroundColor(Color.parseColor("#333333"));
                }
            }
        } else {
            System.out.println("null quakes passed");
        }
        return v;
    }
}