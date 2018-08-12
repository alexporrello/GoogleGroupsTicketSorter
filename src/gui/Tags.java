package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import reader.SupportPage;

public class Tags {
	public static final String TAG_FILE = SupportPage.SUPPORT_PAGES + "\\tags.txt";
	
	/**
	 * Checks if the tag file has been created. Then...
	 * <ul>
	 * 	<li>If it has not been created, then create the file, populate with tags <i>Resolved</i> and <i>Unresolved</i>,
	 * 		and return these two tags, separated by a comma.
	 * 	<li>If it has been created, load all of the tags and return them as a string separated by commas.</li>
	 * </ul>
	 * <b>Note:</b> Should be run before {@link #loadAllTags()}. 
	 * @return All loaded tags as a string separated by commas.
	 * @throws IOException Throws exception if the tag file cannot be created.
	 */
	public static String createOrLoadAllTags() throws IOException {
		if(!new File(Tags.TAG_FILE).exists()) {
			new File(Tags.TAG_FILE).createNewFile();

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(Tags.TAG_FILE))) {
				String content = "Unresolved\nResolved";
				bw.write(content);
				
				return "Unresolved,Resolved";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return loadAllTags();
	}
	
	/**
	 * Load all of the tags and return them as a string separated by commas.
	 * @return All tags loaded from the file as a string separated by commas.
	 */
	public static String loadAllTags() {
		String toReturn = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(Tags.TAG_FILE))) {
			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null) {
				toReturn = sCurrentLine + "," + toReturn;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	
	public static void writeNewTag(String newTag) throws IOException {		
		HashSet<String> allTags = new HashSet<String>();
		for(String tag : createOrLoadAllTags().split(",")) { allTags.add(tag); }
		allTags.add(newTag);
		
		String toWrite = "";
		for(String s : allTags) { toWrite = s + "\n" + toWrite; }
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(Tags.TAG_FILE))) {
			bw.write(toWrite.substring(0, toWrite.length()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
