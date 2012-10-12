package jp.genin.ln2shin.recommend.util;

import jp.genin.ln2shin.recommend.design.*;

public class ShowRatingTable {

	public static void main(String[] args) {
		String filename = args[0];
		RatingData ratingData = new RatingData(filename);

		System.out.printf("||    |");
		for(int j = 1; j <= ratingData.getMaxMovieId(); j++)
			System.out.printf(" %2d |", j);
		System.out.println("|");

		int[][] ratingTable = ratingData.genRatingTable();
		for (int i = 0; i < ratingData.getMaxUserId(); i++) {
			System.out.printf("|| %2d |", i+1);
			for (int j = 0; j < ratingData.getMaxMovieId(); j++) {
				System.out.printf(" %2d |", ratingTable[i][j]);
			}
			System.out.println("|");
		}
	}
}
