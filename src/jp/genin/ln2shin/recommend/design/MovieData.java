package jp.genin.ln2shin.recommend.design;

import java.io.*;
import java.util.*;

public class MovieData {

	private int numOfMovie = 0;
	private ArrayList<Movie> movieList = new ArrayList<Movie>();


	public MovieData(String fileName) {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String readString = null;

			while((readString = br.readLine()) != null) {
				String[] strLine = readString.split("::");

				movieList.add(new Movie(strLine));
			}

			numOfMovie = movieList.size();

			Collections.sort(movieList, new Comparator<Movie>() {
				@Override
				public int compare(Movie movie1, Movie movie2) {
					return movie1.getMovieId() - movie2.getMovieId();
				}
			});

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumOfMovie() {
		return numOfMovie;
	}

	public int getMovieId(int movieIndex) {
		return movieList.get(movieIndex).getMovieId();
	}

	public int getMovieIndex(int movieId) {
		for (Movie movie : movieList)
			if (movie.getMovieId() == movieId)
				return movieList.indexOf(movie);

		return -1;
	}

	public String getMovieTitle(int movieIndex) {
		return movieList.get(movieIndex).getMovieTitle();
	}

	public ArrayList<Movie> getMovieList() {
		return movieList;
	}

}