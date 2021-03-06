package droid.ipm.tablelayout;

import droid.ipm.tablelayout.R;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class TabLayoutActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ScheduleActivity.class);
        spec = tabHost.newTabSpec("schedule").setIndicator(getResources().getString(R.string.tab_schedule),
                          res.getDrawable(R.drawable.ic_tab_schedule))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, StationsActivity.class);
        spec = tabHost.newTabSpec("stations").setIndicator(getResources().getString(R.string.tab_stations),
                          res.getDrawable(R.drawable.ic_tab_stations))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, FavoritesActivity.class);
        spec = tabHost.newTabSpec("favorites").setIndicator(getResources().getString(R.string.tab_favorites),
                          res.getDrawable(R.drawable.ic_tab_favorites))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        
    }
    
   
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.generalmenu, menu);
        return true;
    }
    

    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        switch (item.getItemId()) {
            case R.id.iwebsite: intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.transtejo.pt/"));
            					startActivity(intent);
            					break;
            case R.id.iabout: 	intent = new Intent(this, AboutActivity.class);
  	      						startActivityForResult(intent, 0);
            					break;
        }
        return true;
    }
    
}