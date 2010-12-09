package droid.ipm.tablelayout;

import java.util.Calendar;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;

public class DisplayScheduleActivity extends Activity{
	
	private String[] uteis;
	private String[] saturday;
	private String[] sunday;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  String from = getIntent().getExtras().getString("From");
		  String to = getIntent().getExtras().getString("To");
		  
		  int day = Calendar.DAY_OF_WEEK;

		  String[] schedule = null;
		  if(from.equals("Cais do Sodr") && to.equals("Cacilhas")){
			  if(day > 1 && day < 7)
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_uteis);
			  else
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_cacilhas_sabados_domingos_feriados);
		  }else if(from.equals("Cais do Sodr") && to.equals("Montijo")){
			  if(day > 1 && day < 7)
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_uteis);
			  else if(day == 1)
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_sabados);
			  else
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_montijo_domingos_feriados);
		  }else if(from.equals("Cais do Sodr") && to.equals("Seixal")){
			  if(day > 1 && day < 7)
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_uteis);
			  else if(day == 1)
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_sabados);
			  else
				  schedule = getResources().getStringArray(R.array.schedule_caisdosodre_seixal_domingos_feriados);
		  }else if(from.equals("Cacilhas") && to.equals("Cais do Sodr")){
			  if(day > 1 && day < 7)
				  schedule = getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_uteis);
			  else
				  schedule = getResources().getStringArray(R.array.schedule_cacilhas_caisdosodre_sabados_domingos_feriados);
		  }
		  
		  LinearLayout mainLayout = new LinearLayout(this);
		  mainLayout.setOrientation(1);
		  mainLayout.setScrollContainer(true);
		  mainLayout.setScrollBarStyle(50331648);
		  System.out.println(schedule[schedule.length-1]);
		  TextView text;
		  for(int i = 0; i < schedule.length; i++){
			  int hours = getHours(schedule[i]);
			  String line = schedule[i].split(":")[0]+"    ";
			  System.out.println(line);
			  while(i < schedule.length && hours == getHours(schedule[i])){
				  line += schedule[i].split(":")[1]+"  ";
				  i++;
			  }
			  i--;
			  
			  text = new TextView(this);
			  text.setText(line);
			  text.setTextSize(20);
			  text.setPadding(0, 0, 0, 0);
			  mainLayout.addView(text);
		  }
		  
		  setContentView(mainLayout);

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

}
