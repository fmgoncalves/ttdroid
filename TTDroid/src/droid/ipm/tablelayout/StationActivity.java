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

public class StationActivity extends Activity{
	
	private String[] connections;
	private String[] departures;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);

		  //read station information  
		  getStationInformation(getIntent().getExtras().getString("station"));
		  
		  LinearLayout mainLayout = new LinearLayout(this);
		  mainLayout.setOrientation(1);
		  mainLayout.setPadding(10, 5, 0, 0);
		  //mainLayout.setBackgroundColor(Color.rgb(79,79,79));
		  
		  LinearLayout main_transportsLayout = new LinearLayout(this);
		  main_transportsLayout.setOrientation(0);
		  //main_transportsLayout.setBackgroundColor(Color.rgb(206, 111, 31));
		  
		  //Display station name
		  TextView text = new TextView(this);
		  text.setText(getIntent().getExtras().getString("Station"));
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
		  main_departuresLayout.setPadding(5, 10, 0, 0);
		  
//		  text = new TextView(this);
//		  text.setText("Partidas");
//		  text.setTextSize(21);
//		  main_departuresLayout.addView(text);
		  
		  for(int i = 0; i < departures.length; i++){
			  //div para informacao de um destino
			  LinearLayout main_departure_departuresLayout = new LinearLayout(this);
			  main_departure_departuresLayout.setOrientation(0);
			  main_departure_departuresLayout.setPadding(5, 15, 0, 0);
			  
			  //div esquerda
			  LinearLayout main_left_departure_departuresLayout = new LinearLayout(this);
			  main_left_departure_departuresLayout.setOrientation(1);
			  
			  //div para titulo e icons dos transportes
			  LinearLayout main_up_left_departure_departuresLayout = new LinearLayout(this);
			  main_up_left_departure_departuresLayout.setOrientation(0);
			  
			  text = new TextView(this);
			  text.setText(departures[i]);
			  text.setTextSize(20);
			  text.setPadding(0, 0, 5, 0);
			  main_up_left_departure_departuresLayout.addView(text);
			  String[] aux = getConnections(departures[i]);
			  //System.out.println(aux.length);
			  displayConnections(aux, main_up_left_departure_departuresLayout);
			  
			  main_left_departure_departuresLayout.addView(main_up_left_departure_departuresLayout);
			  
			  text = new TextView(this);
			  text.setText("Preço: "+getPrice(departures[i]));
			  text.setTextSize(17);
			  text.setPadding(15, 0, 0, 0);
			  main_left_departure_departuresLayout.addView(text);
			  
			  text = new TextView(this);
			  text.setText("Próximo: "+getNextDeparture(getIntent().getExtras().getString("station"), departures[i]));
			  text.setTextSize(17);
			  text.setPadding(15, 0, 0, 0);
			  main_left_departure_departuresLayout.addView(text);
			  
			  main_departure_departuresLayout.addView(main_left_departure_departuresLayout);
			  
			  //div direita
			  LinearLayout main_right_departure_departuresLayout = new LinearLayout(this);
			  main_right_departure_departuresLayout.setOrientation(1);
			  
			  icon = new ImageView(this);
			  icon.setImageResource(R.drawable.next);
			  icon.setScaleType(ImageView.ScaleType.MATRIX);
			  icon.setPadding(50, 25, 0, 0);
			  
			  final int index = i;
			  icon.setOnClickListener(new View.OnClickListener() {
				    public void onClick(View view) {
				    	Intent intent = new Intent(StationActivity.this, DisplayScheduleActivity.class);
			    	      intent.putExtra("From", getIntent().getExtras().getString("station"));
			    	      intent.putExtra("To", departures[index]); 
			              startActivity(intent);
				    }
			  });
			  
			  main_right_departure_departuresLayout.addView(icon);
			  main_departure_departuresLayout.addView(main_right_departure_departuresLayout);
			  main_departuresLayout.addView(main_departure_departuresLayout);
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
			        if(getIntent().getExtras().getString("Station").equals("Cais do Sodré"))
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
		  if(station.equals("Cais do Sodré")){
			  connections = getResources().getStringArray(R.array.caisdosodre_connections);
			  departures = getResources().getStringArray(R.array.caisdosodre_departures);
		  }else if(station.equals("Cacilhas")){
				  connections = getResources().getStringArray(R.array.cacilhas_connections);
				  departures = getResources().getStringArray(R.array.cacilhas_departures);
		  }else if(station.equals("Porto Brandão")){
			  connections = getResources().getStringArray(R.array.portobrandao_connections);
			  departures = getResources().getStringArray(R.array.portobrandao_departures);
		  }else if(station.equals("Trafaria")){
			  connections = getResources().getStringArray(R.array.trafaria_connections);
			  departures = getResources().getStringArray(R.array.trafaria_departures);
		  }else if(station.equals("Belém")){
			  connections = getResources().getStringArray(R.array.belem_connections);
			  departures = getResources().getStringArray(R.array.belem_departures);
		  }else if(station.equals("Terreiro do Paço")){
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
		if(origin.equals("Cais do Sodré") && destiny.equals("Cacilhas")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_uteis);
			else
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_sabados_domingos_feriados);
		}else if(origin.equals("Cais do Sodré") && destiny.equals("Montijo")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_uteis);
			else if(day == 1)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_sabados);
			else
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_domingos_feriados);
		}else if(origin.equals("Cais do Sodré") && destiny.equals("Seixal")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_sabados);
			else
				schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_domingos_feriados);
		}else if(origin.equals("Cacilhas") && destiny.equals("Cais do Sodré")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_uteis);
			else
				schedule = getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_sabados_domingos_feriados);
		}else if(origin.equals("Seixal") && destiny.equals("Cais do Sodré")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_sabados);
		}else if(origin.equals("Montijo") && destiny.equals("Cais do Sodré")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_sabados);
		}else if(origin.equals("Trafaria") && destiny.equals("Belém")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_trafaria_belem_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_trafaria_belem_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_trafaria_belem_sabados);
		}else if(origin.equals("Porto Brandão") && destiny.equals("Belém")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_belem_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_porto_brandao_belem_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_belem_sabados);
		}else if(origin.equals("Belém") && destiny.equals("Trafaria")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_belem_trafaria_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_belem_trafaria_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_belem_trafaria_sabados);
		}else if(origin.equals("Porto Brandão") && destiny.equals("Trafaria")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_uteis);
			else if(day == 1)
					schedule = getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_domingos_feriados);
			else
				schedule = getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_sabados);
		}else if(origin.equals("Barreiro") && destiny.equals("Terreiro do Paço")){
			if(day > 1 && day < 7)
				schedule = getResources().getStringArray(R.array.schedule_barreiro_terreiro_do_paco_uteis);
			else
				schedule = getResources().getStringArray(R.array.schedule_barreiro_terreiro_do_paco_sabados_domingos_feriados);
		}else if(origin.equals("Terreiro do Paço") && destiny.equals("Barreiro")){
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
		  if(station.equals("Cais do Sodré"))
			  return getResources().getStringArray(R.array.caisdosodre_connections);
		  else if(station.equals("Cacilhas"))
			  return getResources().getStringArray(R.array.cacilhas_connections);
		  else if(station.equals("Seixal"))
			  return getResources().getStringArray(R.array.seixal_connections);
		  else if(station.equals("Montijo"))
			  return getResources().getStringArray(R.array.montijo_connections);
		  else if(station.equals("Porto Brandão"))
			  return getResources().getStringArray(R.array.portobrandao_connections);
		  else if(station.equals("Trafaria"))
			  return getResources().getStringArray(R.array.trafaria_connections);
		  else if(station.equals("Terreiro do Paço"))
			  return getResources().getStringArray(R.array.terreirodopaco_connections);
		  else if(station.equals("Barreiro"))
			  return getResources().getStringArray(R.array.barreiro_connections);
		  else if(station.equals("Belém"))
			  return getResources().getStringArray(R.array.belem_connections);
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
		}else if(station.equals("Terreiro do Paço")){
			return getResources().getString(R.string.price_terreirodopaco_barreiro);	
		}else if(station.equals("Barreiro")){
			return getResources().getString(R.string.price_terreirodopaco_barreiro);	
		}else if(station.equals("Porto Brandão")){
			return getResources().getString(R.string.price_trafaria_belem);	
		}else if(station.equals("Trafaria")){
			return getResources().getString(R.string.price_trafaria_belem);	
		}else if(station.equals("Belém")){
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
