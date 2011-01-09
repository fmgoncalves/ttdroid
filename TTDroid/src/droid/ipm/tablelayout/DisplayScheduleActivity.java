package droid.ipm.tablelayout;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import droid.ipm.util.Favorite;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class DisplayScheduleActivity extends Activity{
	
	private final String FAVORITE_STORE = "favorites_store";
	String from;
	String to;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  from = getIntent().getExtras().getString("From");
		  to = getIntent().getExtras().getString("To");
		  
		  Calendar calendar = Calendar.getInstance();
		  int day = calendar.get(Calendar.DAY_OF_WEEK);

		  String[] schedule = getSchedule(from, to, day);
		  
		  LinearLayout mainLayout = new LinearLayout(this);
		  mainLayout.setOrientation(1);
		  
		  //div para o header
		  LinearLayout main_headerLayout = new LinearLayout(this);
		  main_headerLayout.setOrientation(0);
		  
		  TextView text;
		  text = new TextView(this);
		  text.setText(from+" - "+to);
		  text.setTextSize(28);
		  main_headerLayout.addView(text);
		  mainLayout.addView(main_headerLayout);
		  
		  //div para o body
		  LinearLayout main_bodyLayout = new LinearLayout(this);
		  main_bodyLayout.setOrientation(1);
		  main_bodyLayout.setPadding(10, 0, 10, 0);
		  
		  ImageView icon;
		  int label = 0;
		  for(int i = 0; i < schedule.length; i++){
			  LinearLayout main_lineLayout = new LinearLayout(this);
			  main_lineLayout.setOrientation(0);
			  main_lineLayout.setPadding(0, 5, 0, 5);
			  int hours = getHours(schedule[i]);
			  text = new TextView(this);
			  text.setText(schedule[i].split(":")[0]);
			  text.setTextSize(20);
			  text.setTypeface(Typeface.DEFAULT_BOLD);
			  main_lineLayout.addView(text);
			  String line = "";
			  while(i < schedule.length && hours == getHours(schedule[i])){
				  if(schedule[i].split(":")[1].contains("F")){
					  line += schedule[i].split(":")[1].split(" ")[0]+"F     ";
					  label = 1;
				  }else if(schedule[i].split(":")[1].contains("a)")){
					  line += schedule[i].split(":")[1].split(" ")[0]+"a     ";
					  label = 2;
				  }else if(schedule[i].split(":")[1].contains("A")){
					  line += schedule[i].split(":")[1].split(" ")[0]+"A     ";
					  label = 3;
				  }else if(schedule[i].split(":")[1].contains("B")){
					  line += schedule[i].split(":")[1].split(" ")[0]+"B     ";
					  label = 3;
				  }else if(schedule[i].split(":")[1].contains("C")){
					  line += schedule[i].split(":")[1].split(" ")[0]+"C     ";
					  label = 3;
				  }else
					  line += schedule[i].split(":")[1]+"       ";

				  i++;
			  }
			  i--;
			  
			  text = new TextView(this);
			  text.setText(line);
			  text.setTextSize(20);
			  text.setPadding(30, 0, 0, 0);
			  main_lineLayout.addView(text);
			  main_bodyLayout.addView(main_lineLayout);
			  
			  if(i != schedule.length - 1){
				  icon = new ImageView(this);
				  icon.setImageResource(R.drawable.line);
				  main_bodyLayout.addView(icon);
			  }
			  
		  }
		  
		  text = new TextView(this);
		  switch(label){
		  	case 1: text.setText("F - Ferry");
		  			break;
		  	case 2: text.setText("a - Com partida para BelŽm");
		  			break;
		  	case 3: text.setText("A - Destina-se apenas para Porto Brand‹o\nB - Directo ˆ Trafaria\nC - Percurso BelŽm-Trafaria-Porto Brand‹o");
		  			break;
		  }
		  
		  text.setTextSize(15);
		  main_bodyLayout.addView(text);
		  
		  ScrollView scroll = new ScrollView(this);
		  scroll.setSmoothScrollingEnabled(true);
		  scroll.addView(main_bodyLayout);
		  mainLayout.addView(scroll);
		  
		  LinearLayout main_footerLayout = new LinearLayout(this);
		  main_footerLayout.setOrientation(0);
		  icon = new ImageView(this);
		  icon.setImageResource(R.drawable.previous);
		  icon.setScaleType(ImageView.ScaleType.MATRIX);
		  main_footerLayout.addView(icon);
		  
		  icon = new ImageView(this);
		  icon.setImageResource(R.drawable.next);
		  icon.setScaleType(ImageView.ScaleType.MATRIX);
		  main_footerLayout.addView(icon);
		  
		  mainLayout.addView(main_footerLayout);
		  setContentView(mainLayout);

		  
	}
	
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.schedulemenu, menu);
        return true;
    }
	
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        switch (item.getItemId()) {
            case R.id.iaddfavorite:	saveFavorite();
            						break;
            case R.id.iabout: 	intent = new Intent(this, AboutActivity.class);
  	      						startActivityForResult(intent, 0);
            					break;
        }
        return true;
    }
    
	private void saveFavorite() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(
					FAVORITE_STORE, Context.MODE_PRIVATE));
			oos.writeObject(new Favorite(from,to));
			oos.close();
			Toast.makeText(this, "Favorito adicionado", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(this, "Tente novamente", Toast.LENGTH_LONG).show();
		}
	}

	int getHours(String time){
		if(time.contains("F") || time.contains("a)") || time.contains("A") || time.contains("B") || time.contains("C"))
			time = time.split(" ")[0];
		String[] sTime = time.split(":");
		return Integer.parseInt(sTime[0]);
	}
	
	int getMinutes(String time){
		if(time.contains("F") || time.contains("a)") || time.contains("A") || time.contains("B") || time.contains("C"))
			time = time.split(" ")[0];
		String[] sTime = time.split(":");
		return Integer.parseInt(sTime[1]);
	}
	
	String[] getSchedule(String from, String to, int dayOfWeek){
		if(from.equals("Cais do SodrŽ") && to.equals("Cacilhas")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_uteis);
			else
				return getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_sabados_domingos_feriados);
		}else if(from.equals("Cais do SodrŽ") && to.equals("Montijo")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_caisdosodre_montijo_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_caisdosodre_montijo_sabados);
			else
				return getResources().getStringArray(R.array.schedule_caisdosodre_montijo_domingos_feriados);
		}else if(from.equals("Cais do SodrŽ") && to.equals("Seixal")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_caisdosodre_seixal_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_caisdosodre_seixal_sabados);
			else
				return getResources().getStringArray(R.array.schedule_caisdosodre_seixal_domingos_feriados);
		}else if(from.equals("Cacilhas") && to.equals("Cais do SodrŽ")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_uteis);
			else
				return getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_sabados_domingos_feriados);
		}else if(from.equals("Seixal") && to.equals("Cais do SodrŽ")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_domingos_feriados);
			else
				return getResources().getStringArray(R.array.schedule_seixal_cais_do_sodre_sabados);
		}else if(from.equals("Montijo") && to.equals("Cais do SodrŽ")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_domingos_feriados);
			else
				return getResources().getStringArray(R.array.schedule_montijo_cais_do_sodre_sabados);
		}else if(from.equals("Trafaria") && to.equals("BelŽm")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_trafaria_belem_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_trafaria_belem_domingos_feriados);
			else
				return getResources().getStringArray(R.array.schedule_trafaria_belem_sabados);
		}else if(from.equals("Porto Brand‹o") && to.equals("BelŽm")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_porto_brandao_belem_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_porto_brandao_belem_domingos_feriados);
			else
				return getResources().getStringArray(R.array.schedule_porto_brandao_belem_sabados);
		}else if(from.equals("BelŽm") && to.equals("Trafaria")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_belem_trafaria_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_belem_trafaria_domingos_feriados);
			else
				return getResources().getStringArray(R.array.schedule_belem_trafaria_sabados);
		}else if(from.equals("Porto Brand‹o") && to.equals("Trafaria")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_uteis);
			else if(dayOfWeek == 1)
				return getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_domingos_feriados);
			else
				return getResources().getStringArray(R.array.schedule_porto_brandao_trafaria_sabados);
		}else if(from.equals("Barreiro") && to.equals("Terreiro do Pao")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_barreiro_terreiro_do_paco_uteis);
			else
				return getResources().getStringArray(R.array.schedule_barreiro_terreiro_do_paco_sabados_domingos_feriados);
		}else if(from.equals("Terreiro do Pao") && to.equals("Barreiro")){
			if(dayOfWeek > 1 && dayOfWeek < 7)
				return getResources().getStringArray(R.array.schedule_terreiro_do_paco_barreiro_uteis);
			else
				return getResources().getStringArray(R.array.schedule_terreiro_do_paco_barreiro_sabados_domingos_feriados);
		}
		  return null;
	}

}
