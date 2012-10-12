package jp.genin.ln2shin.recommend.design;

import java.util.*;

public class Movie {

	private int movieId = 0;
	private String movieTitle = "";
	private ArrayList<String> genreList = new ArrayList<String>();


	public Movie(String[] strLine) {
		movieId = Integer.parseInt(strLine[0]);
		movieTitle = strLine[1];
		String[] genreLine = strLine[2].split("|");
		for (int i = 0; i < genreLine.length; i++)
			genreList.add(genreLine[i]);
	}


	public int getMovieId() {
		return movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public ArrayList<String> getGenre() {
		return genreList;
	}
}