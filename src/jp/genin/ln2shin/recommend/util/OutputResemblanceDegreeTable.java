package jp.genin.ln2shin.recommend.util;

import java.io.*;

import jp.genin.ln2shin.recommend.design.*;

public class OutputResemblanceDegreeTable {

	public static void main(String[] args) {
		String filename = args[0];
		String outputFilename = args[1];
		RatingData ratingData = new RatingData(filename);

		double[][] resemblanceDegreeTable = ratingData.genResemblanceDegreeTable();

		File file = new File(outputFilename);
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			for (int i = 0; i < ratingData.getMaxUserId(); i++) {
				StringBuilder line = new StringBuilder();
				for (int j = 0; j < ratingData.getMaxUserId(); j++) {
					line.append(Double.toString(resemblanceDegreeTable[i][j]));
					if (j != ratingData.getMaxUserId() - 1)
						line.append(" ");
				}
				pw.println(line);
			}
			pw.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}



	}

}