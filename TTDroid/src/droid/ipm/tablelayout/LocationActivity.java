package droid.ipm.tablelayout;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity; 
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import droid.ipm.tablelayout.R;

public class LocationActivity extends MapActivity{

	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map);
	    
	    MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    mapView.setStreetView(true);
	    MapController mc = mapView.getController();
	    mc.setZoom(14);
	    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
	    StationItemizedOverlayActivity itemizedoverlay = new StationItemizedOverlayActivity(drawable,this);
	    
	    GeoPoint point = new GeoPoint(38705169,-9145006);
	    OverlayItem overlayitem = new OverlayItem(point, "Estação Cais do Sodré", "");
	    itemizedoverlay.addOverlay(overlayitem);
	    if(getIntent().getExtras().getString("Station").equals("Cais do Sodré"))
	    	mc.setCenter(point);
	    
	    point = new GeoPoint(38688004,-9147869);
	    overlayitem = new OverlayItem(point, "Estação Cacilhas", "");
	    itemizedoverlay.addOverlay(overlayitem);
	    if(getIntent().getExtras().getString("Station").equals("Cacilhas"))
	    	mc.setCenter(point);
	    
	    mapOverlays.add(itemizedoverlay);
	    
	    
//	    LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//	    Location l = lm.getLastKnownLocation("gps");
//	    System.out.println(l.getLatitude()+" "+l.getLongitude());
	    
	    LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    LocationListener mlocListener = new MyLocationListener();
	    mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	}
	
	/* Class My Location Listener */

	public class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location loc)	{
			loc.getLatitude();
			loc.getLongitude();
			String Text = "My current location is: "+"Latitud = " + loc.getLatitude() +"Longitud = " + loc.getLongitude();
			Toast.makeText( getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
		}


		@Override
		public void onProviderDisabled(String provider){
			Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();
		}

		
		@Override
		public void onProviderEnabled(String provider){
			Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
		}


		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){

		}

	}

}
	
