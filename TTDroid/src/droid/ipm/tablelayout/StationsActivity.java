package droid.ipm.tablelayout;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import droid.ipm.util.Pair;
import droid.ipm.util.XMLExample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class StationsActivity extends Activity {

	private List<Pair> stationsInfo;
	private boolean withDistance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = this.getLayoutInflater();

		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(1);

		String[] stations = getResources().getStringArray(R.array.stations_array);
		
		//Create Information Array
		stationsInfo = new LinkedList<Pair>();
		for (int i = 0; i < stations.length; i++)
			stationsInfo.add(new Pair(stations[i],null));
		
		//Check network connection
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(l!=null)
			withDistance = true;
		else
			withDistance = false;
		//XMLExample aux = new XMLExample(38.688137,-9.147667, getResources().getString(R.string.location_caisdosodre));
		//Toast.makeText(this, stations[1]+" "+aux.gimme(), Toast.LENGTH_LONG).show();
		
		//Threat distances
		if(withDistance){
			XMLExample aux;
			double lat, longi;
			lat=l.getLatitude();
			longi=l.getLongitude();
			//Toast.makeText(this, lat+"and"+longi, Toast.LENGTH_LONG).show();
			for(int i = 0; i < stationsInfo.size(); i++){
				aux = new XMLExample(lat,longi, getLocation(stationsInfo.get(i).fst));
				stationsInfo.get(i).setSecond(aux.getDistance());
			}
			Collections.sort(stationsInfo);
		  }
		
		for (int i = 0; i < stationsInfo.size(); i++) {
			final String name = stationsInfo.get(i).getFirst();
			View v = inflater.inflate(R.layout.stations_item, null);
			((TextView) v.findViewById(R.id.name)).setText(name);
			if(withDistance)
				((TextView) v.findViewById(R.id.distance)).setText(String.valueOf(stationsInfo.get(i).getSecond())+" km");
			
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
			    	Intent intent = new Intent(StationsActivity.this, StationActivity.class);
		    	      intent.putExtra("station", name);
		              startActivity(intent);
				}
			});
			
			v.setPadding(0, 10, 0, 10);
			ll.addView(v);
		}
		setContentView(ll);
	}
	
	String getLocation(String station){
		  if(station.equals("Cais do SodrŽ"))
			  return getResources().getString(R.string.location_caisdosodre);
		  else if(station.equals("Cacilhas"))
			  return getResources().getString(R.string.location_cacilhas);
		  else if(station.equals("Seixal"))
			  return getResources().getString(R.string.location_seixal);
		  else if(station.equals("Montijo"))
			  return getResources().getString(R.string.location_montijo);
		  else if(station.equals("Porto Brand‹o"))
			  return getResources().getString(R.string.location_portobrandao);
		  else if(station.equals("Trafaria"))
			  return getResources().getString(R.string.location_trafaria);
		  else if(station.equals("Terreiro do Pao"))
			  return getResources().getString(R.string.location_terreirodopaco);
		  else if(station.equals("Barreiro"))
			  return getResources().getString(R.string.location_barreiro);
		  else if(station.equals("BelŽm"))
			  return getResources().getString(R.string.location_belem);
		return null;
	}

}
