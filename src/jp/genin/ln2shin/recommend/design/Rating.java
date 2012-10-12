package jp.genin.ln2shin.recommend.design;

public class Rating {

	private int userId;
	private int movieId;
	private int rating;


	public Rating(int userId, int movieId, int rating) {
		this.userId = userId;
		this.movieId = movieId;
		this.rating = rating;
	}


	public int getUserId() {
		return userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public int getRating() {
		return rating;
	}

}
