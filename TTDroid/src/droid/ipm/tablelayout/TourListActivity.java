package droid.ipm.tablelayout;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TourListActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LayoutInflater inflater = this.getLayoutInflater();
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(1);
		
		TypedArray tours_xml = getResources().obtainTypedArray(R.array.tours);
		
		for (int i = 0; i < tours_xml.length(); i++) {
			JSONObject j = null;
			try {
				final String jstring = tours_xml.getString(i);
				j = new JSONObject(jstring);
				
				
				View v = inflater.inflate(R.layout.tour_item, null);
				((TextView) v.findViewById(R.id.tour_name)).setText(j.getString("tour-name"));
				((ImageView) v.findViewById(R.id.tour_scheme)).setImageResource(getResources().getIdentifier(j.getString("img"), "drawable", "com.filipe.test"));
				
				if(j.getJSONArray("monuments").length() > 0){
					((TextView) v.findViewById(R.id.monument_name01)).setText(j.getJSONArray("monuments").getJSONObject(0).getString("monument_name"));
					if(j.getJSONArray("monuments").length() > 1)
						((TextView) v.findViewById(R.id.monument_name02)).setText(j.getJSONArray("monuments").getJSONObject(1).getString("monument_name"));
				}
				
				v.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(TourListActivity.this, TourActivity.class);
						intent.putExtra("tour", jstring);
						startActivity(intent);
					}
				});
				v.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1f));
				ll.addView(v);
				//TODO adicionar barra horizontal a dividir circuitos
			} catch (JSONException e) {
				System.out.println("Failure to add tour item number "+i+" because\n"+e.getLocalizedMessage());
			}
			
		}
		
		setContentView(ll);
	}
}
