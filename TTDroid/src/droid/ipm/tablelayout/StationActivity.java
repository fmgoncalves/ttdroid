	package droid.ipm.tablelayout;

import java.util.Calendar;

import droid.ipm.tablelayout.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.LayoutInflater;

public class StationActivity extends Activity{
	
	private String station;
	private String[] connections;
	private String[] departures;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);

		  station = getIntent().getExtras().getString("station");
		  
		  //read station information  
		  getStationInformation(station);
		  
		  LinearLayout mainLayout = new LinearLayout(this);
		  mainLayout.setOrientation(1);
		  mainLayout.setPadding(10, 5, 0, 0);
		  //mainLayout.setBackgroundColor(Color.rgb(79,79,79));

		  LinearLayout main_transportsLayout = new LinearLayout(this);
		  main_transportsLayout.setOrientation(0);
		  //main_transportsLayout.setBackgroundColor(Color.rgb(206, 111, 31));
		  
		  //Display station name
		  TextView text = new TextView(this);
		  text.setText(station);
		  text.setTextSize(28);
		  text.setPadding(0, 0, 5, 0);
		  main_transportsLayout.addView(text);
		  
		  //Display station connections
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
			  icon.setPadding(5, 8, 0, 0);
			  main_transportsLayout.addView(icon);
		  } 
		  mainLayout.addView(main_transportsLayout);

		  //Display next departure
		  LinearLayout main_departuresLayout = new LinearLayout(this);
		  main_departuresLayout.setOrientation(1);
		  
		  LayoutInflater inflater = this.getLayoutInflater();
		  for (int i = 0; i < departures.length; i++) {
			  View v = inflater.inflate(R.layout.station_destiny, null);
			  ((TextView) v.findViewById(R.id.name)).setText(departures[i]);
			  
			  String[] connections = getConnections(departures[i]);
			  for(int j = 0; j < connections.length; j++){
				  int target = 0;
				  if(j == 0) target = R.id.transport1;
				  else if(j == 1) target = R.id.transport2;
				  else if(j == 2) target = R.id.transport3;
				  
				  if(connections[j].equals("bus")) ((ImageView) v.findViewById(target)).setImageResource(R.drawable.ic_bus_m);
				  else if(connections[j].equals("tram")) ((ImageView) v.findViewById(target)).setImageResource(R.drawable.ic_tram_m);
				  else if(connections[j].equals("train")) ((ImageView) v.findViewById(target)).setImageResource(R.drawable.ic_train_m);
			  } 
			  ((TextView) v.findViewById(R.id.price)).setText(getPrice(departures[i]));	
			  ((TextView) v.findViewById(R.id.time)).setText(getNextDeparture(station, departures[i]));
			  
			  final int index = i;
			  v.setOnClickListener(new View.OnClickListener() {
				    public void onClick(View view) {
				    	Intent intent = new Intent(StationActivity.this, DisplayScheduleActivity.class);
			    	      intent.putExtra("From", station);
			    	      intent.putExtra("To", departures[index]); 
			              startActivity(intent);
				    }
			  });
			  
			  main_departuresLayout.addView(v);
		  }
		  mainLayout.addView(main_departuresLayout);
		  
		  //Display map option
		  LinearLayout main_mapLayout = new LinearLayout(this);
		  main_mapLayout.setOrientation(0);
		  main_mapLayout.setPadding(5, 15, 0, 0);
		  
		  icon = new ImageView(this);
		  icon.setImageResource(R.drawable.ic_maps);
		  icon.setScaleType(ImageView.ScaleType.MATRIX);
		  icon.setPadding(0, 20, 0, 0);
		  main_mapLayout.addView(icon);
		  
//		  text = new TextView(this);
//		  text.setText("Mapa");
//		  text.setTextSize(21);
//		  text.setPadding(5, 0, 0, 0);
//		  main_mapLayout.addView(text);

		  icon.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View view) {
//			    	Intent intent = new Intent(StationActivity.this, LocationActivity.class);
//			    	intent.putExtra("Station",getIntent().getExtras().getString("Station") );
//			        startActivity(intent);
			        String geoUriString = " ";
			        if(getIntent().getExtras().getString("Station").equals("Cais do Sodr�"))
			        	geoUriString = "geo:0,0?q=38.705083,-9.145429&z=10"; 
			        else if(getIntent().getExtras().getString("Station").equals("Cacilhas"))
			        	geoUriString = "geo:0,0?q=38.688137,-9.147667&z=10"; 
			        Uri geoUri = Uri.parse(geoUriString);  
			        startActivity(new Intent(Intent.ACTION_VIEW, geoUri));  
			    }
		  });
		  mainLayout.addView(main_mapLayout);
		  ScrollView sv = new ScrollView(this);
		  sv.addView(mainLayout);
		  setContentView(sv);
	}
	
	/**
	 * Initialize class variables with station information
	 */
	void getStationInformation(CharSequence station){
		  if(station.equals("Cais do Sodr�")){
			  connections = getResources().getStringArray(R.array.caisdosodre_connections);
			  departures = getResources().getStringArray(R.array.caisdosodre_departures);
		  }else if(station.equals("Cacilhas")){
				  connections = getResources().getStringArray(R.array.cacilhas_connections);
				  departures = getResources().getStringArray(R.array.cacilhas_departures);
		  }else if(station.equals("Porto Brand�o")){
			  connections = getResources().getStringArray(R.array.portobrandao_connections);
			  departures = getResources().getStringArray(R.array.portobrandao_departures);
		  }else if(station.equals("Trafaria")){
			  connections = getResources().getStringArray(R.array.trafaria_connections);
			  departures = getResources().getStringArray(R.array.trafaria_departures);
		  }else if(station.equals("Bel�m")){
			  connections = getResources().getStringArray(R.array.belem_connections);
			  departures = getResources().getStringArray(R.array.belem_departures);
		  }else if(station.equals("Terreiro do Pa�o")){
			  connections = getResources().getStringArray(R.array.terreirodopaco_connections);
			  departures = getResources().getStringArray(R.array.terreirodopaco_departures);
		  }else if(station.equals("Barreiro")){
			  connections = getResources().getStringArray(R.array.barreiro_connections);
			  departures = getResources().getStringArray(R.array.barreiro_departures);
		  }else if(station.equals("Seixal")){
			  connections = getResources().getStringArray(R.array.seixal_connections);
			  departures = getResources().getStringArray(R.array.seixal_departures);
		  }else if(station.equals("Montijo")){
			  connections = getResources().getStringArray(R.array.montijo_connections);
			  departures = getResources().getStringArray(R.array.montijo_departures);
		  }						
	}
	
	String getNextDeparture(String origin, String destiny){
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE); 
		
		String[] schedule = null;
		if(origin.equals("Cais do Sodr�") && destiny.equals("Cacilhas")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_uteis);
			else
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_sabados_domingos_feriados);
		}else if(origin.equals("Cais do Sodr�") && destiny.equals("Montijo")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_uteis);
			else if(day == 1)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_sabados);
			else
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_domingos_feriados);
		}else if(origin.equals("Cais do Sodr�") && destiny.equals("Seixal")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_sabados);
			else
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_domingos_feriados);
		}else if(origin.equals("Cacilhas") && destiny.equals("Cais do Sodr�")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_uteis);
			else
				schedule = getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_sabados_domingos_feriados);
		}else if(origin.equals("Seixal") && destiny.equals("Cais do Sodr�")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_sabados);
		}else if(origin.equals("Montijo") && destiny.equals("Cais do Sodr�")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_sabados);
		}else if(origin.equals("Trafaria") && destiny.equals("Bel�m")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_trafaria_belem_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_trafaria_belem_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_trafaria_belem_sabados);
		}else if(origin.equals("Porto Brand�o") && destiny.equals("Bel�m")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_belem_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_porto_brandao_belem_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_belem_sabados);
		}else if(origin.equals("Bel�m") && destiny.equals("Trafaria")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_belem_trafaria_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_belem_trafaria_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_belem_trafaria_sabados);
		}else if(origin.equals("Porto Brand�o") && destiny.equals("Trafaria")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_sabados);
		}else if(origin.equals("Barreiro") && destiny.equals("Terreiro do Pa�o")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_barreiro_terreiro_do_paco_uteis);
			else
				schedule = getResources().getStringArray(R.array.schedule_barreiro_terreiro_do_paco_sabados_domingos_feriados);
		}else if(origin.equals("Terreiro do Pa�o") && destiny.equals("Barreiro")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_terreiro_do_paco_barreiro_uteis);
			else
				schedule = getResources().getStringArray(R.array.schedule_terreiro_do_paco_barreiro_sabados_domingos_feriados);
		}
			
		for(int j = 0; j < schedule.length; j++){
			String time = schedule[j];
			if(time.contains("F") || time.contains("a)") || time.contains("A") || time.contains("B") || time.contains("C"))
				time = time.split(" ")[0];
			String[] sTime = time.split(":");
			int sHours = Integer.parseInt(sTime[0]);
			int sMinutes = Integer.parseInt(sTime[1]);
			//System.out.println("1 if "+sHours+" == "+hours);
			if(sHours == hours){
				//System.out.println("2 if "+sMinutes+" > "+minutes);
				if(sMinutes > minutes)
					return schedule[j];
			}else if(sHours > hours){
				return schedule[j];
			}
		}
		return schedule[0];
	}
	
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.generalmenu, menu);
        return true;
    }
	
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iother:	break;
            case R.id.iabout: 	startActivityForResult(new Intent(this, AboutActivity.class), 0);
            					break;
        }
        return true;
    }
	
	/**
	 * Return connections for the given station
	 */
	String[] getConnections(String station){
		  if(station.equals("Cais do Sodr�"))
			  return getResources().getStringArray(R.array.caisdosodre_connections);
		  else if(station.equals("Cacilhas"))
			  return getResources().getStringArray(R.array.cacilhas_connections);
		  else if(station.equals("Seixal"))
			  return getResources().getStringArray(R.array.seixal_connections);
		  else if(station.equals("Montijo"))
			  return getResources().getStringArray(R.array.montijo_connections);
		  else if(station.equals("Porto Brand�o"))
			  return getResources().getStringArray(R.array.portobrandao_connections);
		  else if(station.equals("Trafaria"))
			  return getResources().getStringArray(R.array.trafaria_connections);
		  else if(station.equals("Terreiro do Pa�o"))
			  return getResources().getStringArray(R.array.terreirodopaco_connections);
		  else if(station.equals("Barreiro"))
			  return getResources().getStringArray(R.array.barreiro_connections);
		  else if(station.equals("Bel�m"))
			  return getResources().getStringArray(R.array.belem_connections);
		return null;
	}
	
	/**
	* Return the price for the given station
	*/
	String getPrice(String station){
		if(station.equals("Cais do Sodr�")){
			if(this.station.equals("Cacilhas"))
				return getResources().getString(R.string.price_cacilhas_caisdosodre);
			else if(this.station.equals("Montijo"))
				return getResources().getString(R.string.price_montijo_caisdosodre);
			else if(this.station.equals("Seixal"))
				return getResources().getString(R.string.price_seixal_caisdosodre);
		}else if(station.equals("Cacilhas")){
			return getResources().getString(R.string.price_cacilhas_caisdosodre);
		}else if(station.equals("Seixal")){
			return getResources().getString(R.string.price_montijo_caisdosodre);
		}else if(station.equals("Montijo")){
			return getResources().getString(R.string.price_seixal_caisdosodre);	
		}else if(station.equals("Terreiro do Pa�o")){
			return getResources().getString(R.string.price_terreirodopaco_barreiro);	
		}else if(station.equals("Barreiro")){
			return getResources().getString(R.string.price_terreirodopaco_barreiro);	
		}else if(station.equals("Porto Brand�o")){
			return getResources().getString(R.string.price_trafaria_belem);	
		}else if(station.equals("Trafaria")){
			return getResources().getString(R.string.price_trafaria_belem);	
		}else if(station.equals("Bel�m")){
			return getResources().getString(R.string.price_trafaria_belem);	
		}
		return null;
	}
	
	/**
	* Display departure connections
	*/
	void displayConnections(String[] connections, LinearLayout layout){
		System.out.println("ENTROU");
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
			  icon.setPadding(5, 6, 0, 0);
			  layout.addView(icon);
		  } 
	}
	
}
