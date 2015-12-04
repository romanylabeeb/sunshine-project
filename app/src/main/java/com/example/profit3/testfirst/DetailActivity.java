package com.example.profit3.testfirst;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.profit3.testfirst.settings.SettingsActivity;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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
            settingIntent.putExtra(Intent.EXTRA_TEXT,"settings");
            // downloadIntent.setData(Uri.parse(fileUrl));
            startActivity(settingIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

           Intent intent = getActivity().getIntent();
            //System.out.println("message===="+message);
         //   R.layout.fragment_detail
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(null!=intent){
                TextView textView=(TextView) rootView.findViewById(R.id.detailsV);
                textView.setText(intent.getStringExtra(intent.EXTRA_TEXT));
            }
            return rootView;
        }
    }
}