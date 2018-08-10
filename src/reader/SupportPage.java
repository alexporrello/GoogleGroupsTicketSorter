package reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An entire support page, including...
 * <ul>
 * 	<li>The support page's name.</li>
 * 	<li>The support page's web url.</li>
 * 	<li>The support page's local directory.</li>
 * 	<li>The support page's tags.</li>
 * 	<li>All of the support page's messages.</li>
 * </ul>
 * 
 * @author Alexander Porrello
 */
public class SupportPage extends ArrayList<Message> {
	private static final long serialVersionUID = 1522435904734482515L;

	public static final File SUPPORT_PAGES = new File(System.getProperty("user.home") + "\\support_pages");

	/** Users can add tags to a support page to help sort it. **/
	private HashSet<String> tags = new HashSet<String>();

	/** The support page's name **/
	private String name;

	/** The support page's web URL **/
	private String url;

	/** The support page's directory on the local machine **/
	private String directory;

	/** Initialize an empty support page. **/
	public SupportPage() { }

	/**
	 * Reads in a support page that has been scraped to the local machine
	 * by GoogleGroupsScraper.
	 * @param directory The support page's directory on the local machine.
	 */
	public SupportPage(File directory) {
		this.directory = directory.getAbsolutePath();

		try {
			readXML(directory.getAbsolutePath());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a support page that has been scraped to the local machine 
	 * by GoogleGroupsScraper.
	 * @param directory The directory of the local file to be loaded.
	 */
	private void readXML(String directory) throws SAXException, IOException, ParserConfigurationException {
		File fXmlFile = new File(directory);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();

		NodeList aList = doc.getElementsByTagName("Info");
		for (int i = 0; i < aList.getLength(); i++) {
			Node node = aList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				this.url = element.getElementsByTagName("url").item(0).getTextContent();
				this.name = element.getElementsByTagName("name").item(0).getTextContent();

				try {
					for(String s : element.getElementsByTagName("tags").item(0).getTextContent().split(",")) {
						if(s.length() > 0) {
							this.tags.add(s);
						}
					}
				} catch(NullPointerException e) {
					System.err.print("The support page '" + name + "' has no tags.\n");
				}

				break;
			}
		}

		NodeList bList = doc.getElementsByTagName("Post");
		for (int i = 0; i < bList.getLength(); i++) {
			Node node = bList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String userName = element.getElementsByTagName("username").item(0).getTextContent();
				String post     = element.getElementsByTagName("body").item(0).getTextContent();

				add(new Message(userName, post.replaceAll("#n#", "\n")));
			}
		}
	}

	/** Adds a tag to the current support page **/
	public Boolean addTag(String tag) {
		return tags.add(tag.trim());
	}

	/** Removes a tag from the current support page **/
	public Boolean removeTag(String tag) {
		return tags.remove(tag);
	}

	/** Returns this support page's name. **/
	public String getName() {
		return name;
	}

	/** Returns this support page's url. **/
	public String getURL() {
		return url;
	}

	/** Returns this support page's directory. **/
	public String getDirectory() {
		return directory;
	}

	/** Returns this support page's tags. **/
	public HashSet<String> getTags() {
		return tags;
	}

	/** Returns this support page's tags as a string, separated by commas. **/
	public String getTagsAsString() {
		String toReturn = "";

		for(String s : tags) {
			toReturn = toReturn + "," + s;
		}

		try {
			return toReturn.substring(1, toReturn.length());
		} catch(StringIndexOutOfBoundsException e) {
			return "";
		}
	}

	/** Saves updates to a support page **/
	public void save() {
		try {
			SupportPageWriter.write(this);
		} catch (ParserConfigurationException | TransformerException e1) {
			e1.printStackTrace();
		}
	}
}
