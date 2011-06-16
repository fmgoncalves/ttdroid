package droid.ipm.tablelayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import droid.ipm.util.Favorite;

public class DisplayScheduleActivity extends Activity {

	private final String FAVORITE_STORE = "favorites_store";
	String from;
	String to;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);

		  LayoutInflater inflater = this.getLayoutInflater();
		  
		  View mainLayout = inflater.inflate(R.layout.schedule_list, null);
		  
		  from = getIntent().getExtras().getString("From");
		  to = getIntent().getExtras().getString("To");
		  
		  Calendar calendar = Calendar.getInstance();
		  int day = calendar.get(Calendar.DAY_OF_WEEK);

		  String[] schedule = getSchedule(from, to, day);
		  
		  TextView text = (TextView) mainLayout.findViewById(R.id.schedule_title);
		  text.setText(String.format("%s - %s",from, to));
		  
		  //div para o body
		  LinearLayout main_bodyLayout = (LinearLayout) mainLayout.findViewById(R.id.schedule_body);

		  boolean label[] = new boolean[3];
		  
		  Map<String,List<String>> schdl = new HashMap<String, List<String>>();
		  for(int i = 0; i < schedule.length; i++) {
			  String[] x = schedule[i].split(":");
			  String key = x[0];
			  String value = x[1];
			  char c = value.charAt(value.length()-1);
			  switch(c) {
			  case 'F':
				  label[0] = true;
			  case 'a':
				  label[1] = true;
			  case 'A':
				  label[2] = true;
			  case 'B':
				  label[2] = true;
			  case 'C':
				  label[2] = true;
			  }
			  if(!schdl.containsKey(key)) {
				  List<String> val = new LinkedList<String>();
				  schdl.put(key, val);
			  }
			  schdl.get(key).add(value);
		  }
		  
		  for( String x: schdl.keySet()) {
			  View v = inflater.inflate(R.layout.schedule_item, null);
			  TextView hour = (TextView) v.findViewById(R.id.item_hour);
			  GridView minutes = (GridView) v.findViewById(R.id.item_minutes);
			  hour.setText(x);
			  minutes.setAdapter(new ArrayAdapter<String>(this,R.layout.simple_tv, schdl.get(x)));
			  main_bodyLayout.addView(v);
		  }
		  
		  for(int i =0; i < label.length; i++) {
			  if(label[i]) {
				  text = new TextView(this);
				  switch(i){
				  	case 0: text.setText("F - Ferry");
				  			break;
				  	case 1: text.setText("a - Com partida para Bel�m");
				  			break;
				  	case 2: text.setText("A - Destina-se apenas para Porto Brand�o\nB - Directo � Trafaria\nC - Percurso Bel�m-Trafaria-Porto Brand�o");
				  			break;
				  }
				  text.setTextSize(15);
				  main_bodyLayout.addView(text);
			  }
		  }
		  		  
		  LinearLayout main_footerLayout = (LinearLayout) mainLayout.findViewById(R.id.schedule_footer);
		  main_footerLayout.setOrientation(0);
		  ImageView icon = new ImageView(this);
		  icon.setImageResource(R.drawable.previous);
		  icon.setScaleType(ImageView.ScaleType.MATRIX);
		  main_footerLayout.addView(icon);
		  
		  icon = new ImageView(this);
		  icon.setImageResource(R.drawable.next);
		  icon.setScaleType(ImageView.ScaleType.MATRIX);
		  main_footerLayout.addView(icon);
		
		  setContentView(mainLayout);  
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.schedulemenu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.iaddfavorite:
			saveFavorite();
			break;
		case R.id.iabout:
			intent = new Intent(this, AboutActivity.class);
			startActivityForResult(intent, 0);
			break;
		}
		return true;
	}

	private void saveFavorite() {
		try {
			List<Favorite> favorites = loadFavorites();
			ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(
					FAVORITE_STORE, Context.MODE_PRIVATE));
			for (Favorite f : favorites)
				oos.writeObject(f);
			oos.writeObject(new Favorite(from, to));
			oos.close();
			Toast.makeText(this, "Favorito adicionado", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(this, "Tente novamente", Toast.LENGTH_LONG).show();
		}
	}

	private List<Favorite> loadFavorites() throws IOException,
			FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(
				openFileInput(FAVORITE_STORE));
		List<Favorite> result = new LinkedList<Favorite>();
		while (true) {
			try {
				result.add((Favorite) in.readObject());
			} catch (IOException e) {
				break;
			}
		}
		return result;
	}

	String[] getSchedule(String from, String to, int dayOfWeek) {
		if (from.equals("Cais do Sodr�") && to.equals("Cacilhas")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_caisdosodre_cacilhas_uteis);
			else
				return getResources()
						.getStringArray(
								R.array.schedule_caisdosodre_cacilhas_sabados_domingos_feriados);
		} else if (from.equals("Cais do Sodr�") && to.equals("Montijo")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_caisdosodre_montijo_uteis);
			else if (dayOfWeek == 1)
				return getResources().getStringArray(
						R.array.schedule_caisdosodre_montijo_sabados);
			else
				return getResources().getStringArray(
						R.array.schedule_caisdosodre_montijo_domingos_feriados);
		} else if (from.equals("Cais do Sodr�") && to.equals("Seixal")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_caisdosodre_seixal_uteis);
			else if (dayOfWeek == 1)
				return getResources().getStringArray(
						R.array.schedule_caisdosodre_seixal_sabados);
			else
				return getResources().getStringArray(
						R.array.schedule_caisdosodre_seixal_domingos_feriados);
		} else if (from.equals("Cacilhas") && to.equals("Cais do Sodr�")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_cacilhas_caisdosodre_uteis);
			else
				return getResources()
						.getStringArray(
								R.array.schedule_cacilhas_caisdosodre_sabados_domingos_feriados);
		} else if (from.equals("Seixal") && to.equals("Cais do Sodr�")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_seixal_cais_do_sodre_uteis);
			else if (dayOfWeek == 1)
				return getResources()
						.getStringArray(
								R.array.schedule_seixal_cais_do_sodre_domingos_feriados);
			else
				return getResources().getStringArray(
						R.array.schedule_seixal_cais_do_sodre_sabados);
		} else if (from.equals("Montijo") && to.equals("Cais do Sodr�")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_montijo_cais_do_sodre_uteis);
			else if (dayOfWeek == 1)
				return getResources()
						.getStringArray(
								R.array.schedule_montijo_cais_do_sodre_domingos_feriados);
			else
				return getResources().getStringArray(
						R.array.schedule_montijo_cais_do_sodre_sabados);
		} else if (from.equals("Trafaria") && to.equals("Bel�m")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_trafaria_belem_uteis);
			else if (dayOfWeek == 1)
				return getResources().getStringArray(
						R.array.schedule_trafaria_belem_domingos_feriados);
			else
				return getResources().getStringArray(
						R.array.schedule_trafaria_belem_sabados);
		} else if (from.equals("Porto Brand�o") && to.equals("Bel�m")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_porto_brandao_belem_uteis);
			else if (dayOfWeek == 1)
				return getResources().getStringArray(
						R.array.schedule_porto_brandao_belem_domingos_feriados);
			else
				return getResources().getStringArray(
						R.array.schedule_porto_brandao_belem_sabados);
		} else if (from.equals("Bel�m") && to.equals("Trafaria")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_belem_trafaria_uteis);
			else if (dayOfWeek == 1)
				return getResources().getStringArray(
						R.array.schedule_belem_trafaria_domingos_feriados);
			else
				return getResources().getStringArray(
						R.array.schedule_belem_trafaria_sabados);
		} else if (from.equals("Porto Brand�o") && to.equals("Trafaria")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_porto_brandao_trafaria_uteis);
			else if (dayOfWeek == 1)
				return getResources()
						.getStringArray(
								R.array.schedule_porto_brandao_trafaria_domingos_feriados);
			else
				return getResources().getStringArray(
						R.array.schedule_porto_brandao_trafaria_sabados);
		} else if (from.equals("Barreiro") && to.equals("Terreiro do Pa�o")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_barreiro_terreiro_do_paco_uteis);
			else
				return getResources()
						.getStringArray(
								R.array.schedule_barreiro_terreiro_do_paco_sabados_domingos_feriados);
		} else if (from.equals("Terreiro do Pa�o") && to.equals("Barreiro")) {
			if (dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(
						R.array.schedule_terreiro_do_paco_barreiro_uteis);
			else
				return getResources()
						.getStringArray(
								R.array.schedule_terreiro_do_paco_barreiro_sabados_domingos_feriados);
		}
		return null;
	}

}
