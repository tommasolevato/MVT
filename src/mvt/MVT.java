package mvt;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import graph.Edge;
import graph.Graph;
import graph.MarkingGraphParser;
import graph.Node;
import graph.SCGGraphParser;
import petriNet.ExecutionLog;
import petriNet.Transition;

public class MVT {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		MarkingGraphParser g = new MarkingGraphParser();
		Graph parsed = g
				.parse(new File("D:/Documenti/VirtualBox VMs/Windows XP/esempio workspace Oris/First_Release/TCAnalyzer/output_MarkingGraph.graphml"));
		System.out.println(parsed.getNumberOfNodes());
		Set<Node> visited = new HashSet<>();
		List<Graph> allEdgeCoverage = parsed.getAllNodeCoverage();
		for(Graph path : allEdgeCoverage) {
			visited.addAll(path.getAllNodes());
		}
		System.out.println(visited.size());
		
//		ExecutionLogParser e = new ExecutionLogParser();
//		File toParse = new File("D:/galli_levato/tse/results/release_1/millisec/relativeLog/RTAI_relative_millisec_1.txt");
//		ExecutionLog log = e.parse(toParse);
//		for (Transition t : log.getTransitions()) {
//			System.out.println(t);
//		}
	}

}
