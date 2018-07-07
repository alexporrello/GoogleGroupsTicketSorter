package reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import scraper.GoogleGroupsScraper;

public class Ticket extends ArrayList<Message> {
	private static final long serialVersionUID = 1522435904734482515L;

	public static final String TITLE_STRING = "##TITLE##";
	public static final String SPLIT_STRING = "#=#=#=#";

	String title;
	
	public Ticket() {
		// For initiating an empty ticket.
	}

	/**
	 * Reads in ticket files that have been saved to the local machine
	 * by this program.
	 * @param url the path of the ticket file to be parsed.
	 */
	public Ticket(File url) {
		String ticket = readInFile(url.getAbsolutePath());

		String[] split = ticket.split(Ticket.SPLIT_STRING);

		for(String s : split) {
			if(s.trim().startsWith(Ticket.TITLE_STRING)) {
				title = s.replace(Ticket.TITLE_STRING, "");
			} else if(!s.contains("[a-zA-Z]+") && s.length() > 2) {
				this.add(new Message(s));
			}
		}
	}


	/**
	 * Reads in a ticket file given its path.
	 * @param path the path to the local file.
	 * @return a String of the path file's contents.
	 */
	public static String readInFile(String path) {
		String toReturn = "";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				toReturn = toReturn + "\n" + sCurrentLine;
				//TODO Process Properties
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	public String getTitle() {
		return title;
	}

	public void writeToFile() {
		File f = new File(GoogleGroupsScraper.SUPPORT_PAGES + "\\" + title.trim());

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()))) {
			String content = toString();
			
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		String toReturn = Ticket.TITLE_STRING + title;

		for(Message m : this) {
			//TODO Write properties
			toReturn = toReturn + "\n" + Ticket.SPLIT_STRING + "\n" + m.toString();
		}

		return toReturn;
	}
}
