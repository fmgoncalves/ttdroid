package droid.ipm.tablelayout;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

public class DisplayScheduleActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
	        TextView textview = new TextView(this);
	        textview.setText("Show Schedule: esta em extra From e To");
	        setContentView(textview);
	}

}
