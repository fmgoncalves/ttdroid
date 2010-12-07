package droid.ipm.tablelayout;

import java.util.Calendar;

import droid.ipm.tablelayout.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StationActivity extends Activity{
	
	private String[] connections;
	private String[] departures;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
//		  setContentView(R.layout.station);
//		  
//		  TextView stationName = (TextView) findViewById(R.id.stationname);
//		  stationName.setText(getIntent().getExtras().getString("Station"));
//
//		  //read information about a given station
//		  if(stationName.getText().equals("Cais do Sodré")){
//			  connections = getResources().getStringArray(R.array.caisdosodre_connections);
//			  departures = getResources().getStringArray(R.array.caisdosodre_departures);
//		  }else if(stationName.getText().equals("Cacilhas")){
//				  connections = getResources().getStringArray(R.array.cacilhas_connections);
//				  departures = getResources().getStringArray(R.array.cacilhas_departures);
//		  }		
//		  
//		  //display connections
//		  ImageView connectionIcon;
//		  for(int i = 0; i < connections.length; i++)
//			  if(connections[i].equals("bus")){
//				  connectionIcon = (ImageView) findViewById(R.id.stationtp1);
//				  connectionIcon.setImageResource(R.drawable.ic_bus);
//			  }else if(connections[i].equals("tram")){
//				  connectionIcon = (ImageView) findViewById(R.id.stationtp2);
//				  connectionIcon.setImageResource(R.drawable.ic_tram);
//			  }else if(connections[i].equals("train")){
//				  connectionIcon = (ImageView) findViewById(R.id.stationtp3);
//				  connectionIcon.setImageResource(R.drawable.ic_train);
//			  }
//
//		  int hours = Calendar.HOUR_OF_DAY;
//		  int minutes = Calendar.MINUTE; 
//		  
//		  //display departures
//		  TextView departureText;
//		  for(int i = 0; i < departures.length; i++){
//			  if(i == 0){
//				  departureText = (TextView) findViewById(R.id.stationdeparture1);
//				  departureText.setText(departures[i]);
//			  }else if (i == 1){
//				  departureText = (TextView) findViewById(R.id.stationdeparture2);
//				  departureText.setText(departures[i]);
//			  }else if (i == 2){
//				  departureText = (TextView) findViewById(R.id.stationdeparture3);
//				  departureText.setText(departures[i]);
//			  }
//		  	
//			  String[] departureConnections = getConnections(departures[i]);
//		  	  for(int j = 0; j < departureConnections.length; j++){
//				  if(departureConnections[j].equals("bus")){
//					  connectionIcon = (ImageView) findViewById(R.id.stationtp1);
//					  connectionIcon.setImageResource(R.drawable.ic_bus);
//				  }else if(departureConnections[j].equals("tram")){
//					  connectionIcon = (ImageView) findViewById(R.id.stationtp2);
//					  connectionIcon.setImageResource(R.drawable.ic_tram);
//				  }else if(departureConnections[j].equals("train")){
//					  connectionIcon = (ImageView) findViewById(R.id.stationtp3);
//					  connectionIcon.setImageResource(R.drawable.ic_train);
//				  }
//		  	  }
//		  
//		  }		
		  
		  //TODO: isto com xml a estrutura, vai ficar mega comprido o código, por causa dos ids sempre a diferir
		  // por outro lado se nao for xml fica mais código aqui mas menos código para todos os elementos repetidos
		  
		  LinearLayout mainLayout = new LinearLayout(this);
		  mainLayout.setOrientation(1);
		  mainLayout.setPadding(10, 5, 0, 0);
		 
		  //Display station name
		  TextView text = new TextView(this);
		  text.setText(getIntent().getExtras().getString("Station"));
		  text.setTextSize(28);
		  mainLayout.addView(text);
		  
		  //read station information  
		  getStationInformation(getIntent().getExtras().getString("Station"));
		  
		  LinearLayout main_transportsLayout = new LinearLayout(this);
		  main_transportsLayout.setOrientation(0);
		  
		  //display station connections
		  ImageView icon;
		  for(int i = 0; i < connections.length; i++){
			  icon = new ImageView(this);
			  if(connections[i].equals("bus"))
				  icon.setImageResource(R.drawable.ic_bus_m);
			  else if(connections[i].equals("tram"))
				  icon.setImageResource(R.drawable.ic_tram_m);
			  else if(connections[i].equals("train"))
				  icon.setImageResource(R.drawable.ic_train_m);
			  icon.setScaleType(ImageView.ScaleType.MATRIX);
			  icon.setPadding(5, 0, 0, 0);
			  main_transportsLayout.addView(icon);
		  } 
		  mainLayout.addView(main_transportsLayout);
		  
		  
		  LinearLayout main_departuresLayout = new LinearLayout(this);
		  main_departuresLayout.setOrientation(1);
		  main_departuresLayout.setPadding(5, 15, 0, 0);
		  
		  text = new TextView(this);
		  text.setText("Partidas");
		  text.setTextSize(21);
		  main_departuresLayout.addView(text);
		  
		  int hours = Calendar.HOUR_OF_DAY;
		  int minutes = Calendar.MINUTE; 
		  
		  //display station departures
		  for(int i = 0; i < departures.length; i++){
			  LinearLayout main_departure_departuresLayout = new LinearLayout(this);
			  main_departure_departuresLayout.setOrientation(0);
			  main_departure_departuresLayout.setPadding(5, 5, 0, 0);
			  
			  text = new TextView(this);
			  text.setText(departures[i]);
			  text.setTextSize(18);
			  main_departure_departuresLayout.addView(text);
			
			  displayConnections(getConnections(departures[i]), main_departure_departuresLayout);
			  
			  text = new TextView(this);
			  text.setText(getPrice(departures[i]));
			  text.setTextSize(15);
			  text.setPadding(5, 0, 0, 0);
			  main_departure_departuresLayout.addView(text);
			  
			  main_departuresLayout.addView(main_departure_departuresLayout);
			  
			  text = new TextView(this);
			  text.setText("00:01");
			  text.setTextSize(15);
			  text.setPadding(15, 0, 0, 0);
			  main_departuresLayout.addView(text);
			  
			  text = new TextView(this);
			  text.setText("01:20");
			  text.setTextSize(12);
			  text.setPadding(15, 0, 0, 0);
			  main_departuresLayout.addView(text);
		  }
		  
		  mainLayout.addView(main_departuresLayout);
		  
		  text = new TextView(this);
		  text.setText("Ir para...");
		  text.setTextSize(21);
		  text.setPadding(5, 15, 0, 0);
		  		  
		  text.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View view) {
			    	Intent intent = new Intent(StationActivity.this, LocationActivity.class);
			    	intent.putExtra("Station",getIntent().getExtras().getString("Station") );
			        startActivity(intent);
			    }
		  });
		  mainLayout.addView(text);
		  
		  setContentView(mainLayout);
	}
	
	/**
	 * Initialize class variables with station information
	 */
	void getStationInformation(CharSequence station){
		  if(station.equals("Cais do Sodré")){
			  connections = getResources().getStringArray(R.array.caisdosodre_connections);
			  departures = getResources().getStringArray(R.array.caisdosodre_departures);
		  }else if(station.equals("Cacilhas")){
				  connections = getResources().getStringArray(R.array.cacilhas_connections);
				  departures = getResources().getStringArray(R.array.cacilhas_departures);
		  }		
	}
	
	/**
	 * Return connections for the given station
	 */
	String[] getConnections(String station){
		  if(station.equals("Cais do Sodré"))
			  return getResources().getStringArray(R.array.caisdosodre_connections);
		  else if(station.equals("Cacilhas"))
			  return getResources().getStringArray(R.array.cacilhas_connections);
		  else if(station.equals("Seixal"))
			  return getResources().getStringArray(R.array.seixal_connections);
		  else if(station.equals("Montijo"))
			  return getResources().getStringArray(R.array.montijo_connections);	
		return null;
	}
	
	/**
	* Return the price for the given station
	*/
	String getPrice(String station){
		if(station.equals("Cais do Sodré")){
			if(getIntent().getExtras().getString("Station").equals("Cacilhas"))
				return getResources().getString(R.string.price_cacilhas_caisdosodre);
			else if(getIntent().getExtras().getString("Station").equals("Montijo"))
				return getResources().getString(R.string.price_montijo_caisdosodre);
			else if(getIntent().getExtras().getString("Station").equals("Seixal"))
				return getResources().getString(R.string.price_seixal_caisdosodre);
		}else if(station.equals("Cacilhas")){
			return getResources().getString(R.string.price_cacilhas_caisdosodre);
		}else if(station.equals("Seixal")){
			return getResources().getString(R.string.price_montijo_caisdosodre);
		}else if(station.equals("Montijo")){
			return getResources().getString(R.string.price_seixal_caisdosodre);	
		}
		return null;
	}
	
	/**
	* Display departure connections
	*/
	void displayConnections(String[] connections, LinearLayout layout){
		  ImageView icon;
		  for(int i = 0; i < connections.length; i++){
			  icon = new ImageView(this);
			  if(connections[i].equals("bus"))
				  icon.setImageResource(R.drawable.ic_bus_l);
			  else if(connections[i].equals("tram"))
				  icon.setImageResource(R.drawable.ic_tram_l);
			  else if(connections[i].equals("train"))
				  icon.setImageResource(R.drawable.ic_train_l);
			  icon.setScaleType(ImageView.ScaleType.MATRIX);
			  icon.setPadding(5, 0, 0, 0);
			  layout.addView(icon);
		  } 
	}
	
}
