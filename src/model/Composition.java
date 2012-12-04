package model;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Constants;
import util.IObserver;
import util.ISubject;
import util.ModelChangedEventArgs;
import util.ViewEventArgs;

public class Composition implements ISubject {

	private List<Glyph> children;
	private List<IObserver> observers;

	public Composition() {
		this.children = new ArrayList<Glyph>();
		this.observers = new ArrayList<IObserver>();
	}

	public void insert(Glyph glyph, int i) {
		this.children.add(i, glyph);
		this.modelChanged();
	}

	public void remove(int i) {
		this.children.remove(i);
		this.modelChanged();
	}

	public void refresh(ViewEventArgs args) {
		this.modelChanged();
	}

	public List<Glyph> getChildren() {
		return this.children;
	}

	public void updateFont(List<Font> fonts, int startFrom, int endAt) {
		int i, j;
		for (i = startFrom, j = 0; i <= endAt; i++, j++) {
			this.children.get(i).setFont(fonts.get(j));
		}

		this.modelChanged();
	}

	@Override
	public void registerObserver(IObserver observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		int index = this.observers.indexOf(observer);
		if (index >= 0) {
			this.observers.remove(index);
		}
	}

	@Override
	public void notifyObservers() {
		ModelChangedEventArgs args = new ModelChangedEventArgs(
				this.getChildren());
		for (IObserver observer : this.observers) {
			observer.updateObserver(args);
		}
	}

	public void modelChanged() {
		this.notifyObservers();
	}

	public void saveToFile(String filePath) throws Exception {
		if (filePath == null || filePath.equals("")) {
			throw new NullPointerException("File path is null.");
		}

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("document");
		doc.appendChild(rootElement);

		Element glyphsElement = doc.createElement("glyphs");
		rootElement.appendChild(glyphsElement);

		for (Glyph glyph : this.children) {
			Element glyphElement = glyph.toXmlElement(doc);
			glyphsElement.appendChild(glyphElement);
		}

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filePath));
		transformer.transform(source, result);
	}

	public void loadFromFile(String filePath) throws Exception {
		if (filePath == null || filePath.equals("")) {
			throw new NullPointerException("File path is null.");
		}
				
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
		FileInputStream stream = new FileInputStream(new File(filePath));
		Document doc = dBuilder.parse(stream);
		
		Element docEle = doc.getDocumentElement();
		
		NodeList nl = docEle.getElementsByTagName(Constants.GlyphsNodeName);
		
		Element glyphs = (Element) nl.item(0);
		
		nl = glyphs.getChildNodes();
		
		this.children.clear();
		
		for (int i = 0 ; i < nl.getLength(); i++){
			
			Node nNode = nl.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if (eElement.getNodeName() == Constants.CharNodeName) {
					NodeList nlList = eElement.getElementsByTagName(Constants.ContentString).item(0).getChildNodes();
					Node nValue = (Node) nlList.item(0);
					char content = nValue.getNodeValue().charAt(0);						
					Node fontNode = eElement.getElementsByTagName(Constants.FontNodeName).item(0);
					Element fontElemtn = (Element) fontNode;
					String fontName = fontElemtn.getAttribute(Constants.FontNameAttributeName);
					int style = Integer.parseInt(fontElemtn.getAttribute(Constants.FontStyleAttributeName));
					int size = Integer.parseInt(fontElemtn.getAttribute(Constants.FontSizeAttributeName));
					Font font = new Font(fontName, style, size);
					Char ch = new Char(content, font);						
					this.children.add(ch);
				}
				else if (eElement.getNodeName() == Constants.PictureNodeName) {
					Picture pic = new Picture(eElement.getAttribute(Constants.FilePathAttributeName));
					this.children.add(pic);
				}
			}
		}
		
		stream.close();
		this.modelChanged();
	}
}
