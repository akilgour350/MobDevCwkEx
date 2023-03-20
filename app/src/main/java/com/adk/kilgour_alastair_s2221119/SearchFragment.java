/*
Name: Alastair Kilgour
SN: S2221119
Programme: Computer
*/

package com.adk.kilgour_alastair_s2221119;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        } else {
            System.out.println("null quakes passed");
        }
        return v;
    }
}