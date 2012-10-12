package jp.genin.ln2shin.recommend.design;

import java.io.*;
import java.util.*;

public class RatingData {

	private int maxUserId;
	private int maxMovieId;
	private List<Rating> ratingList;
	private Map<Integer, List<Rating>> userMap;
	private Map<Integer, List<Rating>> movieMap;


	public RatingData(String filename) {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String readString = null;
			maxUserId = 0;
			maxMovieId = 0;
			ratingList = new ArrayList<Rating>();
			userMap = new TreeMap<Integer, List<Rating>>();
			movieMap = new TreeMap<Integer, List<Rating>>();

			while ((readString = br.readLine()) != null) {
				String[] strLine = readString.split("::");

				int userId = Integer.parseInt(strLine[0]);
				int movieId = Integer.parseInt(strLine[1]);
				int rating = Integer.parseInt(strLine[2]);

				if (userId > maxUserId)
					maxUserId = userId;
				if (movieId > maxMovieId)
					maxMovieId = movieId;

				ratingList.add(new Rating(userId, movieId, rating));
			}

			for (Rating rating : ratingList) {
				if (userMap.get(rating.getUserId()) != null) {
					List<Rating> tmpUserList = userMap.get(rating.getUserId());
					tmpUserList.add(rating);
					userMap.put(rating.getUserId(), tmpUserList);
				} else {
					List<Rating> tmpUserList = new ArrayList<Rating>();
					tmpUserList.add(rating);
					userMap.put(rating.getUserId(), tmpUserList);
				}

				if (movieMap.get(rating.getMovieId()) != null) {
					List<Rating> tmpMovieList = movieMap.get(rating.getMovieId());
					tmpMovieList.add(rating);
					movieMap.put(rating.getMovieId(), tmpMovieList);
				} else {
					List<Rating> tmpMovieList = new ArrayList<Rating>();
					tmpMovieList.add(rating);
					movieMap.put(rating.getMovieId(), tmpMovieList);
				}

			}

			for (List<Rating> ratingList : userMap.values())
				Collections.sort(ratingList, new Comparator<Rating>() {
					@Override
					public int compare(Rating rating1, Rating rating2) {
						int value1 = rating1.getMovieId();
						int value2 = rating2.getMovieId();
						if (value1 > value2)
							return 1;
						else if (value2 > value1)
							return -1;
						return 0;
					}
				});
			for (List<Rating> ratingList : movieMap.values())
				Collections.sort(ratingList, new Comparator<Rating>() {
					@Override
					public int compare(Rating rating1, Rating rating2) {
						int value1 = rating1.getUserId();
						int value2 = rating2.getUserId();
						if (value1 > value2)
							return 1;
						else if (value2 > value1)
							return -1;
						return 0;
					}
				});

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getMaxUserId() {
		return maxUserId;
	}

	public int getMaxMovieId() {
		return maxMovieId;
	}

	public int getRating(int userId, int movieId) {
		int rating = 0;
		for (Rating item : getUserRatingList(userId))
			if (item.getMovieId() == movieId) {
				rating = item.getRating();
				break;
			}
		return rating;
	}

	public List<Rating> getUserRatingList(int userId) {
		return userMap.get(userId);
	}

	public List<Rating> getMovieRatingList(int movieId) {
		return movieMap.get(movieId);
	}

	public Map<Integer, List<Rating>> getUserMap() {
		return userMap;
	}

	public Map<Integer, List<Rating>> getMovieMap() {
		return movieMap;
	}

	public TwinRatingLists getTwinSameMovieSeenUserRatingList(int user1Id, int user2Id) {
		List<Rating> user1SeenList = getUserRatingList(user1Id);
		List<Rating> user2SeenList = getUserRatingList(user2Id);
		List<Rating> tmpUser1List = new ArrayList<Rating>();
		List<Rating> tmpUser2List = new ArrayList<Rating>();
		TwinRatingLists twinList = new TwinRatingLists(tmpUser1List, tmpUser2List);

		for (Rating rating1 : user1SeenList)
			for (Rating rating2 : user2SeenList)
				if (rating1.getMovieId() == rating2.getMovieId()) {
					twinList.addFirstList(rating1);
					twinList.addSecondList(rating2);
				}

		return twinList;
	}

	public TwinRatingLists genDoubleMovieSeenUserRatingList(int movie1Id, int movie2Id) {
		List<Rating> movie1List = getMovieRatingList(movie1Id);
		List<Rating> movie2List = getMovieRatingList(movie2Id);
		List<Rating> tmpMovie1List = new ArrayList<Rating>();
		List<Rating> tmpMovie2List = new ArrayList<Rating>();
		TwinRatingLists twinList = new TwinRatingLists(tmpMovie1List, tmpMovie2List);

		for (Rating rating1 : movie1List)
			for (Rating rating2 : movie2List)
				if (rating1.getUserId() == rating2.getUserId()) {
					twinList.addFirstList(rating1);
					twinList.addSecondList(rating2);
				}

		return twinList;
	}

	public double calcUserAvgScore(List<Rating> userRatingList) {
		double userAvgScore = 0.0;

		for (Rating rating : userRatingList)
			userAvgScore += rating.getRating();
		if (userRatingList.size() != 0)
			userAvgScore /= userRatingList.size();

		return userAvgScore;
	}

	public double  calcResemblanceDegree(int user1Id, int user2Id) {
		double user1AvgScore = calcUserAvgScore(getUserRatingList(user1Id));
		double user2AvgScore = calcUserAvgScore(getUserRatingList(user2Id));

		TwinRatingLists twoUserRatingList = getTwinSameMovieSeenUserRatingList(user1Id, user2Id);
		List<Rating> user1RatingList = twoUserRatingList.getFirstList();
		List<Rating> user2RatingList = twoUserRatingList.getSecondList();

		// 2l‚ª‹¤’Ê‚µ‚Ä•]‰¿‚µ‚Ä‚¢‚é‚à‚Ì‚ª1ŒÂˆÈ‰º‚Ìê‡‚Í‘ŠŠÖŒW”‚ð0‚Æ‚·‚é(else).
		if (user1RatingList.size() > 2) {
			double user1Score = 0.0;
			for (Rating rating : user1RatingList)
				user1Score += Math.pow((rating.getRating() - user1AvgScore), 2);
			user1Score = Math.sqrt(user1Score);

			double user2Score = 0.0;
			for (Rating rating : user2RatingList)
				user2Score += Math.pow((rating.getRating() - user2AvgScore), 2);
			user2Score = Math.sqrt(user2Score);

			double twoUsersScore = 0.0;
			for (int i = 0; i < user1RatingList.size(); i++)
				twoUsersScore +=
				(user1RatingList.get(i).getRating() - user1AvgScore)
				* (user2RatingList.get(i).getRating() - user2AvgScore);

			// 0Š„‚ð–h‚®.
			if (user1Score != 0 && user2Score != 0) {
				double resemblanceDegree = twoUsersScore / (user1Score * user2Score);

				return resemblanceDegree;
			} else
				return 0.0;

		} else
			return 0.0;
	}

	public double estimateRating(int userId, int movieId) {
		// userId ‚É‘Î‚·‚é movieId ‚Ì rating ‚ð„’è‚·‚é.
		double userRating = 0.0;
		double sumOfRating = 0.0;
		double userAvgScore = calcUserAvgScore(getUserRatingList(userId));
		List<Rating> seenMovieUserList = getMovieRatingList(movieId);
		// —v‘f”‚ª1ˆÈ‰º‚È‚ç‚ÎŒvŽZ‚Å‚«‚È‚¢‚Ì‚Å0‚ð•Ô‚·.
		if (seenMovieUserList.size() > 0) {
			for (Rating rating : seenMovieUserList) {
				double tmpUserAvgScore = calcUserAvgScore(getUserRatingList(rating.getUserId()));
				double tmpuserRating = calcResemblanceDegree(userId, rating.getUserId());
				tmpuserRating *= rating.getRating() - tmpUserAvgScore;
				userRating += tmpuserRating;

				sumOfRating += Math.abs(calcResemblanceDegree(userId, rating.getUserId()));
			}

			return userAvgScore + (userRating / sumOfRating);
		} else
			return userAvgScore;
	}

	public double[][] genResemblanceDegreeTable() {
		double[][] resemblanceDegreeTable = new double[maxUserId][maxUserId];

		for (int i = 1; i < maxUserId; i++)
			for (int j = 1; j < maxUserId; j++) {
				if (i == j)
					resemblanceDegreeTable[i-1][i-1] = 1.0;
				else if (i != j)
					resemblanceDegreeTable[i-1][j-1] = calcResemblanceDegree(i+1, j+1);
			}
		return resemblanceDegreeTable;
	}

	public int[][] genRatingTable() {
		int[][] ratingTable = new int[maxUserId][maxMovieId];
		for (Rating item : ratingList)
			ratingTable[item.getUserId() - 1][item.getMovieId() - 1] = item.getRating();
		return ratingTable;
	}

}