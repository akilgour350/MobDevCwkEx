/*
Name: Alastair Kilgour
SN: S2221119
Programme: Computer
*/
package com.adk.kilgour_alastair_s2221119;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<Earthquake> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        loadData(); // calls method to get all the data for the app
        initInterface();


    }

    public void searchForInput(View v) {
        ArrayList<ResultQuake> results = new ArrayList<>();
        EditText input = findViewById(R.id.searchInput);
        String toSearch = input.getText().toString().toLowerCase();

        if (toSearch != "" && toSearch != null) {
            Spinner spn = findViewById(R.id.searchBy);
            String searchBy = spn.getSelectedItem().toString();

            switch (searchBy) {
                case "Locality / Region":
                    for (int i = 0; i < earthquakes.size(); i++) {
                        if (toSearch.contains(earthquakes.get(i).getLocality().toLowerCase()) && toSearch.contains(earthquakes.get(i).getRegion().toLowerCase())) {
                            ResultQuake rq = new ResultQuake(earthquakes.get(i), Resemblance.Exact);
                            results.add(rq);
                        } else if (toSearch.contains(earthquakes.get(i).getLocality().toLowerCase()) || toSearch.contains(earthquakes.get(i).getRegion().toLowerCase())) {
                            ResultQuake rq = new ResultQuake(earthquakes.get(i), Resemblance.Near);
                            results.add(rq);
                        }
                    }

                    break;

                case "Date":
                    System.out.println("Searching by date");
                    for (int i = 0; i < earthquakes.size(); i++) {
                        if (toSearch.contains(earthquakes.get(i).getDate())) {
                            ResultQuake rq = new ResultQuake(earthquakes.get(i), Resemblance.Exact);
                            results.add(rq);
                        }
                    }
                    break;

                default:

            }

            if (results.size() != 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    results.sort(Comparator.comparing(ResultQuake::getResemblance));
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("resultQuakes", results);
                SearchFragment sFrag = new SearchFragment();
                sFrag.setArguments(bundle);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.searchPlaceholder, sFrag);
                ft.commit();
            } else {
                Snackbar sb = Snackbar.make(findViewById(R.id.actMainLayout), "No results!", 3000);
                sb.show();
            }


        } else {
            Snackbar sb = Snackbar.make(findViewById(R.id.actMainLayout), "Enter search parameters!", 3000);
            sb.show();
        }
    }

    // FOR SETTING UP THE MAIN ACTIVITY GUI
    private void initInterface() {
        // sets layout and items in searchBy spinner
        Spinner spn = findViewById(R.id.searchBy);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getApplicationContext(), R.array.search_by_array, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        EditText et = findViewById(R.id.searchInput);
        et.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                searchForInput(et);
            }
            return false;
        });
    }

    // FOR GETTING DATA FROM SOURCE
    public void loadData()
    {
        final String URL_SOURCE ="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
        // Run network access on a separate thread;
        new Thread(new Task(URL_SOURCE)).start();
    }
    private class Task implements Runnable
    {
        private String url;
        public Task(String myUrl)
        {
            url = myUrl;
        }

        @Override
        public void run()
        {
            URL myUrl;
            URLConnection yc;
            BufferedReader in;
            String inputLine;
            String result = "";
            earthquakes = new ArrayList<>();

            try
            {
                myUrl = new URL(url);
                yc = myUrl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    System.out.println(inputLine);

                }
                in.close();

                if (!result.equals("")) {
                    // PullParser Setup
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput( new StringReader( result ) );

                    int eventType = xpp.getEventType(); // initialise eventType variable to track which type of event the Parser is on
                    Earthquake quake = new Earthquake(); // creates Earthquake object to be populated
                    boolean building = false; // creates boolean to store whether an Earthquake object is being built

                    while (eventType != XmlPullParser.END_DOCUMENT) { // checks to ensure the Parser hasn't reach the end of the document

                        if(eventType == XmlPullParser.START_TAG) // checks for an XML start tag (eg <myTag>)
                        {
                            if (xpp.getName().equals("item")) {
                                quake = new Earthquake(); // initialises earthquake object
                                building = true; // sets that an Earthquake object is being built

                            } else if (xpp.getName().equals("title") && building) {
                                xpp.next(); // moves to next event (will be TEXT)
                                quake.setLocationRegion(xpp.getText()); // stores title of the earthquake

                            } else if (xpp.getName().equals("description") && building) {
                                xpp.next(); // moves to next event (will be TEXT)
                                quake.setDepthMagnitude((xpp.getText())); // stores description of the earthquake

                            } else if (xpp.getName().equals("pubDate") && building) {
                                xpp.next(); // moves to next event (will be TEXT)
                                quake.setDate(xpp.getText()); // stores date of the earthquake

                            } else if (xpp.getName().equals("lat") && building) {
                                xpp.next(); // moves to next event (will be TEXT)
                                quake.setLatitude(xpp.getText()); // stores latitude of the earthquake

                            } else if (xpp.getName().equals("long") && building) {
                                xpp.next(); // moves to next event (will be TEXT)
                                quake.setLongitude(xpp.getText()); // stores longitude of the earthquake

                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            if (xpp.getName().equals("item") && building) {
                                float[] tempDist = new float[3];
                                android.location.Location.distanceBetween(Double.parseDouble(quake.getLatitude()), Double.parseDouble(quake.getLongitude()), 55.8642, 4.2518, tempDist);
                                quake.setDistance((int)tempDist[0]);
                                earthquakes.add(quake); // adds complete Earthquake object to ArrayList
                                building = false; // sets that an Earthquake object is NOT being built
                                System.out.println("Created earthquake: ");
                                System.out.println("Location: " + quake.getLocality() + "," + quake.getRegion());
                                System.out.println("Date: " + quake.getDate());
                            }
                        }
                        eventType = xpp.next(); // moves to next event
                    }
                    System.out.println("Data parsed");
                } else {
                    throw new Exception("Result was empty.");
                }
            }
            catch (Exception ex)
            {
                System.out.println("Error: " + ex.getMessage());
                Snackbar sb = Snackbar.make(findViewById(R.id.actMainLayout), "Something went wrong...", 3000);
                sb.show();
            }
        }

    }
}