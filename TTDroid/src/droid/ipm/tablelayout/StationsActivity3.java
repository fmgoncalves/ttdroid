package droid.ipm.tablelayout;

import droid.ipm.tablelayout.R;
import droid.ipm.util.XMLExample;
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

import java.lang.Integer;



public class StationsActivity3 extends ListActivity {
	
	private String[] stations;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  stations = getResources().getStringArray(R.array.stations_array);
	  
	  LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	  Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	  XMLExample aux = new XMLExample(38.688137,-9.147667, getResources().getString(R.string.location_caisdosodre));
	  //Toast.makeText(this, stations[1]+" "+aux.gimme(), Toast.LENGTH_LONG).show();
	  if(l!=null){
		  double lat, longi;
		  lat=l.getLatitude();
		  longi=l.getLongitude();
		  //Toast.makeText(this, lat+"and"+longi, Toast.LENGTH_LONG).show();
		  for(int i = 0; i < stations.length; i++){
			  aux = new XMLExample(lat,longi, getLocation(stations[i]));
			  //stations[i] = stations[i]+"  "+aux.gimme();
		  }
		  stations = bubbleSort(stations);
	  }
	  
//	  for(int i = 0; i < stations.length-1; i++){
//		  stations[i] = stations[i] +" "+aux.gimme();
//	  }
//	  stations[stations.length-1] = stations[stations.length-1] +"      1 km";
//	  stations = bubbleSort(stations);
	  this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, stations));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	      Intent intent = new Intent(StationsActivity3.this, StationActivity.class);
	      intent.putExtra("Station", parent.getItemAtPosition(position).toString().split("  ")[0]);
          startActivity(intent);
	    }
	  });
	  	
		
	}
	
	String getLocation(String station){
		  if(station.equals("Cais do Sodré"))
			  return getResources().getString(R.string.location_caisdosodre);
		  else if(station.equals("Cacilhas"))
			  return getResources().getString(R.string.location_cacilhas);
		  else if(station.equals("Seixal"))
			  return getResources().getString(R.string.location_seixal);
		  else if(station.equals("Montijo"))
			  return getResources().getString(R.string.location_montijo);
		  else if(station.equals("Porto Brandão"))
			  return getResources().getString(R.string.location_portobrandao);
		  else if(station.equals("Trafaria"))
			  return getResources().getString(R.string.location_trafaria);
		  else if(station.equals("Terreiro do Paço"))
			  return getResources().getString(R.string.location_terreirodopaco);
		  else if(station.equals("Barreiro"))
			  return getResources().getString(R.string.location_barreiro);
		  else if(station.equals("Belém"))
			  return getResources().getString(R.string.location_belem);
		return null;
	}
	
	public static String[] bubbleSort(String[] stations) {
	    int n = stations.length;
	    for (int pass=1; pass < n; pass++) {  // count how many times
	        // This next loop becomes shorter and shorter
	        for (int i=0; i < n-pass; i++) {
	        	String[] ss = stations[i].split(" ");
	        	double km = new Double(ss[ss.length-2]);
	        	ss = stations[i+1].split(" ");
	        	double km2 = new Double(ss[ss.length-2]);
	            if (km > km2) {
	                // exchange elements
	                String temp = stations[i];  stations[i] = stations[i+1];  stations[i+1] = temp;
	            }
	        }
	    }
	    return stations;
	}
	
}
