package com.example.profit3.testfirst;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
       // getMenuInflater().inflate(R.menu.share, menu);

        // Return true to display menu

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
        private ShareActionProvider mShareActionProvider;
        private String forecastStr;
        public PlaceholderFragment() {
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          //  super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.share, menu);
            MenuItem menuItem=menu.findItem(R.id.menu_item_share);
            // Get the provider and hold onto it to set/change the share intent.
             mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            // Attach an intent to this ShareActionProvider.  You can update this at any time,
            // like when the user selects a new piece of data they might like to share.
            if (mShareActionProvider != null ) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
               // Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }

        public Intent createShareForecastIntent(){
    Intent shareIntent=new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,forecastStr+"#forwcast");

            return  shareIntent;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

           Intent intent = getActivity().getIntent();
            //System.out.println("message===="+message);
         //   R.layout.fragment_detail
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(null!=intent){
                if(intent.hasExtra(Intent.EXTRA_TEXT)){
                 forecastStr=intent.getStringExtra(Intent.EXTRA_TEXT);
                    TextView textView=(TextView) rootView.findViewById(R.id.detailsV);
                    textView.setText(intent.getStringExtra(intent.EXTRA_TEXT));
                }
             }
            return rootView;
        }

    }
}