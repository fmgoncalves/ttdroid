package droid.ipm.util;

import java.util.ArrayList;

/** Contains getter and setter method for varialbles  */
public class PathInformation {

	/** Variables */
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> website = new ArrayList<String>();
	private String distance = "";

	
	/** In Setter method default it will return arraylist 
	 *  change that to add  */
	
	public ArrayList<String> getName() {
		return name;
	}

	public void setName(String name) {
		this.name.add(name);
	}

	public ArrayList<String> getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website.add(website);
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

}
