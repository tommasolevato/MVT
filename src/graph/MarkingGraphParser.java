package graph;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import petriNet.Marking;
import petriNet.Transition;

public class MarkingGraphParser extends GraphParser {

	@Override
	public Graph parse(File xml) throws ParserConfigurationException, SAXException, IOException {
		toParse = new Graph();
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document doc = dBuilder.parse(xml);
		doc.getDocumentElement().normalize();
		NodeList states = doc.getElementsByTagName("node");

		for (int i = 0; i < states.getLength(); i++) {
			MarkingState s = buildMarkingState(states.item(i));
			toParse.addNode(s);
		}

		NodeList edges = doc.getElementsByTagName("edge");
		for (int i = 0; i < edges.getLength(); i++) {
			Edge e = buildEdge(edges.item(i));
			toParse.addEdge(e);
		}
		return toParse;
	}

	private MarkingState buildMarkingState(Node stateToBuild) {
		String marking = getNodeLabel(stateToBuild);
		return new MarkingState(getNodeMarking(marking));
	}

	private Edge buildEdge(Node edgeToBuild) {
		int id = getEdgeId(edgeToBuild);
		String source = getNodeAttribute(edgeToBuild, "source");
		String dest = getNodeAttribute(edgeToBuild, "target");
		String sourceSubString = source.split("\"")[1];
		String destSubString = dest.split("\"")[1];
		Marking sourceMarking = getNodeMarking(sourceSubString);
		Marking destMarking = getNodeMarking(destSubString);
		return new Edge(new Transition(id), toParse.getStateById(MarkingState.getStateId(sourceMarking)),
				toParse.getStateById(MarkingState.getStateId(destMarking)));
	}

}
