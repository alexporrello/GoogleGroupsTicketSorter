package reader;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SupportPageWriter {

	public static Boolean write(SupportPage page) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder        docBuilder = docFactory.newDocumentBuilder();
		Document               doc        = docBuilder.newDocument();

		Element rootElement = doc.createElement("support-page");
		doc.appendChild(rootElement);

		int id = 0;

		Element supportPageInfo = doc.createElement("Info");
		rootElement.appendChild(supportPageInfo);

		supportPageInfo.setAttribute("id", id++ + "");
		addElement(doc, supportPageInfo, "url", page.getURL());
		addElement(doc, supportPageInfo, "name", page.getName());
		addElement(doc, supportPageInfo, "tags", page.getTagsAsString());
		
		for(Message m : page) {
			Element post = doc.createElement("Post");
			rootElement.appendChild(post);

			post.setAttribute("id", id++ + "");

			addElement(doc, post, "username", m.getPoster());
			addElement(doc, post, "body", m.getBody());
		}

		return writeToFile(page.getDirectory(), doc);
	}

	private static void addElement(Document doc, Element parent, String element, String value) {
		Element newElement = doc.createElement(element);
		newElement.appendChild(doc.createTextNode(value));
		parent.appendChild(newElement);
	}

	public static Boolean writeToFile(String url, Document doc) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(url));

		transformer.transform(source, result);

		return new File(url).exists();
	}
}