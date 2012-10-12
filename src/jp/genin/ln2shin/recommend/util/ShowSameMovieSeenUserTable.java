package jp.genin.ln2shin.recommend.util;

import jp.genin.ln2shin.recommend.design.*;

public class ShowSameMovieSeenUserTable {

	public static void main(String[] args) {
		String ratingFilename = args[0];
		int movieId = Integer.parseInt(args[1]);
		RatingData ratingData = new RatingData(ratingFilename);

		System.out.println("|| User |Rating||");
		for (Rating rating : ratingData.getMovieRatingList(movieId))
			System.out.printf("|| %4d | %4d ||\n", rating.getUserId(), rating.getRating());
	}
}
