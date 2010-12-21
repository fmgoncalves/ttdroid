package droid.ipm.tablelayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.comment);
		Button ok_button = (Button)findViewById(R.id.okbutton);
		//este onClick est‡ no xml mas se for chamado por l‡ o programa estoira....
		ok_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				success();
			}
		});
	}

	public void success(){
		Intent intent = new Intent();
		intent.putExtra("commenter", ((TextView)findViewById(R.id.commenter)).getText().toString());
		intent.putExtra("comment", ((TextView)findViewById(R.id.comment)).getText().toString());
		intent.putExtra("rating", (int)((RatingBar)findViewById(R.id.rating)).getRating());
		setResult(RESULT_OK,intent);
        finish();
	}
}
