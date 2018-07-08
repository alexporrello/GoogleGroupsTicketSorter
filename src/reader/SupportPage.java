package reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SupportPage extends ArrayList<Message> {
	private static final long serialVersionUID = 1522435904734482515L;

	public static final File SUPPORT_PAGES = new File(System.getProperty("user.home") + "\\scraper.log\\support_tickets");

	private String title;
	private String url;

	public SupportPage() {
		// For initiating an empty ticket.
	}

	/**
	 * Reads in ticket files that have been saved to the local machine
	 * by GoogleGroupsScraper.
	 * @param url the path of the ticket file to be parsed.
	 */
	public SupportPage(File url) {
		try {
			readXML();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void readXML() throws SAXException, IOException, ParserConfigurationException {
		File fXmlFile = new File("D:\\Downloads\\file.xml");
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
				this.title = element.getElementsByTagName("name").item(0).getTextContent();

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

	public String getTitle() {
		return title;
	}

	public String getURL() {
		return url;
	}

	@Override
	public String toString() {
		String toReturn = title + "\n" + url + "\n";

		for(Message m : this) {
			toReturn = toReturn + "\n===================\n" + m.poster + "\n" + m.body;
		}

		return toReturn;
	}
}
