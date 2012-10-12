package jp.genin.ln2shin.recommend.util;

import java.util.*;

import jp.genin.ln2shin.recommend.design.*;

public class ShowSameMovieSeenUsersRatingTable {

	public static void main(String[] args) {
		String ratingFilename = args[0];
		int user1Id = Integer.parseInt(args[1]);
		int user2Id = Integer.parseInt(args[2]);
		RatingData ratingData = new RatingData(ratingFilename);
		TwinRatingLists twinList = ratingData.getTwinSameMovieSeenUserRatingList(user1Id, user2Id);

		System.out.println("UserId " + user1Id + "と" + user2Id + "の観た映画のレーティングリスト.");
		System.out.printf("||      | %4d | %4d ||\n", user1Id, user2Id);

		List<Rating> firstList = twinList.getFirstList();
		List<Rating> secondList = twinList.getSecondList();

		for (int i = 0; i < firstList.size(); i++) {
			System.out.printf("|| %4d | %4d | %4d ||\n",
					firstList.get(i).getMovieId(), firstList.get(i).getRating(), secondList.get(i).getRating());
		}
	}
}
