package jp.genin.ln2shin.recommend.util;

import jp.genin.ln2shin.recommend.design.*;

public class ShowUserSeenMovieTable {

	public static void main(String[] args) {
		String ratingFilename = args[0];
		int userId = Integer.parseInt(args[1]);
		RatingData ratingData = new RatingData(ratingFilename);

		System.out.println("|| Movie|Rating||");
		for (Rating rating : ratingData.getUserRatingList(userId))
			System.out.printf("|| %4d | %4d ||\n", rating.getMovieId(), rating.getRating());
	}
}
