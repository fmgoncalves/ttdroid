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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import droid.ipm.other.TourComment;

public class TourActivity extends Activity {

	private static final int COMMENT_REQUEST_CODE = 0;
	// Filename to comments location
	private final String COMMENT_STORE = "comment_store";
	private String tourName;
	List<TourComment> comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ScrollView sv = new ScrollView(this);
		LayoutInflater inflater = this.getLayoutInflater();

		LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.tour_main,
				null);

		try {
			JSONObject json_t = new JSONObject(getIntent().getExtras()
					.getString("tour"));

			((TextView) ll.findViewById(R.id.tour_name)).setText(json_t
					.getString("tour-name"));
			((TextView) ll.findViewById(R.id.tour_description)).setText(json_t
					.getString("description"));

			JSONArray monuments = json_t.getJSONArray("monuments");
			LinearLayout monument_list = ((LinearLayout) ll
					.findViewById(R.id.monument_list));
			for (int i = 0; i < monuments.length(); i++) {
				JSONObject mon = monuments.getJSONObject(i);
				final String monument_link = mon.getString("wiki-url");
				View v = inflater.inflate(R.layout.monument_item, null);
				((TextView) v.findViewById(R.id.monument_name)).setText(mon
						.getString("monument_name"));
				((ImageView) v.findViewById(R.id.monument_thumb))
						.setImageResource(getResources().getIdentifier(
								mon.getString("image"), "drawable",
								"droid.ipm.tablelayout"));
				v.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent("android.intent.action.VIEW", Uri
								.parse(monument_link.replace("%%", "%")));
						startActivity(i);
					}
				});

				monument_list.addView(v);
			}

			tourName = json_t.getString("tour-name");

			try {
				loadComments();
			} catch (Exception e) {
				comments = new LinkedList<TourComment>();
			}

			showComments((LinearLayout) ll.findViewById(R.id.comment_list));

		} catch (JSONException e) {
			System.out.println("Failure at Tour: " + e.getLocalizedMessage());
		}

		sv.addView(ll);
		setContentView(sv);
	}

	private void showComments(LinearLayout comment_list) {
		comment_list.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(getResources().getString(R.string.add_comment));
		tv.setTextSize(16f);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				newComment();
			}
		});

		comment_list.addView(tv);

		LayoutInflater inflater = this.getLayoutInflater();

		for (TourComment tc : comments) {
			View v = inflater.inflate(R.layout.comment_item, null);
			((TextView) v.findViewById(R.id.commenter)).setText(tc
					.getCommenter());
			((TextView) v.findViewById(R.id.comment)).setText(tc.getComment());
			((RatingBar) v.findViewById(R.id.rating)).setRating((float) tc
					.getRating());
			comment_list.addView(v);
		}
		// Dummy data to fill up
		View v0 = inflater.inflate(R.layout.comment_item, null);
		if (tourName.equals("Descobrimentos")) {
			((TextView) v0.findViewById(R.id.commenter)).setText("José Manuel");
			((TextView) v0.findViewById(R.id.comment))
					.setText("Uma perspectiva diferente da história da cidade.");
			((RatingBar) v0.findViewById(R.id.rating)).setRating(3);
		} else {
			((TextView) v0.findViewById(R.id.commenter))
					.setText("Ernesto Ferreira");
			((TextView) v0.findViewById(R.id.comment))
					.setText("Bom programa para uma tarde de Verão.");
			((RatingBar) v0.findViewById(R.id.rating)).setRating(4);
		}
		comment_list.addView(v0);
	}

	private void loadComments() throws IOException, FileNotFoundException,
			IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(
				openFileInput(COMMENT_STORE + "_" + tourName));
		comments = new LinkedList<TourComment>();
		while (true) {
			try {
				comments.add((TourComment) in.readObject());
			} catch (IOException e) {
				break;
			}
		}
	}

	private void saveComments() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(
					COMMENT_STORE + "_" + tourName, Context.MODE_PRIVATE));
			for (TourComment c : comments)
				oos.writeObject(c);
			oos.close();
		} catch (IOException e) {
			System.out.println("Error saving: " + e.getLocalizedMessage());
		}
	}

	public void newComment() {
		Intent intent = new Intent(getApplicationContext(),
				CommentActivity.class);
		startActivityForResult(intent, COMMENT_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == COMMENT_REQUEST_CODE && resultCode == -1) {
			Bundle extras = intent.getExtras();
			String commenter = extras.getString("commenter");
			String comment = extras.getString("comment");
			int rating = extras.getInt("rating");
			comments.add(new TourComment(commenter, comment, rating));
			saveComments();
			Toast.makeText(TourActivity.this, "Comentário adicionado",
					Toast.LENGTH_SHORT).show();
			try {
				loadComments();
			} catch (Exception e) {
				// TODO do what?
			}
			showComments((LinearLayout) findViewById(R.id.comment_list));
		} else
			Toast.makeText(
					TourActivity.this,
					"Returned " + requestCode + " with "
							+ (resultCode == -1 ? "success" : "failure"),
					Toast.LENGTH_SHORT).show();
	}
}
