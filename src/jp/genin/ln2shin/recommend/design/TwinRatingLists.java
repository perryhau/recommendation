package jp.genin.ln2shin.recommend.design;

import java.util.ArrayList;
import java.util.List;

public class TwinRatingLists {

	private List<Rating> firstList = new ArrayList<Rating>();
	private List<Rating> secondList = new ArrayList<Rating>();

	public TwinRatingLists(List<Rating> aFirstList, List<Rating> aSecondList) {
		firstList = aFirstList;
		secondList = aSecondList;
	}

	public List<Rating> getFirstList() {
		return firstList;
	}

	public List<Rating> getSecondList() {
		return secondList;
	}

	public void addFirstList(Rating rating) {
		firstList.add(rating);
	}

	public void addSecondList(Rating rating) {
		secondList.add(rating);
	}

	public void removeFromFirstList(Rating rating) {
		firstList.remove(rating);
	}

	public void removeFromSecondList(Rating rating) {
		secondList.remove(rating);
	}
}
