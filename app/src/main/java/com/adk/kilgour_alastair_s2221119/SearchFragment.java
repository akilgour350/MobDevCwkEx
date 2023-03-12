package com.adk.kilgour_alastair_s2221119;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    ArrayList<ResultQuake> quakes;
    ResultQuakeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        quakes = new ArrayList<>();


        if (getArguments() != null) {
            Bundle extras = getArguments();
            quakes = extras.getParcelableArrayList("resultQuakes");

            adapter = new ResultQuakeAdapter(quakes, getContext());
            RecyclerView rv = v.findViewById(R.id.rvSearchResults);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            System.out.println("null quakes passed");
        }


        return v;
    }
}