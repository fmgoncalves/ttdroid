package droid.ipm.tablelayout;

import droid.ipm.tablelayout.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

public class StationsActivity extends ListActivity {
	
	private String[] stations;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  stations = getResources().getStringArray(R.array.stations_array);
	  
	  this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, stations));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	      Intent intent = new Intent(StationsActivity.this, StationActivity.class);
	      intent.putExtra("Station", parent.getItemAtPosition(position).toString());
          startActivity(intent);
	    }
	  });
	  
	  double lat, longi;
	  LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	  Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	  if(l!=null){
		  lat=l.getLatitude();
		  longi=l.getLongitude();
		  Toast.makeText(this, lat+"and"+longi, Toast.LENGTH_LONG);
	  }
	}
	
}
