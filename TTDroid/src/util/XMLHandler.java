package util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static PathInformation sitesList = null;

	public static PathInformation getSitesList() {
		return sitesList;
	}

	public static void setSitesList(PathInformation sitesList) {
		XMLHandler.sitesList = sitesList;
	}

	/** Called when tag starts ( ex:- <name>AndroidPeople</name> 
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("maintag"))
		{
			/** Start */ 
			sitesList = new PathInformation();
		} else if (localName.equals("website")) {
			/** Get attribute value */
			String attr = attributes.getValue("category");
			sitesList.setCategory(attr);
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
			sitesList.setName(currentValue);
		else if (localName.equalsIgnoreCase("website"))
			sitesList.setWebsite(currentValue);

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
