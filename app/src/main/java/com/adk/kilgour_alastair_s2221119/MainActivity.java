
/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 _________________
// Student ID           _________________
// Programme of Study   _________________
//

// Update the package name to include your Student Identifier
package com.adk.kilgour_alastair_s2221119;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private String result;
    private ArrayList<Earthquake> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
    }



    // FOR GETTING DATA FROM SOURCE
    public void loadData()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();

        try {
            if (result != null && result != "") {
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
                        if (xpp.getName().equals("lastBuildDate")) // allows app to display the date the data is from
                        {
                            // sets a TextView to display the date the data was published
                            TextView txt = findViewById(R.id.salutation);
                            xpp.next(); // moves to next event (will be TEXT)
                            txt.setText("Last update: " + xpp.getText());

                        } else if (xpp.getName().equals("item")) {
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
                        if (xpp.getName().equals("item")) {
                            float[] tempDist = new float[3];
                            android.location.Location.distanceBetween(Double.parseDouble(quake.getLatitude()), Double.parseDouble(quake.getLongitude()), 55.8642, 4.2518, tempDist);
                            quake.setDistance((int)tempDist[0]);
                            earthquakes.add(quake); // adds complete Earthquake object to ArrayList
                            building = false; // sets that an Earthquake object is NOT being built
                        }
                    }
                    eventType = xpp.next(); // moves to next event
                }

                System.out.println("All earthquakes read in.");

            } else {
                throw new Exception("Result was empty.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private class Task implements Runnable
    {
        private String url;
        public Task(String aurl)
        {
            url = aurl;
        }

        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            try
            {
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    System.out.println(inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                System.out.println(ae.getMessage());
            }
        }

    }
}