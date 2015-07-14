package graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import petriNet.Marking;
import petriNet.Place;

public abstract class GraphParser {
	protected Graph toParse;

	public abstract Graph parse(File xml) throws ParserConfigurationException, SAXException, IOException;

	protected String getNodeAttribute(Node toParse, String attribute) {
		return toParse.getAttributes().getNamedItem(attribute).toString();
	}

	protected String getNodeLabel(Node toAnalyze) {
		Element casted = (Element) toAnalyze;
		Element data = (Element) casted.getElementsByTagName("data").item(0);
		Element shapeNode = (Element) data.getElementsByTagName("y:ShapeNode").item(0);
		Element nodeLabel = (Element) shapeNode.getElementsByTagName("y:NodeLabel").item(0);
		return nodeLabel.getTextContent();
	}

	protected int getEdgeId(Node toAnalyze) {
		Element casted = (Element) toAnalyze;
		Element data = (Element) casted.getElementsByTagName("data").item(0);
		Element shapeNode = (Element) data.getElementsByTagName("y:PolyLineEdge").item(0);
		Element nodeLabel = (Element) shapeNode.getElementsByTagName("y:EdgeLabel").item(0);
		return Integer.parseInt(nodeLabel.getTextContent().replace("t", ""));
	}

	protected Marking getNodeMarking(String markingString) {
		List<String> placesStrings = new ArrayList<>(Arrays.asList(markingString.split("p")));
		placesStrings.remove(0);
		List<Place> placesList = new ArrayList<>();
		for (String placeString : placesStrings) {
			Place toAdd = new Place(Integer.parseInt(placeString.trim()));
			placesList.add(toAdd);
		}
		return new Marking(placesList);
	}

}
