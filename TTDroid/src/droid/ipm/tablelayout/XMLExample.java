package droid.ipm.tablelayout;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import util.PathInformation;
import util.XMLHandler;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XMLExample extends Activity {

	/** Create Object For SiteList Class */
	PathInformation sitesList = null;
	
	public XMLExample(/*GeoPoint origin, GeoPoint destiny*/){

		try {
			
			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */
			URL sourceUrl = new URL(
					"http://www.androidpeople.com/wp-content/uploads/2010/06/example.xml");

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			XMLHandler myXMLHandler = new XMLHandler();
			xr.setContentHandler(myXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}

		/** Get result from MyXMLHandler SitlesList Object */
		sitesList = XMLHandler.sitesList;

		System.out.println(sitesList.getWebsite());

	}
}