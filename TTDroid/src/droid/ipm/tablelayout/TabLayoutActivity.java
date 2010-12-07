package droid.ipm.tablelayout;

import droid.ipm.tablelayout.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
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
        intent = new Intent().setClass(this, NetworkActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("network").setIndicator("Rede",
                          res.getDrawable(R.drawable.ic_tab_network))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, StationsActivity.class);
        spec = tabHost.newTabSpec("stations").setIndicator("Estações",
                          res.getDrawable(R.drawable.ic_tab_stations))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ScheduleActivity.class);
        spec = tabHost.newTabSpec("schedule").setIndicator("Horários",
                          res.getDrawable(R.drawable.ic_tab_schedule))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ScheduleActivity.class);
        spec = tabHost.newTabSpec("Favorites").setIndicator("Favoritos",
                          res.getDrawable(R.drawable.ic_tab_favorites))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        
    }
    
   
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }
    

    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        switch (item.getItemId()) {
            case R.id.iother:	new XMLExample();
            					break;
            case R.id.iabout: 	intent = new Intent(this, AboutActivity.class);
  	      						startActivityForResult(intent, 0);
            					break;
        }
        return true;
    }
    
}