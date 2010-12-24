package droid.ipm.util;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XMLExample{

	/** Create Object For SiteList Class */
	PathInformation pathInfo = null;
	
	public XMLExample(double originLat, double originLong, String loc){

		try {
			
			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			//http://maps.googleapis.com/maps/api/directions/xml?origin=38.705083,-9.145429&destination=38.688137,-9.147667&region=pt&avoid=highways&sensor=true
			/** Send URL to parse XML Tags */
			//System.out.println("http://maps.googleapis.com/maps/api/directions/xml?origin="+Double.toString(originLat)+","+Double.toString(originLong)+"&destination=loc&region=pt&avoid=highways&sensor=true");
			URL sourceUrl = new URL(
					"http://maps.googleapis.com/maps/api/directions/xml?origin="+Double.toString(originLat)+","+Double.toString(originLong)+"&destination="+loc+"&region=pt&avoid=highways&sensor=true");
			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			XMLHandler myXMLHandler = new XMLHandler();
			xr.setContentHandler(myXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));
			
            pathInfo = myXMLHandler.getParsedData();

			
		} catch (Exception e) {
			System.out.println("XML Parsing Excpetion = " + e);
		}


	}
	
	public String gimme(){
		return pathInfo.getDistance();
	}
	
}