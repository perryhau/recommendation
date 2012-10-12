package jp.genin.ln2shin.recommend.util;

import java.util.List;

import jp.genin.ln2shin.recommend.design.*;

public class ShowSeenTwoMovieUserRatingTable {

	public static void main(String[] args) {
		String ratingFilename = args[0];
		int movie1Id = Integer.parseInt(args[1]);
		int movie2Id = Integer.parseInt(args[2]);
		RatingData ratingData = new RatingData(ratingFilename);
		TwinRatingLists twinList = ratingData.genDoubleMovieSeenUserRatingList(movie1Id, movie2Id);

		System.out.println("movieId " + movie1Id + "と，movieId " + movie2Id + "の映画を観たユーザーのレーティングリスト.");
		System.out.printf("||      | %4d | %4d ||\n", movie1Id, movie2Id);

		List<Rating> firstList = twinList.getFirstList();
		List<Rating> secondList = twinList.getSecondList();

		for (int i = 0; i < firstList.size(); i++) {
			System.out.printf("|| %4d | %4d | %4d ||\n",
					firstList.get(i).getUserId(), firstList.get(i).getRating(), secondList.get(i).getRating());
		}
	}

}
