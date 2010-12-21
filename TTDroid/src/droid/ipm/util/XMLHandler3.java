package droid.ipm.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler3 extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static PathInformation pathList = null;

	public static PathInformation getSitesList() {
		return pathList;
	}

	public static void setSitesList(PathInformation sitesList) {
		XMLHandler3.pathList = sitesList;
	}
	//http://www.androidpeople.com/wp-content/uploads/2010/06/example.xml
	/** Called when tag starts ( ex:- <name>AndroidPeople</name> 
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("DirectionsResponse"))
		{
			/** Start */ 
			pathList = new PathInformation();
		} else if (localName.equals("distance")) {
			/** Get attribute value */
			String attr = attributes.getValue("category");
			//pathList.setCategory(attr);
		}

	}

	/** Called when tag closing ( ex:- <name>AndroidPeople</name> 
	 * -- </name> )*/
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		/** set value */ 
		if (localName.equalsIgnoreCase("name"))
			pathList.setName(currentValue);
		else if (localName.equalsIgnoreCase("website"))
			pathList.setWebsite(currentValue);

	}

	/** Called to get tag characters ( ex:- <name>AndroidPeople</name> 
	 * -- to get AndroidPeople Character ) */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

}
