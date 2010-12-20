package droid.ipm.tablelayout;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class NewsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = this.getLayoutInflater();

		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(1);

		TypedArray news = getResources().obtainTypedArray(R.array.news);
		for (int i = 0; i < news.length(); i++) {
			JSONObject j = null;
			try {
				j = new JSONObject(news.getString(i));
				View v = inflater.inflate(R.layout.news_item, null);
				((TextView) v.findViewById(R.id.title)).setText(j.getString("title"));
				((TextView) v.findViewById(R.id.content)).setText(j.getString("content"));
				((TextView) v.findViewById(R.id.date)).setText(j.getString("date"));
				ll.addView(v);
				//TODO adicionar barra horizontal a dividir notícias
			} catch (JSONException e) {
				System.out.println("Failure to add news item number "+i+" because "+e.getLocalizedMessage());
			}
			
		}
		sv.addView(ll);
		setContentView(sv);
	}

}
