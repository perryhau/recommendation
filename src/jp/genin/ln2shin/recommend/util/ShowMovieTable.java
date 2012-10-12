package jp.genin.ln2shin.recommend.util;

import jp.genin.ln2shin.recommend.design.MovieData;

public class ShowMovieTable {

	public static void main(String[] args) {
		String fileName = args[0];
		MovieData movieData = new MovieData(fileName);

		for (int i = 0; i <= movieData.getMovieIndex(10); i++) {
			System.out.printf("|| %2d | %40s ||\n",
					movieData.getMovieId(i),
					movieData.getMovieTitle(movieData.getMovieId(i)));
		}

	}

}