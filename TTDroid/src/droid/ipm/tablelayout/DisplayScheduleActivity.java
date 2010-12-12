package droid.ipm.tablelayout;

import java.util.Calendar;

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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  String from = getIntent().getExtras().getString("From");
		  String to = getIntent().getExtras().getString("To");
		  
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
				  if(schedule[i].split(":")[1].contains("F"))
					  line += schedule[i].split(":")[1].split(" ")[0]+"*     ";
				  else
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
		  text.setText("* Ferry");
		  text.setTextSize(20);
		  main_bodyLayout.addView(text);
		  
		  ScrollView scroll = new ScrollView(this);
		  scroll.setSmoothScrollingEnabled(true);
		  scroll.addView(main_bodyLayout);
		  mainLayout.addView(scroll);
		  setContentView(mainLayout);

		  
	}
	
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.schedulemenu, menu);
        return true;
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case 0:
            return new TimePickerDialog(this,
                    mTimeSetListener, 00, 00, true);
        }
        return null;
    }
    
	 // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Toast.makeText(DisplayScheduleActivity.this, "Alarme adicionado com sucesso", Toast.LENGTH_LONG).show();
			}
		};
	
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        switch (item.getItemId()) {
            case R.id.iaddalarm: showDialog(0);
            					break;
            case R.id.iabout: 	intent = new Intent(this, AboutActivity.class);
  	      						startActivityForResult(intent, 0);
            					break;
        }
        return true;
    }
    
	int getHours(String time){
		if(time.contains("F"))
			time = time.split(" ")[0];
		String[] sTime = time.split(":");
		return Integer.parseInt(sTime[0]);
	}
	
	int getMinutes(String time){
		if(time.contains("F"))
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
		  }
		  return null;
	}

}
