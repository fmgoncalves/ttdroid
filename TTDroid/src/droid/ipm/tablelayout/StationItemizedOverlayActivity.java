package droid.ipm.tablelayout;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class StationItemizedOverlayActivity extends ItemizedOverlay{
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public StationItemizedOverlayActivity(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
		}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
//	  Toast t = new Toast(mContext);
//	  t.setText(Text);
//	  t.setDuration(Toast.LENGTH_SHORT);
//	  t.setGravity(t.getGravity(), 10, 10);
	  Toast.makeText( mContext,item.getTitle(),Toast.LENGTH_SHORT).show();
	  return true;
	}
	
}
