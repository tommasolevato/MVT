package graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import petriNet.Domain;
import petriNet.DomainInequality;
import petriNet.Marking;
import petriNet.Transition;

public class SCGGraphParser extends GraphParser {
	private Map<Integer, StateClassNode> ids;

	public SCGGraphParser() {
		ids = new HashMap<>();
	}

	public Graph parse(File xml) throws ParserConfigurationException, SAXException, IOException {
		toParse = new Graph();
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document doc = dBuilder.parse(xml);
		doc.getDocumentElement().normalize();
		NodeList states = doc.getElementsByTagName("node");

		for (int i = 0; i < states.getLength(); i++) {
			StateClassNode s = buildStateClass(states.item(i));
			ids.put(s.getId(), s);
			toParse.addNode(s);
		}

		NodeList edges = doc.getElementsByTagName("edge");
		for (int i = 0; i < edges.getLength(); i++) {
			Edge e = buildEdge(edges.item(i));
			toParse.addEdge(e);
		}
		return toParse;
	}

	private StateClassNode buildStateClass(Node stateClassToBuild) {
		int id = getNodeId(stateClassToBuild);
		Marking m = getNodeMarking(stateClassToBuild);
		Domain d = getNodeDomain(stateClassToBuild);
		return new StateClassNode(id, m, d);
	}

	private Edge buildEdge(Node edgeToBuild) {
		String name = getEdgeId(edgeToBuild);
		String source = getNodeAttribute(edgeToBuild, "source");
		String dest = getNodeAttribute(edgeToBuild, "target");
		String sourceSubString = source.split("\"")[1];
		String destSubString = dest.split("\"")[1];
		int sourceId = Integer.parseInt(sourceSubString);
		int destId = Integer.parseInt(destSubString);

		return new Edge(new Transition(name), ids.get(sourceId), ids.get(destId));
	}

	private int getNodeId(Node toParse) {
		String rawId = getNodeAttribute(toParse, "id");
		String intSubString = rawId.split("\"")[1];
		return Integer.parseInt(intSubString);
	}

	private Marking getNodeMarking(Node toParse) {
		String nodeLabel = getNodeLabel(toParse);
		String lineToParse = nodeLabel.split("\n")[0];
		return getMarkingNode(lineToParse);
	}

	private Domain getNodeDomain(Node toParse) {
		String nodeLabel = getNodeLabel(toParse);
		List<String> inequalitiesStrings = new ArrayList<>(Arrays.asList(nodeLabel.split("\n")));
		inequalitiesStrings.remove(0);
		inequalitiesStrings.remove(inequalitiesStrings.size() - 1);
		List<DomainInequality> inequalities = new ArrayList<>();
		for (String inequality : inequalitiesStrings) {
			inequality = inequality.replace("(", "");
			inequality = inequality.replace(")", "");
			inequality = inequality.replace("*", "");
			String rawMinFiringTime = inequality.split("/=")[0];
			String rawTransitions = inequality.split("/=")[1];
			String rawMaxFiringTime = inequality.split("/=")[2];
			boolean newlyEnabled = false;
			if (rawMaxFiringTime.replace(" ", "").split("\\[").length > 1) {
				newlyEnabled = true;
			}
			float minFiringTime = Float.NEGATIVE_INFINITY;
			if (!rawMinFiringTime.contains("INF")) {
				minFiringTime = Float.valueOf(rawMinFiringTime.replace(" ", "").split("\\[")[0]);
			}
			float maxFiringTime = Float.POSITIVE_INFINITY;
			if (!rawMaxFiringTime.contains("INF")) {
				maxFiringTime = Float.valueOf(rawMaxFiringTime.replace(" ", "").split("\\[")[0]);
			}
			Transition subtracting = new Transition(rawTransitions.split("-")[0].trim());
			Transition toSubtract = null;
			if (rawTransitions.split("-").length > 1) {
				toSubtract = new Transition(rawTransitions.split("-")[1].trim());
			}
			inequalities.add(new DomainInequality(minFiringTime, maxFiringTime, subtracting, toSubtract, newlyEnabled));
		}

		return new Domain(inequalities);
	}
}