package droid.ipm.util;

import java.io.Serializable;

public class Favorite implements Serializable {

	private String from;
	private String to;

	private static final long serialVersionUID = 1L;

	public Favorite(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}


}

