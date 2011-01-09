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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FavoritesActivity extends ListActivity {

	private static final int COMMENT_REQUEST_CODE = 0;
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

		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, getFavoritesArray()));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(FavoritesActivity.this, DisplayScheduleActivity.class);
  	      		intent.putExtra("From", parent.getItemAtPosition(position).toString().split("  ")[0]);
  	      		intent.putExtra("To", parent.getItemAtPosition(position).toString().split("  ")[2]); 
  	      		startActivity(intent);
			}
		});
	}


	private String[] getFavoritesArray() {
			String[] result = new String[favorites.size()];
			for(int i = 0; i < favorites.size();i++)
				result[i] = favorites.get(i).getFrom()+" - "+favorites.get(i).getTo();
		return result;
	}


	private void loadFavorites() throws IOException, FileNotFoundException,
			IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(
				openFileInput(FAVORITE_STORE));
		favorites = new LinkedList<Favorite>();
		while (true) {
			try {
				favorites.add((Favorite) in.readObject());
			} catch (IOException e) {
				break;
			}
		}
	}



}
