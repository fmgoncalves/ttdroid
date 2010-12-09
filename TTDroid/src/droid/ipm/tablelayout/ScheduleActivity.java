package droid.ipm.tablelayout;

import java.util.Arrays;

import droid.ipm.tablelayout.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.AdapterView.OnItemSelectedListener;

public class ScheduleActivity extends Activity {
	
	private Spinner spinnerFrom, spinnerTo;
	private String[] stations;
	private ScheduleActivity self;
	private ArrayAdapter<String> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.schedule);
	    
	    self = this;
	    stations = getResources().getStringArray(R.array.stations_array);

	    spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
	    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stations);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinnerFrom.setAdapter(adapter);
	    spinnerFrom.setOnItemSelectedListener(new FromSpinnerListener());

	    spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
	    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stations);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinnerTo.setAdapter(adapter);
	    spinnerTo.setOnItemSelectedListener(new ToSpinnerListener());
	    
        final Button button = (Button) findViewById(R.id.button);
        button.setText("Ok");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
      	      Intent intent = new Intent(ScheduleActivity.this, DisplayScheduleActivity.class);
    	      intent.putExtra("From", (String) spinnerFrom.getSelectedItem());
    	      intent.putExtra("To", (String) spinnerTo.getSelectedItem()); 
              startActivity(intent);
            }
        });

	}
	
	public class FromSpinnerListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent,View view, int pos, long id) {
			//Toast.makeText(parent.getContext(), "The planet is " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
			//System.out.println(spinnerFrom.getSelectedItem());
		}

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
	public class ToSpinnerListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent,View view, int pos, long id) {
			//Toast.makeText(parent.getContext(), "The planet is " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
}
