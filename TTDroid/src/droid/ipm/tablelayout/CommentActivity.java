package droid.ipm.tablelayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.comment);
	}

	public void success(){
		Intent intent = new Intent();
		intent.putExtra("commenter", ((TextView)this.findViewById(R.id.commenter)).getText());
		intent.putExtra("comment", ((TextView)this.findViewById(R.id.comment)).getText());
		intent.putExtra("rating", (int)((RatingBar)this.findViewById(R.id.rating)).getRating());
		setResult(RESULT_OK,intent);
        finish();
	}
}
