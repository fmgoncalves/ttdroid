package droid.ipm.other;

import java.io.Serializable;

public class TourComment implements Serializable {

	private String commenter;
	private String comment;
	private int rating;

	private static final long serialVersionUID = 1L;

	public TourComment(String commenter, String comment, int rating) {
		super();
		this.commenter = commenter;
		this.comment = comment;
		this.rating = rating;
	}

	public String getCommenter() {
		return commenter;
	}

	public String getComment() {
		return comment;
	}

	public int getRating() {
		return rating;
	}

}
