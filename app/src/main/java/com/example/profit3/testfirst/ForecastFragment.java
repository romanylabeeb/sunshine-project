package com.example.profit3.testfirst;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.profit3.testfirst.connection.AppConnection;
import com.example.profit3.testfirst.parser.WeatherDataParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Profit3 on 21/11/2015.
 */
public  class ForecastFragment extends Fragment {

    private ArrayAdapter<String> forecastAdapter;

    public ForecastFragment() {
    }

    /***
     * init function
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    /**
     * for adding the fragment menu to main menu
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    /***
     * set Fake Data on the menu
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data
        String[] fakeData = {
                "Sun 06/23 -cloudy ,17/10",
                "Mon 06/24 -Rainy ,17/10",
                "Tue 06/25 -Rainy ,17/10",
                "Wen 06/26 -Sunny ,20/10",
                "Thu 06/27 -Sunny ,20/10",
                "Fri 06/28 -Sunny ,21/10",
                "Sun 06/29 -Sunny ,22/10",
                "Sat 06/30 -Sunny ,20/10",
                "Sun 06/31 -Sunny ,20/10"

        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(fakeData));

        forecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_forecast,
                        R.id.list_item_forecast_textview,
                        weekForecast);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  text = forecastAdapter.getItem(position);
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(getActivity(), text, duration).show();
            }
        });

        return rootView;
    }

    /**
     * for select refresh call the action
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            //steps call async class & excute
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class FetchWeatherTask extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
        private AppConnection appConnection;
        @Override
        protected String[] doInBackground(Void... params) {
            this.appConnection=new AppConnection();
            //postalCODE "SMOUHA"=21615
            //Reference http://www.nmisr.com/vb/showthread.php?t=545267
           final String FORECAST_BASE_URL_ONE_WEEK=appConnection.buildRequestURLByParameters("21646","json","metric",7);

         String forecastJsonStr=   this.appConnection.getResponseByUrl(FORECAST_BASE_URL_ONE_WEEK);


           System.out.println("response="+forecastJsonStr);
            try {
                return WeatherDataParser.getWeatherDataFromJson(forecastJsonStr,7);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        /**
         * add result to main activity
         * @param results
         */
        @Override
        protected void onPostExecute(String[] results) {
            if(results!=null){
                forecastAdapter.clear();

                  forecastAdapter.addAll(results);

            }

        }
    }
}
