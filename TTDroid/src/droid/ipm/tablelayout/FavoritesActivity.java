package droid.ipm.tablelayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import droid.ipm.util.Favorite;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FavoritesActivity extends Activity {

	// Filename to comments location
	private final String FAVORITE_STORE = "favorites_store";
	List<Favorite> favorites;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			loadFavorites();
		} catch (Exception e) {
			favorites = new LinkedList<Favorite>();
		}

		LayoutInflater inflater = this.getLayoutInflater();

		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(1);
		
		for (Favorite favorite: favorites) {
			View v = inflater.inflate(R.layout.favorites_item, null);
			((TextView) v.findViewById(R.id.from)).setText(favorite.getFrom());
			((TextView) v.findViewById(R.id.to)).setText(favorite.getTo());
			
			final Favorite f = favorite;
			LinearLayout l = (LinearLayout) v.findViewById(R.id.favlinearlayout);
			l.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
			    	Intent intent = new Intent(FavoritesActivity.this, DisplayScheduleActivity.class);
		    	      intent.putExtra("From", (String) f.getFrom());
		    	      intent.putExtra("To", (String) f.getTo()); 
		              startActivity(intent);
				}
			});
			
			Button deleteButton = (Button) v.findViewById(R.id.deletebutton);
			deleteButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
			    	favorites.remove(f);
			    	FavoritesActivity.this.saveFavorites();
				}
			});
			
			v.setPadding(0, 6, 6, 6);
			ll.addView(v);
		}
		sv.addView(ll);
		setContentView(sv);
	}

	private void loadFavorites() throws IOException, FileNotFoundException,
			IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(openFileInput(FAVORITE_STORE));
		favorites = new LinkedList<Favorite>();
		while (true) {
			try {
				favorites.add((Favorite) in.readObject());
			} catch (IOException e) {
				break;
			}
		}
	}
	
	private void saveFavorites(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(FAVORITE_STORE, Context.MODE_PRIVATE));
			for (Favorite f : favorites)
				oos.writeObject(f);
			oos.close();
			Toast.makeText(this, "Favorito eliminado", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(this, "Tente novamente", Toast.LENGTH_LONG).show();
		}
	}



}
