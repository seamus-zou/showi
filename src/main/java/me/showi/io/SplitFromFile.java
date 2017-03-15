package me.showi.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author seamus
 * @date 2017年2月13日 下午2:45:10
 * @description
 */
public class SplitFromFile {

	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("C:\\Users\\AAS-1359\\Desktop\\new 2.txt");
		BufferedReader bufr = new BufferedReader(fr);

		FileWriter fw = new FileWriter("C:\\Users\\AAS-1359\\Desktop\\new 3.txt");
		BufferedWriter bw = new BufferedWriter(fw);

		String line = "";
		while ((line = bufr.readLine()) != null) {
			String regex = "[a-zA-Z||\\s||\\.]+";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(line);
			while (m.find()) {
				String words = m.group();
				String explanation = line.replace(words, "").replaceAll(" ", "");
				bw.write(words);
				bw.write("\t");
				bw.write(explanation);
				bw.write("\n");
				break;
			}

		}
		bw.close();
		fw.close();
		bufr.close();
		fr.close();
	}
}
