package com.example.profit3.sunshine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.profit3.sunshine.settings.SettingsActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
         //getMenuInflater().inflate(R.menu.forecastfragment,menu);
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//steps call async class & excute
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            settingIntent.putExtra(Intent.EXTRA_TEXT, "settings");
            // downloadIntent.setData(Uri.parse(fileUrl));
            startActivity(settingIntent);
            return true;
        }
        if (id == R.id.action_map) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
            Uri geoLoc = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", location).build();
            Intent geoIntent = new Intent(Intent.ACTION_VIEW);
            geoIntent.setData(geoLoc);
            if (null != geoIntent.resolveActivity(getPackageManager())) {
                startActivity(geoIntent);
            } else {
                Log.d("error", "couldn't call " + location);
            }

        }
        return super.onOptionsItemSelected(item);
    }


}