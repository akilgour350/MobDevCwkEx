package com.adk.kilgour_alastair_s2221119;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        // sets values in Spinner used to select how to search


        return v;
    }

    public void searchForInput(View v) {
        try {
            if (!v.findViewById(R.id.searchInput).equals("")) {
                Spinner spn = v.findViewById(R.id.searchBy);
                if (spn.getSelectedItem().toString().equals("Locality / Region")) {

                } else if (spn.getSelectedItem().toString().equals("Date")) {

                } else {
                    throw new Exception("Invalid search type.");
                }
            } else {
                Snackbar sb = Snackbar.make(v, "Please enter something to search by!", 3000);
                sb.show();
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Snackbar sb = Snackbar.make(v, "Something went wrong...", 3000);
            sb.show();
        }
    }
}